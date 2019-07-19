package rf.rating.dt;

import com.google.common.collect.Lists;
import lombok.Data;
import rf.foundation.model.ModelComponent;
import java.util.List;

@Data
public class TableItem extends ModelComponent {

    private List<TableColumn> columns = Lists.newArrayList();

}
