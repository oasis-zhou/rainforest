package rf.product.repository;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import rf.foundation.context.AppContext;
import rf.foundation.exception.GenericException;
import rf.foundation.pub.DataType;
import rf.foundation.utils.ExcelReader;
import rf.product.model.FactorSpec;
import rf.product.model.enums.FactorCategory;

import java.io.File;
import java.util.List;
import java.util.Map;


@Component
public class ImportFactorService {

    public List<FactorSpec> parseFactors(){
        List<FactorSpec> factorSpecs = Lists.newArrayList();

        try {
            String FACTOR_FILE_PATH = "classpath*:Factor.xlsx";
            Resource[] resources = AppContext.getApplicationContext().getResources(FACTOR_FILE_PATH);

            File file = resources[0].getFile();

            ExcelReader reader = new ExcelReader(file, "FACTORS");

            List<Map<String,String>> mapData = reader.getAllMapData();
            mapData.forEach(map -> {
                if(map.get("FACTOR_CODE") != null && !map.get("FACTOR_CODE").equals("")) {
                    FactorSpec spec = new FactorSpec();
                    spec.setCode(map.get("FACTOR_CODE"));
                    spec.setName(map.get("FACTOR_NAME"));
                    spec.setCategory(FactorCategory.valueOf(map.get("CATEGORY")));
                    spec.setDataType(DataType.valueOf(map.get("DATA_TYPE")));
                    String optionValues = map.get("OPTION_VALUES");
                    if(optionValues != null && !optionValues.equals("")){
                        Map<String,String> values = Maps.newHashMap();
                        for (String value : optionValues.split(",")) {
                            String[] temps = value.split(":");
                            values.put(temps[0],temps[1]);
                        }
                        spec.setOptionValues(values);
                    }

                    spec.setValidationExpress(map.get("VALIDATION"));
                    spec.setDescription(map.get("DESCRIPTION"));

                    factorSpecs.add(spec);
                }
            });
        }catch (Exception e){
            e.printStackTrace();
            throw new GenericException(e);
        }

        return factorSpecs;
    }

}
