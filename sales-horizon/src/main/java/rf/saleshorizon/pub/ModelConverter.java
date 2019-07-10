package rf.saleshorizon.pub;

import com.google.common.collect.Lists;
import rf.eval.model.Expression;
import rf.product.model.FormulaSpec;
import rf.product.model.RuleSpec;

import java.util.List;


public class ModelConverter {

    public static Expression convertFromRuleSpec(RuleSpec ruleSpec){
        Expression expression = new Expression();
        expression.setCode(ruleSpec.getCode());
        expression.setBody(ruleSpec.getBody());
        expression.setMessage(ruleSpec.getMessage());
        expression.setValue(ruleSpec.getValue());
        expression.setFactors(ruleSpec.getFactors());

        return expression;
    }

    public static List<Expression> convertFromRuleSpecs(List<RuleSpec> ruleSpecs){
        List<Expression> expressionList = Lists.newArrayList();
        for(RuleSpec ruleSpec : ruleSpecs){
            Expression expression = convertFromRuleSpec(ruleSpec);
            expressionList.add(expression);
        }
        return expressionList;
    }

    public static Expression convertFromFormulaSpec(FormulaSpec formulaSpec){
        Expression expression = new Expression();
        expression.setCode(formulaSpec.getCode());
        expression.setBody(formulaSpec.getBody());
        expression.setPurpose(formulaSpec.getPurpose().name());
        expression.setFactors(formulaSpec.getFactors());

        return expression;
    }

    public static List<Expression> converFromFormulaSpecs(List<FormulaSpec> formulaSpecs){
        List<Expression> expressionList = Lists.newArrayList();
        for(FormulaSpec formulaSpec : formulaSpecs){
            Expression expression = convertFromFormulaSpec(formulaSpec);
            expressionList.add(expression);
        }
        return expressionList;
    }



}
