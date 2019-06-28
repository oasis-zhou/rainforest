package rf.product.ds.impl;

import com.google.common.collect.Maps;
import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import groovy.lang.Script;
import org.codehaus.groovy.runtime.InvokerHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import rf.foundation.context.AppContext;
import rf.product.ds.FactorService;
import rf.product.ds.FactorsValidation;
import rf.product.model.FactorSpec;

import java.io.File;
import java.io.FileInputStream;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


@Service
public class FactorsValidationImpl implements FactorsValidation {

    private Map<String, Object> scriptCache = new ConcurrentHashMap<String, Object>();

    private String dsl = null;

    private static Logger logger = LoggerFactory.getLogger(FactorsValidationImpl.class);

    @Autowired
    private FactorService service;

    @Override
    public Map<String, String> verify(Map<String, Object> factors) {
        Map<String,String> msgMap = Maps.newHashMap();

        for(String factorCode : factors.keySet()){
            FactorSpec spec = service.findFactor(factorCode);
            if(spec != null) {
                if (factors.get(factorCode) instanceof String && spec.getValidationExpress() != null) {
                    String msg = evalExpression(spec.getValidationExpress(), factors.get(factorCode));
                    if(msg != null && !msg.isEmpty())
                        msgMap.put(spec.getName(), msg);
                }
            }
        }
        return msgMap;
    }


    private String evalExpression(String expression,Object value){

        String msg = null;
        try {
            Binding binding = new Binding();

            binding.setVariable("VALUE", value);

            binding.setVariable("language", "Groovy");
//            GroovyShell shell = new GroovyShell(binding);
//            Object value = shell.evaluate(expression.getBody());

            String cacheKey = "script" +  Math.abs(expression.hashCode());

            logger.debug("*****Validation Expression==" + preprocessScript(expression) + "\n");

            Script script = null;
            if (scriptCache.containsKey(cacheKey)) {
                script = (Script) scriptCache.get(cacheKey);
            } else {
                script = new GroovyShell().parse(preprocessScript(expression));
                scriptCache.put(cacheKey,script);
            }

            msg = (String)InvokerHelper.createScript(script.getClass(), binding).run();

        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }

        return msg;
    }

    private String preprocessScript(String script) throws Exception{
        if(this.dsl == null)
            this.dsl = loadDsl();

        StringBuffer sb = new StringBuffer("def msgStr = ''\n");
        if(script.contains("&&")){
            String[] subScripts = script.split("&&");
            for(String subScript : subScripts){
                sb.append("msgStr = msgStr + ").append(subScript).append(" + '|'\n");
            }
        }else{
            sb.append("msgStr = msgStr + ").append(script).append(")\n");
        }

        return dsl + "\n"+ sb.toString();
    }

    private String loadDsl() throws Exception{
        String DSL_FILE_PATH = "classpath*:FactorValidation.dsl";
        Resource[] resources = AppContext.getApplicationContext().getResources(DSL_FILE_PATH);

        File file = resources[0].getFile();

        byte[] buffer =new byte[(int) file.length()];
        FileInputStream is =new FileInputStream(file);

        is.read(buffer, 0, buffer.length);

        is.close();
        String str = new String(buffer,"UTF-8");

        return str;
    }
}
