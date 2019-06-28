package rf.finance.model;

import rf.foundation.model.ModelComponent;
import rf.foundation.pub.Guid;

public class Offset extends ModelComponent {

    public Offset(){
        this.setUuid(Guid.generateStrId());
    }
}
