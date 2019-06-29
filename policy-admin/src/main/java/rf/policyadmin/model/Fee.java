package rf.policyadmin.model;

import lombok.Data;
import rf.foundation.model.ModelComponent;
import java.math.BigDecimal;


@Data
public class Fee extends ModelComponent {
    private String bizCate;
    private String code;
    private String name;
    private BigDecimal value;
    private boolean asPremium;

}
