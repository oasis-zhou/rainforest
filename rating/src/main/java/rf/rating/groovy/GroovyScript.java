package rf.rating.groovy;


public class GroovyScript {

    public static String rateTableScript(){
        String script = "/************Rate Table Closure***********/ \n" +
                        " def rateTableScript(){\n" +
                        "    rf.rating.dt.DecisionTableService service = rf.foundation.context.AppContext.getBean(rf.rating.dt.DecisionTableService.class);\n" +
                        "    def script = { tableName,conditions -> service.findRateValue(tableName,conditions) }\n" +
                        "    return script\n" +
                        " }\n" +
                        " def RateTable = rateTableScript()\n";
        return script;
    }
}
