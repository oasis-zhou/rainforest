package rf.policyadmin.model;

import rf.foundation.anno.FieldSpec;
import rf.foundation.model.ModelComponent;
import java.math.BigDecimal;


public class Limit extends ModelComponent {

    @FieldSpec(code = "LIMIT_AMOUNT", name = "limit amount")
    private BigDecimal limitAmount;
    @FieldSpec(code = "LIMIT_NUMBER", name = "limit number")
    private BigDecimal limitNumber;
    @FieldSpec(code = "LIMIT_UNIT_AMOUNT", name = "limit unit amount")
    private BigDecimal unitAmount;
    @FieldSpec(code = "LIMIT_NUMBER_OF_UNIT", name = "limit number of unit")
    private BigDecimal numberOfUnit;
    @FieldSpec(code = "LIMIT_UNIT_TYPE", name = "limit unit type")
    private String unitType;
    @FieldSpec(code = "LIMIT_MAX_UNIT_AMOUNT", name = "limit max unit amount")
    private BigDecimal maxUnitAmount;
    @FieldSpec(code = "LIMIT_MAX_NUMBER_OF_UNIT", name = "limit max number of unit")
    private BigDecimal maxNumberOfUnit;
    @FieldSpec(code = "LIMIT_PATTERN", name = "limit pattern")
    private String pattern;
    @FieldSpec(code = "LIMIT_INDEMNITY_TYPE", name = "limit indemnity type")
    private String indemnityType;

    public BigDecimal getLimitAmount() {
        return limitAmount;
    }

    public void setLimitAmount(BigDecimal limitAmount) {
        this.limitAmount = limitAmount;
    }

    public BigDecimal getLimitNumber() {
        return limitNumber;
    }

    public void setLimitNumber(BigDecimal limitNumber) {
        this.limitNumber = limitNumber;
    }

    public BigDecimal getUnitAmount() {
        return unitAmount;
    }

    public void setUnitAmount(BigDecimal unitAmount) {
        this.unitAmount = unitAmount;
    }

    public BigDecimal getNumberOfUnit() {
        return numberOfUnit;
    }

    public void setNumberOfUnit(BigDecimal numberOfUnit) {
        this.numberOfUnit = numberOfUnit;
    }

    public String getUnitType() {
        return unitType;
    }

    public void setUnitType(String unitType) {
        this.unitType = unitType;
    }

    public BigDecimal getMaxUnitAmount() {
        return maxUnitAmount;
    }

    public void setMaxUnitAmount(BigDecimal maxUnitAmount) {
        this.maxUnitAmount = maxUnitAmount;
    }

    public BigDecimal getMaxNumberOfUnit() {
        return maxNumberOfUnit;
    }

    public void setMaxNumberOfUnit(BigDecimal maxNumberOfUnit) {
        this.maxNumberOfUnit = maxNumberOfUnit;
    }

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public String getIndemnityType() {
        return indemnityType;
    }

    public void setIndemnityType(String indemnityType) {
        this.indemnityType = indemnityType;
    }
}
