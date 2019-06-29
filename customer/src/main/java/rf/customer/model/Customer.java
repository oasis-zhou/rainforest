package rf.customer.model;

import lombok.Data;
import rf.foundation.model.ModelComponent;
import rf.foundation.pub.Guid;

@Data
public class Customer extends ModelComponent {

    private String name;
    private String idType;
    private String idNumber;
    private String code;

    public Customer(){
        this.setUuid(Guid.generateStrId());
    }

}
