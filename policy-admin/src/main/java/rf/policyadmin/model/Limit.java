package rf.policyadmin.model;

import lombok.Data;
import rf.foundation.anno.FieldSpec;
import rf.foundation.model.ModelComponent;
import java.math.BigDecimal;


@Data
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

}
