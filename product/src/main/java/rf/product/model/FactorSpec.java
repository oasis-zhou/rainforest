package rf.product.model;

import com.google.common.collect.Maps;
import rf.foundation.model.ModelComponent;
import rf.foundation.pub.DataType;
import rf.product.model.enums.FactorCategory;
import java.util.Map;


public class FactorSpec extends ModelComponent {
    private String code;
    private String name;
    private String description;
    private DataType dataType;
    private FactorCategory category;
    private String validationExpress;
    private Map<String,String> optionValues = Maps.newHashMap();

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public DataType getDataType() {
        return dataType;
    }

    public void setDataType(DataType dataType) {
        this.dataType = dataType;
    }

    public FactorCategory getCategory() {
        return category;
    }

    public void setCategory(FactorCategory category) {
        this.category = category;
    }

    public String getValidationExpress() {
        return validationExpress;
    }

    public void setValidationExpress(String validationExpress) {
        this.validationExpress = validationExpress;
    }

    public Map<String, String> getOptionValues() {
        return optionValues;
    }

    public void setOptionValues(Map<String, String> optionValues) {
        this.optionValues = optionValues;
    }
}
