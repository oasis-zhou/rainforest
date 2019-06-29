package rf.finance.model;

import lombok.Data;
import rf.foundation.model.ModelComponent;
import rf.foundation.pub.Guid;

@Data
public class Offset extends ModelComponent {

    public Offset(){
        this.setUuid(Guid.generateStrId());
    }
}
