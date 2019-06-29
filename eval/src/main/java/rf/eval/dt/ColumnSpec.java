package rf.eval.dt;

import lombok.Data;
import rf.foundation.model.ModelComponent;
import rf.foundation.pub.DataType;

@Data
public class ColumnSpec extends ModelComponent {
    private String name;
    private ScopePattern scopePattern;
    private Boolean condition;
    private DataType dataType;

}
