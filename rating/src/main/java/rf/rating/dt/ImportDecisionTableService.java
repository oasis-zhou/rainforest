package rf.rating.dt;

import com.google.common.collect.Lists;
import org.joda.time.DateTime;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import rf.foundation.context.AppContext;
import rf.foundation.exception.GenericException;
import rf.foundation.pub.DataType;
import rf.foundation.utils.ExcelReader;

import java.io.File;
import java.util.List;
import java.util.Map;


@Component
public class ImportDecisionTableService {

    public List<DecisionTableSpec> parseRateTables(){
        List<DecisionTableSpec> rateTableSpecs = Lists.newArrayList();

        try {
            String RATE_FILE_PATH = "classpath*:RATE_*.xlsx";
            Resource[] resources = AppContext.getApplicationContext().getResources(RATE_FILE_PATH);

            for(Resource r : resources){
                File file = r.getFile();

                DecisionTableSpec tableSpec = buildDecisionTableSpec(file);

                rateTableSpecs.add(tableSpec);
            }
        }catch (Exception e){
            e.printStackTrace();
            throw new GenericException(e);
        }

        return rateTableSpecs;
    }

    public DecisionTableSpec buildDecisionTableSpec(File file) {
        ExcelReader specReader = new ExcelReader(file, "RATE_SPEC");

        DecisionTableSpec tableSpec = buildDecisionTableSpec(specReader);

        ExcelReader rateReader = new ExcelReader(file, "RATES");
        List<Map<String,String>> mapData = rateReader.getAllMapData();
        mapData.forEach(map -> {
            TableItem item = new TableItem();
            tableSpec.getSubComponents().add(item);
            for (Map.Entry<String, String> me : map.entrySet()) {
                TableColumn column = new TableColumn();
                item.getColumns().add(column);
                column.setName(me.getKey());
                if (me.getValue().matches("^(\\[|\\()(\\d+,\\d+)(\\]|\\))$")) {
                    String temp = me.getValue().replaceAll("\\[|\\]|\\(|\\)", "");
                    String[] values = temp.split(",");
                    column.setMinValue(values[0]);
                    column.setMaxValue(values[1]);

                } else {
                    column.setValue(me.getValue());
                }
            }
        });
        return tableSpec;
    }

    private DecisionTableSpec buildDecisionTableSpec(ExcelReader specReader) {
        DecisionTableSpec table = new DecisionTableSpec();
        table.setStatus(DTStatus.EFFECTIVE);
        List<Map<String,String>> mapData = specReader.getAllMapData();
        for(int index = 0;index < mapData.size();index ++ ){
            if(index == 0){
                Map<String,String> tableName = mapData.get(index);
                table.setName(tableName.get("ITEM_VALUE"));

            }else if(index == 1){
                Map<String,String> tableCode = mapData.get(index);
                table.setCode(tableCode.get("ITEM_VALUE"));

            }else if(index == 2){
                Map<String,String> effDate = mapData.get(index);
                table.setEffectiveDate(new DateTime(effDate.get("ITEM_VALUE")).toDate());

            }else if(index == 3){
                Map<String,String> expDate = mapData.get(index);
                table.setExpiredDate(new DateTime(expDate.get("ITEM_VALUE")).toDate());

            }else{
                Map<String,String> columMap = mapData.get(index);

                ColumnSpec column = new ColumnSpec();

                column.setName(columMap.get("ITEM_VALUE"));
                if(columMap.get("CONDITION") != null &&  !columMap.get("CONDITION").equals(""))
                column.setCondition(Boolean.valueOf(columMap.get("CONDITION")));
                if(columMap.get("SCOPE_PATTERN") != null && !columMap.get("SCOPE_PATTERN").equals(""))
                column.setScopePattern(ScopePattern.valueOf(columMap.get("SCOPE_PATTERN")));
                if(columMap.get("DATA_TYPE") != null && !columMap.get("DATA_TYPE").equals(""))
                column.setDataType(DataType.valueOf(columMap.get("DATA_TYPE")));

                table.getColumnSpecs().put(column.getName(),column);
            }
        }
        return table;
    }

}
