package rf.product.model;

import lombok.Data;
import rf.foundation.model.ModelComponent;
import rf.product.model.enums.EndorsementType;
import rf.product.model.enums.RatingLevel;

@Data
public class EndorsementSpec extends ModelComponent {

    private String name;
    private String code;
    private EndorsementType endorsementType;
    private RatingLevel ratingLevel;
    private String description;

}
