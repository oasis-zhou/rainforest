package rf.product.model;

import lombok.Data;
import rf.foundation.model.ModelComponent;
import rf.product.model.enums.EndorsementType;

@Data
public class EndorsementSpec extends ModelComponent {

    private String name;
    private String code;
    private EndorsementType endorsementType;
    private String description;

}
