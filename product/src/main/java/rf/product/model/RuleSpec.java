package rf.product.model;

import com.google.common.collect.Lists;
import lombok.Data;
import rf.foundation.model.ModelComponent;
import rf.product.model.enums.RuleType;
import java.util.List;


@Data
public class RuleSpec extends ModelComponent {
    private String code;
    private String name;
    private String description;
    private RuleType type;
    private List<String> factors = Lists.newArrayList();
    private String body;
    private String message;
    private Object value;

}
