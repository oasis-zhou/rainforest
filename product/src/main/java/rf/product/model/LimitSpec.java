package rf.product.model;

import com.google.common.collect.Lists;
import lombok.Data;
import rf.foundation.model.ModelComponent;
import rf.product.model.enums.IndemnityType;
import java.util.List;

@Data
public class LimitSpec extends ModelComponent {
    private IndemnityType indemnityType;
    private String pattern;
    private String defaultValue;
    private Boolean fixed;
    private List<String> valueOptions = Lists.newArrayList();

}
