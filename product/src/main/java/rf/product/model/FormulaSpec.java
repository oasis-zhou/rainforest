package rf.product.model;

import com.google.common.collect.Lists;
import lombok.Data;
import rf.foundation.model.ModelComponent;
import rf.product.model.enums.FormulaPurpose;
import java.util.List;


@Data
public class FormulaSpec extends ModelComponent {
    private String code;
    private String name;
    private String description;
    private FormulaPurpose purpose;
    private List<String> factors = Lists.newArrayList();
    private String body;

}
