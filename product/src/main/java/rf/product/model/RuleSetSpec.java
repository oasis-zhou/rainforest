package rf.product.model;

import com.google.common.collect.Lists;
import rf.foundation.model.ModelComponent;
import java.util.List;

public class RuleSetSpec extends ModelComponent {
    private String code;
    private String name;
    private String description;
    private List<String> refRules = Lists.newArrayList();

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

    public List<String> getRefRules() {
        return refRules;
    }

    public void setRefRules(List<String> refRules) {
        this.refRules = refRules;
    }
}
