package rf.customer.model;

import lombok.Data;
import rf.foundation.model.ModelComponent;

@Data
public class PotentialCustomer extends ModelComponent {
    private String name;
    private String contractInfo;

}
