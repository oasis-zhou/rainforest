package rf.finance.model;


import rf.foundation.model.ModelComponent;
import rf.foundation.pub.Guid;

public class Transfer extends ModelComponent {

    public Transfer(){
        this.setUuid(Guid.generateStrId());
    }
}
