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

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public class RuleEvaluator implements Evaluator{

    private static Logger logger = LoggerFactory.getLogger(FormulaEvaluator.class);

    private Map<String, Object> scriptCache = new ConcurrentHashMap<String, Object>();

    @Override
    public Map<String,Object> eval(EvalNode node){
        Map<String,Object> values = Maps.newHashMap();

        node.getExpressions().forEach((expression) -> {

            boolean r = evalWithRule(expression, node.getFactors());
            if(r){
                values.put(expression.getCode() + "_MSG",expression.getMessage());
                values.put(expression.getCode() + "_VALUE",expression.getValue());
            }
        });

        logger.debug("*****Rule Result==" + values + "\n");

        node.getValues().putAll(values);

        node.getCurrentValues().clear();
        node.getCurrentValues().putAll(values);

        return values;
    }

    private Boolean evalWithRule(Expression expression, Map<String,Object> factors){

        Boolean result = null;
        try {
            logger.debug("*****Rule All Factors==" + factors + "\n");

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


            result = (Boolean)value;

        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }

        return result;
    }
}
