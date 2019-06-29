package rf.product.model;

import lombok.Data;
import rf.foundation.model.ModelComponent;
import rf.product.model.enums.InsuredObjectCate;

@Data
public class InsuredObjectSpec extends ModelComponent {
    private InsuredObjectCate cate;
    private boolean multiple;

}
