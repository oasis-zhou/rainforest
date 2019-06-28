package rf.policyadmin.model;

import rf.foundation.anno.FieldSpec;
import rf.foundation.model.ModelComponent;
import java.math.BigDecimal;


public class Deductible extends ModelComponent {
    @FieldSpec(code = "DEDUCTIBLE_AMOUNT", name = "deductible amount")
    private BigDecimal deductibleAmount;
    @FieldSpec(code = "DEDUCTIBLE_PERCENTAGE", name = "deductible percentage")
    private BigDecimal deductibleRate;
    private String formula;

    public BigDecimal getDeductibleAmount() {
        return deductibleAmount;
    }

    public void setDeductibleAmount(BigDecimal deductibleAmount) {
        this.deductibleAmount = deductibleAmount;
    }

    public BigDecimal getDeductibleRate() {
        return deductibleRate;
    }

    public void setDeductibleRate(BigDecimal deductibleRate) {
        this.deductibleRate = deductibleRate;
    }

    public String getFormula() {
        return formula;
    }

    public void setFormula(String formula) {
        this.formula = formula;
    }
}
