package rf.eval.dt;

import lombok.Data;
import rf.foundation.model.ModelComponent;

@Data
public class TableColumn extends ModelComponent {
    private String name;
    private Object value;
    private Object maxValue;
    private Object minValue;

}
