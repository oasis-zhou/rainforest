package rf.product.model;

import com.google.common.collect.Lists;
import lombok.Data;
import rf.foundation.model.ModelComponent;
import java.util.List;

@Data
public class RuleSetSpec extends ModelComponent {
    private String code;
    private String name;
    private String description;
    private List<String> refRules = Lists.newArrayList();

}
