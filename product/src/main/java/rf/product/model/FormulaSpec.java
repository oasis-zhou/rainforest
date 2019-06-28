package rf.product.model;

import com.google.common.collect.Lists;
import rf.foundation.model.ModelComponent;
import rf.product.model.enums.FormulaPurpose;
import java.util.List;


public class FormulaSpec extends ModelComponent {
    private String code;
    private String name;
    private String description;
    private FormulaPurpose purpose;
    private List<String> factors = Lists.newArrayList();
    private String body;

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

    public FormulaPurpose getPurpose() {
        return purpose;
    }

    public void setPurpose(FormulaPurpose purpose) {
        this.purpose = purpose;
    }

    public List<String> getFactors() {
        return factors;
    }

    public void setFactors(List<String> factors) {
        this.factors = factors;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
