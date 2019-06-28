package rf.product.model;

import rf.foundation.model.ModelComponent;
import rf.product.model.enums.FeeBizCate;
import rf.product.model.enums.FeeComposeFrom;

public class FeeSpec extends ModelComponent {
    private String code;
    private String name;
    private String description;
    private FeeBizCate bizCate;
    private boolean asPremium;
    private FeeComposeFrom composeFrom;

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

    public FeeBizCate getBizCate() {
        return bizCate;
    }

    public void setBizCate(FeeBizCate bizCate) {
        this.bizCate = bizCate;
    }

    public boolean isAsPremium() {
        return asPremium;
    }

    public void setAsPremium(boolean asPremium) {
        this.asPremium = asPremium;
    }

    public FeeComposeFrom getComposeFrom() {
        return composeFrom;
    }

    public void setComposeFrom(FeeComposeFrom composeFrom) {
        this.composeFrom = composeFrom;
    }
}
