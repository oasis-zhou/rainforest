package rf.product.model;

import lombok.Data;
import rf.foundation.model.ModelComponent;
import java.math.BigDecimal;

@Data
public class ServiceSpec extends ModelComponent {
    private String code;
    private String name;
    private String description;
    private BigDecimal value;

}
