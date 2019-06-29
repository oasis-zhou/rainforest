package rf.product.model;

import lombok.Data;
import rf.foundation.model.ModelComponent;
import rf.product.model.enums.TagSpecType;

@Data
public class TagSpec extends ModelComponent {
    private String code;
    private String name;
    private TagSpecType type;
    private String content;

}
