package rf.saleshorizon.ds.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rf.eval.EvalConstant;
import rf.eval.EvalEngine;
import rf.eval.EvalJob;
import rf.eval.model.EvalNode;
import rf.eval.model.Expression;
import rf.foundation.model.BaseModel;
import rf.foundation.utils.ObjFieldUtil;
import rf.policyadmin.model.*;
import rf.product.ds.ProductService;
import rf.product.model.*;
import rf.product.model.enums.LimitPartten;
import rf.saleshorizon.ds.PricingService;
import rf.saleshorizon.pub.Constants;
import rf.saleshorizon.pub.ModelConverter;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @ClassName PricingServiceImpl
 * @Description: TODO
 * @Author zhouzheng
 * @Date 2019/7/2
 * @Version V1.0
 **/
@Service
public class PricingServiceImpl implements PricingService {

    @Autowired
    private ProductService productService;
    @Autowired
    private EvalEngine evalEngine;

    @Override
    public void price(Policy policy){
        ProductSpec product = productService.findProduct(policy.getProductCode());
        Map<String, FeeSpec> feeSpecMap = getFeeSpecs(product);

        EvalNode node = buildEvalNode(policy);
        EvalJob newbizPremiumJob = evalEngine.endosementPremium();
        newbizPremiumJob.process(node);
        processResult(node,feeSpecMap);

        node.getSubNodes().forEach(subNode -> {
            processResult(subNode,feeSpecMap);
        });
    }

    private EvalNode buildEvalNode(Policy policy){
        EvalNode root = new EvalNode();
        root.setRefBizObject(policy);
        root.getFactors().putAll(ObjFieldUtil.getFieldValues(policy));
        root.getFactors().putAll(policy.getDynamicFields());

        ProductSpec product = productService.findProduct(policy.getProductCode());

        List<Expression> feeExpressionList = ModelConverter.converFromFormulaSpecs(getFeeFormulas(product));
//        List<Expression> pExpressionList = ModelConverter.converFromFormulaSpecs(product.getSubComponentsByType(FormulaSpec.class));
//        root.getExpressions().addAll(pExpressionList);
        root.getExpressions().addAll(feeExpressionList);

        Map<String, List<FormulaSpec>> coverageFormulas = getCoverageFormulas(product);
        Map<String, LimitSpec> coverageLimits = getCoverageLimits(product);

        List<InsuredObject> subjectList = policy.getSubComponentsByType(InsuredObject.class);
        for(InsuredObject subject : subjectList){
            List<Coverage> coverageList = subject.getAllSubComponentsByType(Coverage.class);

            for(Coverage coverage : coverageList){
                EvalNode subNode = new EvalNode();
                subNode.setRefBizObject(coverage);
                subNode.getFactors().putAll(root.getFactors());
                subNode.getFactors().putAll(ObjFieldUtil.getFieldValues(subject));
                subNode.getFactors().putAll(subject.getDynamicFields());
                subNode.getFactors().putAll(ObjFieldUtil.getFieldValues(coverage));
                subNode.getFactors().putAll(coverage.getDynamicFields());

                Limit limit = coverage.getLimit();
                LimitSpec limitSpec = coverageLimits.get(coverage.getCode());
                limit.setIndemnityType(limitSpec.getIndemnityType().name());
                limit.setPattern(limitSpec.getPattern());
                if(limitSpec.getPattern().equals(LimitPartten.FORMULA.getValue())) {
                    Expression expression = ModelConverter.convertFromFormulaSpec(getLimitFormula(limitSpec));
                    subNode.getFactors().put(Constants.LIMIT_FOMULA, expression);
                }

                subNode.getFactors().putAll(ObjFieldUtil.getFieldValues(limit));

                Deductible deductible = coverage.getDeductible();
                if(deductible != null)
                    subNode.getFactors().putAll(ObjFieldUtil.getFieldValues(deductible));

                List<Expression> cExpressionList = ModelConverter.converFromFormulaSpecs(coverageFormulas.get(coverage.getCode()));
                subNode.getExpressions().addAll(cExpressionList);

                root.getSubNodes().add(subNode);
            }
        }
        return root;
    }

    private Map<String, List<FormulaSpec>> getCoverageFormulas(ProductSpec product){
        Map<String,List<FormulaSpec>> coverageFormulas = Maps.newHashMap();

        List<CoverageSpec> coverageSpecList = product.getAllSubComponentsByType(CoverageSpec.class);
        coverageSpecList.forEach(coverageSpec -> {
                    List<FormulaSpec> formulaSpecList = coverageSpec.getAllSubComponentsByType(FormulaSpec.class);
                    coverageFormulas.put(coverageSpec.getCode(),formulaSpecList);
                }
        );

        return coverageFormulas;
    }

    private List<FormulaSpec> getFeeFormulas(ProductSpec product){
        List<FormulaSpec> feeFormulas = Lists.newArrayList();

        List<FeeSpec> feeSpecList = product.getAllSubComponentsByType(FeeSpec.class);
        feeSpecList.forEach(feeSpec -> {
                    List<FormulaSpec> formulaSpecList = feeSpec.getAllSubComponentsByType(FormulaSpec.class);
                    feeFormulas.addAll(formulaSpecList);
                }
        );

        return feeFormulas;
    }

    private FormulaSpec getLimitFormula(LimitSpec limitSpec){
        FormulaSpec limitFormula = null;
        List<FormulaSpec> formulaSpecList = limitSpec.getAllSubComponentsByType(FormulaSpec.class);
        if(formulaSpecList.size() > 0)
            limitFormula = formulaSpecList.get(0);

        return limitFormula;
    }

    private Map<String,LimitSpec> getCoverageLimits(ProductSpec product){
        Map<String,LimitSpec> coverageLimitSpecs = Maps.newHashMap();

        List<CoverageSpec> coverageSpecList = product.getAllSubComponentsByType(CoverageSpec.class);
        coverageSpecList.forEach(coverageSpec -> {
                    List<LimitSpec> limitSpecList = coverageSpec.getAllSubComponentsByType(LimitSpec.class);
                    coverageLimitSpecs.put(coverageSpec.getCode(),limitSpecList.get(0));
                }
        );

        return coverageLimitSpecs;
    }

    private Map<String,FeeSpec> getFeeSpecs(ProductSpec product){
        Map<String,FeeSpec> feeSpecMap = Maps.newHashMap();

        List<FeeSpec> feeSpecList = product.getAllSubComponentsByType(FeeSpec.class);
        feeSpecList.forEach(feeSpec -> {
            feeSpecMap.put(feeSpec.getCode(),feeSpec);
        });
        return feeSpecMap;
    }

    private void processResult(EvalNode node,Map<String,FeeSpec> feeSpecMap){
        Map<String,Object> values = node.getValues();
        BaseModel bizObject = node.getRefBizObject();

        Map<String,Fee> feeMap = getExistFees(bizObject);

        values.keySet().forEach(key -> {
            FeeSpec feeSpec = feeSpecMap.get(key);
            if(feeSpec != null){
                Fee fee = feeMap.get(feeSpec.getCode());
                if(fee == null) {
                    fee = new Fee();
                    fee.setName(feeSpec.getName());
                    fee.setCode(feeSpec.getCode());
                    fee.setBizCate(feeSpec.getBizCate().name());
                    fee.setValue((BigDecimal) values.get(key));
                    fee.setAsPremium(feeSpec.isAsPremium());
                    bizObject.getSubComponents().add(fee);
                }else{
                    fee.setValue((BigDecimal) values.get(key));
                }
            }
        });

        if(bizObject instanceof Policy){
            ((Policy)bizObject).setAOAAmount((BigDecimal)values.get(EvalConstant.AOA_LIMIT_AMOUNT));
            ((Policy)bizObject).setAOPAmount((BigDecimal)values.get(EvalConstant.AOP_LIMIT_AMOUNT));
        }
        if(bizObject instanceof Coverage){
            ((Coverage)bizObject).setAOAAmount((BigDecimal)values.get(EvalConstant.AOA_LIMIT_AMOUNT));
            ((Coverage)bizObject).setAOPAmount((BigDecimal) values.get(EvalConstant.AOP_LIMIT_AMOUNT));
        }

    }

    private Map<String,Fee> getExistFees(BaseModel bizObject){
        Map<String,Fee> feeMap = Maps.newHashMap();
        List<Fee> fees = bizObject.getSubComponentsByType(Fee.class);
        for(Fee fee : fees){
            feeMap.put(fee.getCode(),fee);
        }
        return feeMap;
    }
}
