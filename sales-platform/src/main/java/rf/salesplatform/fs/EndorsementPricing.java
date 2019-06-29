package rf.salesplatform.fs;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.joda.time.Days;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import rf.eval.EvalConstant;
import rf.eval.EvalEngine;
import rf.eval.EvalJob;
import rf.eval.model.EvalNode;
import rf.foundation.model.BaseModel;
import rf.foundation.model.Pair;
import rf.foundation.pub.FunctionSlice;
import rf.policyadmin.ds.PolicyLogService;
import rf.policyadmin.model.*;
import rf.policyadmin.model.enums.EndorsementFeeLevel;
import rf.policyadmin.model.enums.LogType;
import rf.product.ds.ProductService;
import rf.product.model.EndorsementSpec;
import rf.product.model.FeeSpec;
import rf.product.model.FormulaSpec;
import rf.product.model.ProductSpec;
import rf.product.model.enums.FormulaPurpose;
import rf.salesplatform.pub.ModelConverter;
import rf.salesplatform.pub.Constants;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;


@Component
public class EndorsementPricing implements FunctionSlice<Endorsement> {

    @Autowired
    private ProductService productService;
    @Autowired
    private PolicyLogService logService;
    @Autowired
    private EvalEngine evalEngine;

    @Override
    public void execute(Endorsement endorsement, Map<String, Object> context){
        Policy policy = (Policy)context.get(Constants.POLICY_OBJECT);

        ProductSpec product = productService.findProduct(endorsement.getProductCode());
        Map<String,FeeSpec> feeSpecMap = getFeeSpecs(product);

        List<EndorsementSpec> endorsementSpecs = product.getAllSubComponentsByType(EndorsementSpec.class);
        EndorsementSpec endorsementSpec = null;
        for(EndorsementSpec spec : endorsementSpecs){
            if(spec.getCode().equals(endorsement.getCode())){
                   endorsementSpec = spec;
            }
        }

        if(endorsementSpec == null)
            return;

        List<FormulaSpec> formulaSpecs = endorsementSpec.getAllSubComponentsByType(FormulaSpec.class);

        EvalNode node = buildEvalNode(endorsement,policy,formulaSpecs);
        EvalJob endoPremiumJob = evalEngine.endosementPremium();
        endoPremiumJob.process(node);

        endorsement.removeSubComponentsByType(EndorsementFee.class);

        EndorsementFee feeRoot = new EndorsementFee();
        endorsement.getSubComponents().add(feeRoot);

        processResult(node,feeRoot,feeSpecMap);

        node.getSubNodes().forEach(subNode -> {
            EndorsementFee subFee = new EndorsementFee();
            feeRoot.getSubComponents().add(subFee);
            processResult(subNode,subFee,feeSpecMap);
        });
    }


    public EvalNode buildEvalNode(Endorsement bizObject,Policy policy,List<FormulaSpec> formulaSpecs){

        EvalNode root = new EvalNode();
        root.setRefBizObject(bizObject);

        List<FormulaSpec> pFormulaSpecs = Lists.newArrayList();
        List<FormulaSpec> cFormulaSpecs = Lists.newArrayList();
        for(FormulaSpec spec : formulaSpecs){
            if(spec.getPurpose().equals(FormulaPurpose.PREMIUM)){
                cFormulaSpecs.add(spec);
            }else{
                pFormulaSpecs.add(spec);
            }
        }

        root.setExpressions(ModelConverter.converFromFormulaSpecs(pFormulaSpecs));

        Map<String,Object> factors = root.getFactors();
        factors.put(EvalConstant.ENDORSEMENT_TYPE_FACTOR, bizObject.getEndorsementType());
        factors.put(EvalConstant.ENDO_EFF_DATE,new Date(bizObject.getEffectiveDate().getTime()));

        //load policy log
        String endoId = bizObject.getUuid();
        Policy policyLog = logService.loadLogPolicy(endoId, LogType.ISSUE_LOG.name());

        BigDecimal proRate = BigDecimal.ONE;

        if(bizObject instanceof Cancellation){
            Days originalDays = Days.daysBetween(new LocalDate(policyLog.getEffectiveDate()), new LocalDate(policyLog.getExpiredDate()));
            Days offsetDays = Days.daysBetween(new LocalDate(bizObject.getEffectiveDate()), new LocalDate(policyLog.getExpiredDate()));
            proRate = new BigDecimal(offsetDays.getDays() + 1).divide(new BigDecimal(originalDays.getDays() + 1),50, BigDecimal.ROUND_HALF_UP).negate();

        }

        factors.put(EvalConstant.ENDO_PRO_RATE,proRate);

        //generate policy data pair

        Pair rootPair = new Pair(policyLog,policy);

        List<Pair<BaseModel>> subjectPairs = generalPair(policyLog.getSubComponentsByType(InsuredObject.class),policy.getSubComponentsByType(InsuredObject.class));

        rootPair.getSubPairs().addAll(subjectPairs);

        for(Pair<BaseModel> pair : subjectPairs){
            InsuredObject oldSubject = (InsuredObject)pair.getOriginalObj();
            InsuredObject newSubject = (InsuredObject)pair.getNewObj();

            List<Pair<BaseModel>> coveragePairs = generalPair(oldSubject.getSubComponentsByType(Coverage.class), newSubject.getSubComponentsByType(Coverage.class));

            pair.getSubPairs().addAll(coveragePairs);
        }
        //build endorsement rating model
        List<Pair> subPairs = rootPair.getSubPairs();
        for(Pair pair : subPairs){

            List<Pair> subSubPairs = pair.getSubPairs();
            for(Pair subPair : subSubPairs){
                EvalNode cNode = new EvalNode();
                cNode.getFactors().putAll(root.getFactors());

                BaseModel oCoverage = (BaseModel)subPair.getOriginalObj();
                BaseModel nCoverage = (BaseModel)subPair.getNewObj();

                if(oCoverage == null && nCoverage != null){
                    cNode.setRefBizObject(nCoverage);
                    cNode.getFactors().put(EvalConstant.ORIGINAL_PREMIUM, BigDecimal.ZERO);
                    cNode.getFactors().put(EvalConstant.NEW_PREMIUM, ((Coverage)nCoverage).getPolicyFeeByCode(EvalConstant.FEE_TYPE_SNP).getValue());
                }else if(oCoverage != null && nCoverage != null){
                    cNode.setRefBizObject(nCoverage);
                    cNode.getFactors().put(EvalConstant.ORIGINAL_PREMIUM, ((Coverage)nCoverage).getPolicyFeeByCode(EvalConstant.FEE_TYPE_SNP).getValue());
                    cNode.getFactors().put(EvalConstant.NEW_PREMIUM, ((Coverage)nCoverage).getPolicyFeeByCode(EvalConstant.FEE_TYPE_SNP).getValue());
                }else if(oCoverage != null && nCoverage == null){
                    cNode.setRefBizObject(oCoverage);
                    cNode.getFactors().put(EvalConstant.ORIGINAL_PREMIUM, ((Coverage)nCoverage).getPolicyFeeByCode(EvalConstant.FEE_TYPE_SNP).getValue());
                    cNode.getFactors().put(EvalConstant.NEW_PREMIUM,BigDecimal.ZERO);
                }

                cNode.setExpressions(ModelConverter.converFromFormulaSpecs(cFormulaSpecs));

                root.getSubNodes().add(cNode);
            }
        }

        return root;
    }

    private <T extends BaseModel> List<Pair<BaseModel>> generalPair(List<T> originals , List<T> newobjs){

        List<Pair<BaseModel>>  ps = new ArrayList<Pair<BaseModel>>();

        List<BaseModel> temp = new ArrayList<BaseModel>();
        temp.addAll(newobjs);

        for(BaseModel o : originals){

            for(BaseModel n : newobjs){
                if(o.getUuid().equals(n.getUuid())){
                    Pair<BaseModel> p = new Pair<BaseModel>(o,n);
                    ps.add(p);

                    temp.remove(n);
                }
            }
        }
        for(BaseModel t : temp){
            Pair<BaseModel> p = new Pair<BaseModel>(null,t);
            ps.add(p);
        }
        return ps;
    }



    private Map<String, FeeSpec> getFeeSpecs(ProductSpec product){
        Map<String,FeeSpec> feeSpecMap = Maps.newHashMap();

        List<FeeSpec> feeSpecList = product.getAllSubComponentsByType(FeeSpec.class);
        feeSpecList.forEach(feeSpec -> {
            feeSpecMap.put(feeSpec.getCode(),feeSpec);
        });
        return feeSpecMap;
    }

    private void processResult(EvalNode node,EndorsementFee endoFee,Map<String,FeeSpec> feeSpecMap){
        Map<String,Object> values = node.getValues();
        BaseModel bizObject = node.getRefBizObject();

        if(bizObject instanceof Endorsement){
            endoFee.setFeeLevel(EndorsementFeeLevel.POLICY);
            endoFee.setRefBizobjectId(bizObject.getUuid());

        }else if(bizObject instanceof Coverage){
            endoFee.setFeeLevel(EndorsementFeeLevel.COVERAGE);
            endoFee.setRefBizobjectId(bizObject.getUuid());
        }


        values.keySet().forEach(key -> {
            FeeSpec feeSpec = feeSpecMap.get(key);
            if(feeSpec != null){
                Fee fee = new Fee();
                fee.setName(feeSpec.getName());
                fee.setCode(feeSpec.getCode());
                fee.setBizCate(feeSpec.getBizCate().name());
                fee.setValue((BigDecimal) values.get(key));
                fee.setAsPremium(feeSpec.isAsPremium());
                endoFee.getSubComponents().add(fee);
            }
        });
    }

}
