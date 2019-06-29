package rf.policyadmin.model;

import lombok.Data;
import rf.foundation.model.ModelComponent;

@Data
public class Customer extends ModelComponent {
    private String name;
    private String idType;
    private String idNumber;
    private String code;

}
