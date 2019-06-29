package rf.policyadmin.model;

import lombok.Data;
import rf.foundation.anno.FieldSpec;
import rf.foundation.model.ModelComponent;
import java.math.BigDecimal;


@Data
public class Deductible extends ModelComponent {
    @FieldSpec(code = "DEDUCTIBLE_AMOUNT", name = "deductible amount")
    private BigDecimal deductibleAmount;
    @FieldSpec(code = "DEDUCTIBLE_PERCENTAGE", name = "deductible percentage")
    private BigDecimal deductibleRate;
    private String formula;

}
