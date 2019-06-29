package rf.finance.model;


import lombok.Data;
import rf.foundation.model.ModelComponent;
import rf.foundation.pub.Guid;

@Data
public class Transfer extends ModelComponent {

    public Transfer(){
        this.setUuid(Guid.generateStrId());
    }
}
