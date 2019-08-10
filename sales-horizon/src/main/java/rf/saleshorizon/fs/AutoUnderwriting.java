package rf.saleshorizon.fs;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import rf.rating.RatingEngine;
import rf.rating.RatingJob;
import rf.rating.model.RatingNode;
import rf.rating.model.Expression;
import rf.foundation.exception.GenericException;
import rf.foundation.pub.FunctionSlice;
import rf.foundation.utils.ObjFieldUtil;
import rf.policyadmin.model.InsuredObject;
import rf.policyadmin.model.Policy;
import rf.product.model.ProductSpec;
import rf.product.model.RuleSetSpec;
import rf.product.model.RuleSpec;
import rf.saleshorizon.pub.Constants;
import rf.saleshorizon.pub.ModelConverter;

import java.util.List;
import java.util.Map;

@Component
public class AutoUnderwriting implements FunctionSlice<Policy> {

    @Autowired
    private ProductService productService;
    @Autowired
    private RatingEngine ratingEngine;

    @Override
    public void execute(Policy policy, Map<String, Object> context){

        String ruleSetCode = (String)context.get(Constants.AUTO_UNDERWRITING_RULE_SET);

        ProductSpec product = productService.pullFromChain(policy.getProductCode());

        List<Expression> expressionList = ModelConverter.convertFromRuleSpecs(getRules(product, ruleSetCode));

        RatingNode node = buildEvalNode(policy, expressionList);
        RatingJob autoUnderwritingJob = ratingEngine.ruleJob();
        autoUnderwritingJob.process(node);

        Map<String,Object> result = node.getValues();

        String underwritingReason = "";
        for(Map.Entry entry : result.entrySet()){
            if(entry.getValue() != null){
                underwritingReason = underwritingReason + entry.getValue().toString();
            }
        }
        if(underwritingReason != null && underwritingReason.length() > 0)
            throw new GenericException(30010L,underwritingReason);

//        policy.getUnderwritingReason().putAll(result);
    }

    private RatingNode buildEvalNode(Policy policy, List<Expression> expressionList){
        RatingNode root = new RatingNode();
        root.setRefBizObject(policy);
        root.getFactors().putAll(ObjFieldUtil.getFieldValues(policy));
        root.getFactors().putAll(policy.getDynamicFields());

        List<InsuredObject> subjectList = policy.getSubComponentsByType(InsuredObject.class);
        for(InsuredObject subject : subjectList){

            RatingNode subNode = new RatingNode();
            subNode.setRefBizObject(subject);
            subNode.getFactors().putAll(root.getFactors());
            subNode.getFactors().putAll(ObjFieldUtil.getFieldValues(subject));
            subNode.getFactors().putAll(subject.getDynamicFields());
            subNode.getExpressions().addAll(expressionList);

            root.getSubNodes().add(subNode);
        }
        return root;
    }

    private List<RuleSpec> getRules(ProductSpec product,String ruleSetCode){
        final List<RuleSpec> ruleSpecList = Lists.newArrayList();

        List<RuleSetSpec> ruleSetSpecList = product.getAllSubComponentsByType(RuleSetSpec.class);
        ruleSetSpecList.forEach(ruleSetSpec -> {
            if (ruleSetSpec.getCode().equals(ruleSetCode))
                ruleSpecList.addAll(getRulesOfRuleSet(ruleSetSpec, product.getSubComponentsByType(RuleSpec.class)));
        });

        return ruleSpecList;
    }

    private List<RuleSpec> getRulesOfRuleSet(RuleSetSpec ruleSetSpec,List<RuleSpec> rules){

        Map<String,RuleSpec> ruleSpecMap = Maps.newHashMap();
        rules.forEach(ruleSpec -> {
            ruleSpecMap.put(ruleSpec.getCode(), ruleSpec);
        });

        List<RuleSpec> ruleSpecList = Lists.newArrayList();
        ruleSetSpec.getRefRules().forEach(ruleCode -> {
            ruleSpecList.add(ruleSpecMap.get(ruleCode));
        });

        return ruleSpecList;
    }

}
