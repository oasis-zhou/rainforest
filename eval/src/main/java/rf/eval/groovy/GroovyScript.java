package rf.eval.groovy;


public class GroovyScript {

    public static String rateTableScript(){
        String script = "/************Rate Table Closure***********/ \n" +
                        " def rateTableScript(){\n" +
                        "    rf.eval.dt.DecisionTableService service = rf.foundation.context.AppContext.getBean(rf.eval.dt.DecisionTableService.class);\n" +
                        "    def script = { tableName,conditions -> service.findRateValue(tableName,conditions) }\n" +
                        "    return script\n" +
                        " }\n" +
                        " def RateTable = rateTableScript()\n";
        return script;
    }
}
