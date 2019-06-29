package rf.product.model;

import com.google.common.collect.Lists;
import lombok.Data;
import rf.foundation.model.ModelComponent;
import java.util.List;


@Data
public class DeductibleSpec extends ModelComponent {
    private String pattern;
    private String defaultValue;
    private Boolean fixed;
    private List<String> valueOptions = Lists.newArrayList();

}
