package rf.product.model;

import lombok.Data;
import rf.foundation.model.ModelComponent;
import rf.product.model.enums.FeeBizCate;
import rf.product.model.enums.FeeComposeFrom;

@Data
public class FeeSpec extends ModelComponent {
    private String code;
    private String name;
    private String description;
    private FeeBizCate bizCate;
    private boolean asPremium;
    private FeeComposeFrom composeFrom;

}
