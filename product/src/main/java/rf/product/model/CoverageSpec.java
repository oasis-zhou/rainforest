package rf.product.model;

import lombok.Data;
import rf.foundation.model.ModelComponent;
import rf.product.model.enums.CoverageStatus;

@Data
public class CoverageSpec extends ModelComponent {
    private String code;
    private String name;
    private String description;
    private CoverageStatus status;
    private Boolean primary;
    private String riskPackage;

}
