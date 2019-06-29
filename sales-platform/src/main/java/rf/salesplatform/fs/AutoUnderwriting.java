package rf.salesplatform.fs;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import rf.eval.EvalEngine;
import rf.eval.EvalJob;
import rf.eval.model.EvalNode;
import rf.eval.model.Expression;
import rf.foundation.exception.GenericException;
import rf.foundation.pub.FunctionSlice;
import rf.foundation.utils.ObjFieldUtil;
import rf.policyadmin.model.InsuredObject;
import rf.policyadmin.model.Policy;
import rf.product.ds.ProductService;
import rf.product.model.ProductSpec;
import rf.product.model.RuleSetSpec;
import rf.product.model.RuleSpec;
import rf.salesplatform.pub.ModelConverter;
import rf.salesplatform.pub.Constants;
import java.util.List;
import java.util.Map;


@Component
public class AutoUnderwriting implements FunctionSlice<Policy> {

    @Autowired
    private ProductService productService;
    @Autowired
    private EvalEngine evalEngine;


    @Override
    public void execute(Policy policy, Map<String, Object> context){

        String ruleSetCode = (String)context.get(Constants.AUTO_UNDERWRITING_RULE_SET);

        ProductSpec product = productService.findProduct(policy.getProductCode());

        List<Expression> expressionList = ModelConverter.convertFromRuleSpecs(getRules(product, ruleSetCode));

        EvalNode node = buildEvalNode(policy, expressionList);
        EvalJob autoUnderwritingJob = evalEngine.ruleJob();
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

    private EvalNode buildEvalNode(Policy policy, List<Expression> expressionList){
        EvalNode root = new EvalNode();
        root.setRefBizObject(policy);
        root.getFactors().putAll(ObjFieldUtil.getFieldValues(policy));
        root.getFactors().putAll(policy.getDynamicFields());

        List<InsuredObject> subjectList = policy.getSubComponentsByType(InsuredObject.class);
        for(InsuredObject subject : subjectList){

            EvalNode subNode = new EvalNode();
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
