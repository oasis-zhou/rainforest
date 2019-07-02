package rf.eval.dt;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import rf.eval.dt.repository.DecisionTableDao;
import rf.eval.dt.repository.pojo.TDecisionTable;
import rf.foundation.cache.GuavaCacheManager;
import rf.foundation.exception.GenericException;
import rf.foundation.utils.JsonHelper;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static rf.foundation.pub.DataType.*;


@Component
public class DecisionTableService {

    private static Logger logger = LoggerFactory.getLogger(DecisionTableService.class);

    @Autowired
    private DecisionTableDao decisionTableDao;
    @Autowired
    private JsonHelper jsonHelper;
    @Autowired
    private GuavaCacheManager guavaCacheManager;

    public DecisionTableSpec findRateTable(String tableCode) {
        DecisionTableSpec table = (DecisionTableSpec) guavaCacheManager.getCacheByKey(tableCode);

        if(table == null) {
            TDecisionTable po = decisionTableDao.findByCode(tableCode);
            logger.debug("Load rate table " + tableCode + " from database.\n");

            if (po != null && po.getContent() != null) {
                table = jsonHelper.fromJSON(po.getContent(), DecisionTableSpec.class);
                guavaCacheManager.putCache(tableCode,table);
            }
        }

        return table;
    }

    public Map<String,Object> findRateValues(String tableCode,Map conditions){
        DecisionTableSpec table = this.findRateTable(tableCode);
        Map<String,Object> values = this.findMutlpleItemsValue(table,conditions);

        return values;
    }

    public BigDecimal findRateValue(String tableCode,Map conditions){
        BigDecimal value = BigDecimal.ZERO;

        DecisionTableSpec table = this.findRateTable(tableCode);
        Map<String,Object> values = this.findSingleItemValue(table,conditions);
        for(String key : values.keySet()){
            value = new BigDecimal(values.get(key).toString());
        }
        return value;
    }

    public void saveRateTable(DecisionTableSpec table){

        TDecisionTable po = decisionTableDao.findByCode(table.getCode());
        if(po == null) {
            po = new TDecisionTable();
            po.setUuid(table.getUuid());
        }

        String content = jsonHelper.toJSON(table);

        po.setCode(table.getCode());
        po.setName(table.getName());
        po.setEffectiveDate(table.getEffectiveDate());
        po.setExpiredDate(table.getExpiredDate());
        po.setStatus(table.getStatus().name());

        po.setContent(content);

        decisionTableDao.save(po);

        guavaCacheManager.putCache(table.getCode(),table);
    }

    public Map<String,Object> findSingleItemValue(DecisionTableSpec table, Map<String,Object> conditions){

        Map<String,Object> value = Maps.newHashMap();

        List<TableItem> items = findMatchItems(conditions,table);

        if(items.size() > 0){
            TableItem item = items.get(0);

            for(TableColumn column : item.getColumns()) {
                ColumnSpec spec = table.getColumnSpecs().get(column.getName());

                if(spec.getCondition() == null || !spec.getCondition()){
                    value.put(column.getName(),column.getValue());
                }
            }
        }

        return value;
    }

    public Map<String,Object> findMutlpleItemsValue(DecisionTableSpec table, Map<String,Object> conditions){

        Map<String,Object> values = Maps.newHashMap();

        List<TableItem> items = findMatchItems(conditions, table);

        for(TableItem item : items){
            Map<String,Object> value = Maps.newHashMap();
            for(TableColumn column : item.getColumns()) {
                ColumnSpec spec = table.getColumnSpecs().get(column.getName());
                if(spec.getCondition() == null || !spec.getCondition()){
                    value.put(column.getName(),column.getValue());
                }

            }
            values.putAll(value);
        }

        return values;
    }



    private List<TableItem> findMatchItems(Map<String,Object> conditions,DecisionTableSpec table){

        List<TableItem> its = Lists.newArrayList();

        List<TableItem> items = table.getSubComponentsByType(TableItem.class);

        if(items.size() == 0)
            throw new GenericException("No data match");


            for(TableItem item : items){
                boolean isMatch = true;
                for(TableColumn column : item.getColumns()){
                    ColumnSpec spec = table.getColumnSpecs().get(column.getName());
                    Object condition = conditions.get(column.getName());
                    if(spec.getCondition() != null && spec.getCondition() && spec.getScopePattern() != null && condition != null){
                        isMatch = compareForScope(column.getMaxValue(),column.getMinValue(),condition,spec.getDataType().name(),spec.getScopePattern().name());
                    }else if(spec.getCondition() != null && spec.getCondition() && spec.getScopePattern() == null && condition != null){
                        isMatch = compare(column.getValue(),condition,spec.getDataType().name());
                    }
                    if(!isMatch)
                        break;
                }

                if(isMatch){
                    its.add(item);
                }
            }


        return its;
    }

    private boolean compare(Object value,Object condition,String dataType){
        boolean isMatch = true;
        int r = 0;

        if(dataType.equals(DATE.name())) {
            Date c = (Date)condition;
            Date v = (Date)value;

            r = c.compareTo(v);
        }

        if(dataType.equals(STRING.name())) {
            String c = (String)condition;
            String v = (String)value;

            r = c.compareTo(v);
        }

        if(dataType.equals(NUMBER.name())) {
            BigDecimal c = new BigDecimal(condition.toString());
            BigDecimal v = new BigDecimal(value.toString());

            r = c.compareTo(v);
        }

        if(dataType.equals(STRING_LIST.name())) {
            String c = (String)condition;
            String v = (String)value;
            List<String> lists = Arrays.asList(v.split(","));
            if (lists.contains(c)){
                return isMatch;
            }
            return false;
        }

        if(r != 0)
            isMatch = false;


        return isMatch;
    }

    private boolean compareForScope(Object max,Object min,Object condition,String dataType,String scopePattern){
        boolean isMatch = true;
        int up = 0;
        int down = 0;

        if(dataType.equals(DATE.name())) {
            Date c = (Date)condition;
            Date upData = (Date)max;
            Date downData = (Date)min;

            up = c.compareTo(upData);
            down = c.compareTo(downData);
        }

        if(dataType.equals(STRING.name())) {
            String c = (String)condition;
            String upData = (String)max;
            String downData = (String)min;

            up = c.compareTo(upData);
            down = c.compareTo(downData);
        }

        if(dataType.equals(NUMBER.name())) {
            BigDecimal c = new BigDecimal(condition.toString());
            BigDecimal upData = new BigDecimal(max.toString());
            BigDecimal downData = new BigDecimal(min.toString());

            up = c.compareTo(upData);
            down = c.compareTo(downData);
        }

        if(scopePattern.equals(ScopePattern.CLOSE_TO_CLOSE.name())){
            if(!(up <= 0 && down >= 0)){
                isMatch = false;
            }
        }else if(scopePattern.equals(ScopePattern.CLOSE_TO_OPEN.name())){
            if(!(up < 0 && down >= 0)){
                isMatch = false;
            }
        }else if(scopePattern.equals(ScopePattern.OPEN_TO_CLOSE.name())){
            if(!(up <= 0 && down > 0)){
                isMatch = false;
            }
        }else if(scopePattern.equals(ScopePattern.OPEN_TO_OPEN.name())){
            if(!(up < 0 && down > 0)){
                isMatch = false;
            }
        }

        return isMatch;
    }




}
