package rf.saleshorizon.fs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import rf.rating.RatingEngine;
import rf.rating.RatingJob;
import rf.rating.model.RatingNode;
import rf.rating.model.Expression;
import rf.foundation.exception.GenericException;
import rf.foundation.pub.FunctionSlice;
import rf.foundation.utils.ObjFieldUtil;
import rf.policyadmin.model.Endorsement;
import rf.policyadmin.model.EndorsementPolicy;
import rf.policyadmin.model.Policy;
import rf.policyadmin.model.enums.ContractStatus;
import rf.product.model.EndorsementSpec;
import rf.product.model.ProductSpec;
import rf.product.model.RuleSpec;
import rf.saleshorizon.ds.ProductService;
import rf.saleshorizon.pub.Constants;
import rf.saleshorizon.pub.ModelConverter;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Component
public class EndorsementValidation implements FunctionSlice<Endorsement> {

    @Autowired
    private ProductService productService;
    @Autowired
    private RatingEngine ratingEngine;

    @Override
    public void execute(Endorsement endorsement, Map<String, Object> context){
        EndorsementPolicy endoPolicy = (EndorsementPolicy)context.get(Constants.ENDORSEMENT_POLICY_OBJECT);
        Policy policy = endoPolicy.getOriginal();

        //verify the policy contract status
        if(policy.getContractStatus().equals(ContractStatus.TERMINAL))
            throw new GenericException(30011L);
        //verify the endorsement effective date
        verifyEffate(endorsement,policy);
        //verify the endorsement application date
        verifyApplyDate(endorsement,policy);

        ProductSpec product = productService.pullFromChain(endorsement.getProductCode());
        List<EndorsementSpec> endorsementSpecs = product.getAllSubComponentsByType(EndorsementSpec.class);
        EndorsementSpec endorsementSpec = null;
        for(EndorsementSpec spec : endorsementSpecs){
            if(spec.getCode().equals(endorsement.getCode())){
                endorsementSpec = spec;
            }
        }

        if(endorsementSpec == null)
            throw new GenericException(30006L);

        List<RuleSpec> ruleSpecs = endorsementSpec.getSubComponentsByType(RuleSpec.class);

        List<Expression> expressionList = ModelConverter.convertFromRuleSpecs(ruleSpecs);

        RatingNode node = buildEvalNode(endorsement,policy, expressionList);
        RatingJob endoValidationJob = ratingEngine.ruleJob();
        endoValidationJob.process(node);

        Map<String,Object> result = node.getValues();

        if(!result.isEmpty())
            throw new GenericException(result.toString());
    }

    private RatingNode buildEvalNode(Endorsement endorsement, Policy policy, List<Expression> expressionList){
        RatingNode root = new RatingNode();
        root.setRefBizObject(endorsement);
        root.getFactors().putAll(ObjFieldUtil.getFieldValues(policy));
        root.getFactors().putAll(policy.getDynamicFields());
        root.getFactors().putAll(ObjFieldUtil.getFieldValues(endorsement));
        root.getExpressions().addAll(expressionList);

        return root;
    }

    private void verifyEffate(Endorsement endorsement,Policy policy){
        Date polEff = new Date(policy.getEffectiveDate().getTime());
        Date polExp = new Date(policy.getExpiredDate().getTime());

        Date effDate = endorsement.getEffectiveDate();

        //check endorsement effective date
        if (effDate == null) {
            throw new GenericException(30003L);
        }
        //endorsement effective date must in the old POI
        if (effDate.before(polEff) || effDate.after(polExp)) {
            throw  new GenericException(30004L);
        }
    }

    private void verifyApplyDate(Endorsement endorsement,Policy policy){
        Date polEff = new Date(policy.getEffectiveDate().getTime());
        Date polExp = new Date(policy.getExpiredDate().getTime());

        Date applyDate = endorsement.getApplicationDate();

        //endorsement applicationDate date must in the old POI
        if (applyDate.before(polEff) || applyDate.after(polExp)) {
            throw  new GenericException(30013L);
        }
    }


}
