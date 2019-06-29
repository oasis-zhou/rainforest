package rf.product.model;

import com.google.common.collect.Maps;
import lombok.Data;
import rf.foundation.model.ModelComponent;
import rf.foundation.pub.DataType;
import rf.product.model.enums.FactorCategory;
import java.util.Map;


@Data
public class FactorSpec extends ModelComponent {
    private String code;
    private String name;
    private String description;
    private DataType dataType;
    private FactorCategory category;
    private String validationExpress;
    private Map<String,String> optionValues = Maps.newHashMap();

}
