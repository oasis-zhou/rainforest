package rf.eval;

import com.google.common.collect.Maps;
import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import groovy.lang.Script;
import org.codehaus.groovy.runtime.InvokerHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rf.eval.model.EvalNode;
import rf.eval.model.Expression;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public class LimitEvaluator implements Evaluator {

    private static Logger logger = LoggerFactory.getLogger(LimitEvaluator.class);

    private Map<String, Object> scriptCache = new ConcurrentHashMap<String, Object>();

    @Override
    public Map<String,Object> eval(EvalNode node){

        Map<String,Object> limitAmountM = Maps.newHashMap();

        String pattern = (String)node.getFactors().get(EvalConstant.LIMIT_PATTERN);

        limitAmountM = RoundingUtils.round(getValue(node.getFactors()),null);

        if(EvalConstant.APO.equals(pattern)){
            node.getValues().putAll(limitAmountM);
        }else if(EvalConstant.APUPO.equals(pattern)){
            node.getValues().putAll(limitAmountM);
        }else if(EvalConstant.APUPO_MA.equals(pattern)){
            node.getValues().putAll(limitAmountM);
        }else if(EvalConstant.APUPO_MN.equals(pattern)){
            node.getValues().putAll(limitAmountM);

        }

        node.getValues().putAll(limitAmountM);
        node.getFactors().putAll(limitAmountM);

        node.getCurrentValues().clear();
        node.getCurrentValues().putAll(limitAmountM);

        return limitAmountM;
    }

    public Map getValue(Map<String, Object> factorTable){

        Map<String,BigDecimal> result = Maps.newHashMap();
        String pattern = (String)factorTable.get(EvalConstant.LIMIT_PATTERN);

        String limitKey = EvalConstant.AOA_LIMIT_AMOUNT;
        String limitType = (String)factorTable.get(EvalConstant.LIMIT_INDEMNITY_TYPE);
        if(limitType != null && !limitType.equals(EvalConstant.INDEMNITY_AOA)){
            limitKey = EvalConstant.AOP_LIMIT_AMOUNT;
        }
        BigDecimal amount = BigDecimal.ZERO;

        if(EvalConstant.APO.equals(pattern)){
            BigDecimal limitAmount = (BigDecimal)factorTable.get(EvalConstant.LIMIT_AMOUNT);
            if(limitAmount != null){
                amount = limitAmount;
                result.put(limitKey, amount);
            }
        }else if(EvalConstant.APUPO.equals(pattern)){
            BigDecimal unitAmount = (BigDecimal)factorTable.get(EvalConstant.LIMIT_UNIT_AMOUNT);
            BigDecimal numberOfUnit = (BigDecimal)factorTable.get(EvalConstant.LIMIT_NUMBER_OF_UNIT);
            String unitType = (String)factorTable.get(EvalConstant.LIMIT_UNIT_TYPE);

            amount = unitAmount;
            result.put(limitKey, amount);

        }else if(EvalConstant.APUPO_MA.equals(pattern)){

            BigDecimal maxUnitAmount = (BigDecimal)factorTable.get(EvalConstant.LIMIT_MAX_UNIT_AMOUNT);
            if(maxUnitAmount != null){
                amount = maxUnitAmount;
                result.put(limitKey, amount);
            }
        }else if(EvalConstant.APUPO_MN.equals(pattern)){
            BigDecimal unitAmount = (BigDecimal)factorTable.get(EvalConstant.LIMIT_UNIT_AMOUNT);
            BigDecimal numberOfUnit = (BigDecimal)factorTable.get(EvalConstant.LIMIT_NUMBER_OF_UNIT);
            BigDecimal maxNumberOfUnit = (BigDecimal)factorTable.get(EvalConstant.LIMIT_MAX_NUMBER_OF_UNIT);
            amount = ((BigDecimal)unitAmount).multiply((BigDecimal) maxNumberOfUnit)
                    .divide((BigDecimal) numberOfUnit,50, BigDecimal.ROUND_HALF_UP);
            result.put(limitKey, amount);
        }else if(EvalConstant.FORMULA.equals(pattern)){
            Expression expression = (Expression)factorTable.get(EvalConstant.LIMIT_FORMULA);
            Map r = evalWithLimit(expression,factorTable);
            BigDecimal limit = (BigDecimal)r.get("LIMIT");
            result.put(limitKey, limit);
        }

        return result;
    }

    private Map evalWithLimit(Expression expression, Map<String,Object> factors){

        Map result = null;
        try {
            logger.debug("*****Limit All Factors==" + factors + "\n");

            Binding binding = new Binding();
            List<String> factorKeys = expression.getFactors();

            for(String key : factorKeys){
                binding.setVariable(key, factors.get(key));
            }

            binding.setVariable("language", "Groovy");
//            GroovyShell shell = new GroovyShell(binding);
//            Object value = shell.evaluate(expression.getBody());

            String cacheKey = "script" +  Math.abs(expression.getBody().hashCode());

            Script script = null;
            if (scriptCache.containsKey(cacheKey)) {
                script = (Script) scriptCache.get(cacheKey);
            } else {
                script = new GroovyShell().parse(expression.getBody());
                scriptCache.put(cacheKey,script);
            }

            Object value = (Object) InvokerHelper.createScript(script.getClass(), binding).run();

            result = (Map)value;

        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }

        return result;
    }
}
