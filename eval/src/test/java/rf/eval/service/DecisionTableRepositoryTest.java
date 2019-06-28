package rf.eval.service;

import com.google.common.collect.Maps;
import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import rf.eval.dt.*;
import rf.foundation.pub.DataType;
import rf.foundation.utils.JsonHelper;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * Created by zhengzhou on 16/4/19.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class DecisionTableRepositoryTest {

    @Autowired
    private JsonHelper jsonHelper;

    @Autowired
    private DecisionTableService decisionTableService;

    @Autowired
    private ImportDecisionTableService importDecisionTableService;

//    @Test
    public void perestRateTable() throws Exception{

        DecisionTableSpec table1 = initTable1();
        DecisionTableSpec table2 = initTable2();

        decisionTableService.saveRateTable(table1);
        decisionTableService.saveRateTable(table2);

        DecisionTableSpec tableSpec = decisionTableService.findRateTable("RATE_COM_HC");
         tableSpec = decisionTableService.findRateTable("RATE_COM_HC");
        System.out.print("Rate Table:" + jsonHelper.toJSON(tableSpec));
    }
//    @Test
    public void findRate(){
        Map condition = Maps.newHashMap();
        condition.put("INSURED_AGE","30");

        BigDecimal rate = decisionTableService.findRateValue("RATE_COM_HC",condition);

        System.out.print("Rate:" + rate);
    }

    @Test
    public void perestRateTableFromExcel(){
        List<DecisionTableSpec> tables = importDecisionTableService.parseRateTables();

        for(DecisionTableSpec spec : tables){
            decisionTableService.saveRateTable(spec);
        }

    }

//    @Test
    public void scopePattern(){
        String reg = "^(\\[|\\()(\\d+,\\d+)(\\]|\\))$";
        String str = "(0,5)";

        Boolean r = str.matches(reg);
            System.out.print("Result:" + r);

        String newStr = str.replaceAll("\\[|\\]|\\(|\\)","");
        System.out.print("new Str:" + newStr);

    }

    private DecisionTableSpec initTableMSH(){
        DecisionTableSpec table1 = new DecisionTableSpec();
        table1.setName("民生医保通费率表");
        table1.setCode("RATE_MSH");
        table1.setEffectiveDate(new DateTime(2017,1,1,0,0,0).toDate());
        table1.setExpiredDate(new DateTime(2017,12,31,0,0,0).toDate());
        table1.setStatus(DTStatus.EFFECTIVE);

        ColumnSpec col1 = new ColumnSpec();
        col1.setName("INSURED_AGE");
        col1.setDataType(DataType.NUMBER);
        col1.setCondition(true);
        col1.setScopePattern(ScopePattern.OPEN_TO_CLOSE);

        ColumnSpec col2 = new ColumnSpec();
        col2.setName("SOCIAL_INSURANCE");
        col2.setDataType(DataType.NUMBER);
        col2.setCondition(true);

        ColumnSpec col3 = new ColumnSpec();
        col3.setName("PREMIUM");
        col3.setDataType(DataType.NUMBER);
        col3.setCondition(false);

        table1.getColumnSpecs().put(col1.getName(),col1);
        table1.getColumnSpecs().put(col2.getName(),col2);
        table1.getColumnSpecs().put(col3.getName(),col3);

        TableItem item1 = new TableItem();
        table1.getSubComponents().add(item1);

        TableColumn c1 = new TableColumn();
        c1.setName("INSURED_AGE");
        c1.setMaxValue("20");
        c1.setMinValue("0");
        item1.getColumns().add(c1);

        TableColumn c2 = new TableColumn();
        c2.setName("INSURED_AGE");
        c2.setValue("1");
        item1.getColumns().add(c1);

        TableColumn c3 = new TableColumn();
        c3.setName("PREMIUM");
        c3.setValue("50.00");
        item1.getColumns().add(c3);



        return table1;
    }

    private DecisionTableSpec initTable1(){
        DecisionTableSpec table1 = new DecisionTableSpec();
        table1.setName("一般医疗保险金费率表");
        table1.setCode("RATE_COM_HC");
        table1.setEffectiveDate(new DateTime(2017,1,1,0,0,0).toDate());
        table1.setExpiredDate(new DateTime(2017,12,31,0,0,0).toDate());
        table1.setStatus(DTStatus.EFFECTIVE);

        ColumnSpec col1 = new ColumnSpec();
        col1.setName("INSURED_AGE");
        col1.setDataType(DataType.NUMBER);
        col1.setCondition(true);
        col1.setScopePattern(ScopePattern.OPEN_TO_CLOSE);

        ColumnSpec col2 = new ColumnSpec();
        col2.setName("PREMIUM");
        col2.setDataType(DataType.NUMBER);
        col2.setCondition(false);

        table1.getColumnSpecs().put(col1.getName(),col1);
        table1.getColumnSpecs().put(col2.getName(),col2);

        TableItem item1 = new TableItem();
        table1.getSubComponents().add(item1);

        TableColumn c1 = new TableColumn();
        c1.setName("INSURED_AGE");
        c1.setMaxValue("20");
        c1.setMinValue("0");
        item1.getColumns().add(c1);

        TableColumn c2 = new TableColumn();
        c2.setName("PREMIUM");
        c2.setValue("50.00");
        item1.getColumns().add(c2);

        TableItem item2 = new TableItem();
        table1.getSubComponents().add(item2);

        TableColumn c3 = new TableColumn();
        c3.setName("INSURED_AGE");
        c3.setMaxValue("40");
        c3.setMinValue("20");
        item2.getColumns().add(c3);

        TableColumn c4 = new TableColumn();
        c4.setName("PREMIUM");
        c4.setValue("100.00");
        item2.getColumns().add(c4);

        TableItem item3 = new TableItem();
        table1.getSubComponents().add(item3);

        TableColumn c5 = new TableColumn();
        c5.setName("INSURED_AGE");
        c5.setMaxValue("60");
        c5.setMinValue("40");
        item3.getColumns().add(c5);

        TableColumn c6 = new TableColumn();
        c6.setName("PREMIUM");
        c6.setValue("150.00");
        item3.getColumns().add(c6);

        return table1;
    }

    private DecisionTableSpec initTable2(){
        DecisionTableSpec table1 = new DecisionTableSpec();
        table1.setName("恶性肿瘤医疗保险金费率表");
        table1.setCode("RATE_CA");
        table1.setEffectiveDate(new DateTime(2017,1,1,0,0,0).toDate());
        table1.setExpiredDate(new DateTime(2017,12,31,0,0,0).toDate());
        table1.setStatus(DTStatus.EFFECTIVE);

        ColumnSpec col1 = new ColumnSpec();
        col1.setName("INSURED_AGE");
        col1.setDataType(DataType.NUMBER);
        col1.setCondition(true);
        col1.setScopePattern(ScopePattern.OPEN_TO_CLOSE);

        ColumnSpec col2 = new ColumnSpec();
        col2.setName("PREMIUM");
        col2.setDataType(DataType.NUMBER);
        col2.setCondition(false);

        table1.getColumnSpecs().put(col1.getName(),col1);
        table1.getColumnSpecs().put(col2.getName(),col2);

        TableItem item1 = new TableItem();
        table1.getSubComponents().add(item1);

        TableColumn c1 = new TableColumn();
        c1.setName("INSURED_AGE");
        c1.setMaxValue("20");
        c1.setMinValue("0");
        item1.getColumns().add(c1);

        TableColumn c2 = new TableColumn();
        c2.setName("PREMIUM");
        c2.setValue("80.00");
        item1.getColumns().add(c2);

        TableItem item2 = new TableItem();
        table1.getSubComponents().add(item2);

        TableColumn c3 = new TableColumn();
        c3.setName("INSURED_AGE");
        c3.setMaxValue("40");
        c3.setMinValue("20");
        item2.getColumns().add(c3);

        TableColumn c4 = new TableColumn();
        c4.setName("PREMIUM");
        c4.setValue("120.00");
        item2.getColumns().add(c4);

        TableItem item3 = new TableItem();
        table1.getSubComponents().add(item3);

        TableColumn c5 = new TableColumn();
        c5.setName("INSURED_AGE");
        c5.setMaxValue("60");
        c5.setMinValue("40");
        item3.getColumns().add(c5);

        TableColumn c6 = new TableColumn();
        c6.setName("PREMIUM");
        c6.setValue("160.00");
        item3.getColumns().add(c6);

        return table1;
    }
}
