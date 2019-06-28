package rf.policyadmin.model;

import rf.foundation.model.ModelComponent;
import java.math.BigDecimal;


public class Fee extends ModelComponent {
    private String bizCate;
    private String code;
    private String name;
    private BigDecimal value;
    private boolean asPremium;

    public String getBizCate() {
        return bizCate;
    }

    public void setBizCate(String bizCate) {
        this.bizCate = bizCate;
    }

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

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public boolean isAsPremium() {
        return asPremium;
    }

    public void setAsPremium(boolean asPremium) {
        this.asPremium = asPremium;
    }
}
