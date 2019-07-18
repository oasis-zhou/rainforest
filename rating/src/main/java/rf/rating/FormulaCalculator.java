package rf.eval;

import com.google.common.collect.Maps;
import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import groovy.lang.Script;
import org.codehaus.groovy.runtime.InvokerHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rf.eval.groovy.GroovyScript;
import rf.eval.model.EvalNode;
import rf.eval.model.Expression;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public class FormulaEvaluator implements Evaluator{

    private Map<String, Object> scriptCache = new ConcurrentHashMap<String, Object>();


    private static Logger logger = LoggerFactory.getLogger(FormulaEvaluator.class);

    @Override
    public Map<String,Object> eval(EvalNode node){

        Map<String,Object> values = Maps.newHashMap();

        String purpose = node.getCurrentPurpose();

        for(Expression expression : node.getExpressions()) {
            if(purpose.equals(expression.getPurpose())) {
                Map r = evalWithFormula(expression,node.getFactors());

                r = RoundingUtils.round(r, null);
                values.putAll(r);
            }
        }

        if(values.size() > 0)
        logger.debug("*****Formula Result==" + values + "\n");

        node.getValues().putAll(values);
        node.getFactors().putAll(values);

        node.getCurrentValues().clear();
        node.getCurrentValues().putAll(values);

        return values;
    }

    private Map evalWithFormula(Expression expression,Map<String,Object> factors){

        Map result = null;
        try {
            logger.debug("*****Formula All Factors==" + factors + "\n");

            Binding binding = new Binding();
            List<String> factorKeys = expression.getFactors();

            for(String key : factorKeys){
                binding.setVariable(key, factors.get(key));
            }

            binding.setVariable("language", "Groovy");
//            GroovyShell shell = new GroovyShell(binding);
//            Object value = shell.evaluate(expression.getBody());

//            logger.debug("*****Formula Script==" + preprocessScript(expression.getBody()) + "\n");

            String cacheKey = "script" +  Math.abs(expression.getBody().hashCode());

            Script script = null;
            if (scriptCache.containsKey(cacheKey)) {
                script = (Script) scriptCache.get(cacheKey);
            } else {
                script = new GroovyShell().parse(preprocessScript(expression.getBody()));
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

    private String preprocessScript(String script){

        String rateTableScript = GroovyScript.rateTableScript();

        return rateTableScript + script;
    }

}
