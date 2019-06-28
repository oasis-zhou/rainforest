package rf.product.model;

import com.google.common.collect.Lists;
import rf.foundation.model.ModelComponent;
import java.util.List;

public class DeductibleSpec extends ModelComponent {
    private String pattern;
    private String defaultValue;
    private Boolean fixed;
    private List<String> valueOptions = Lists.newArrayList();

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public Boolean getFixed() {
        return fixed;
    }

    public void setFixed(Boolean fixed) {
        this.fixed = fixed;
    }

    public List<String> getValueOptions() {
        return valueOptions;
    }

    public void setValueOptions(List<String> valueOptions) {
        this.valueOptions = valueOptions;
    }
}
