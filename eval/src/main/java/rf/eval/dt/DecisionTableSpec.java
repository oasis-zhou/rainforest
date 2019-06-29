package rf.eval.dt;

import com.google.common.collect.Maps;
import lombok.Data;
import rf.foundation.model.ModelComponent;
import rf.foundation.pub.Guid;
import java.util.Date;
import java.util.Map;

@Data
public class DecisionTableSpec extends ModelComponent {
    private String name;
    private String code;
    private String description;
    private Date effectiveDate;
    private Date expiredDate;
    private DTStatus status;
    private Map<String,ColumnSpec> columnSpecs = Maps.newHashMap();

    public DecisionTableSpec(){
        this.setUuid(Guid.generateStrId());
    }

}
