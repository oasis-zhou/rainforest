package rf.eval.dt;

import com.google.common.collect.Lists;
import rf.foundation.model.ModelComponent;
import java.util.List;


public class TableItem extends ModelComponent {

    private List<TableColumn> columns = Lists.newArrayList();


    public List<TableColumn> getColumns() {
        return columns;
    }

    public void setColumns(List<TableColumn> columns) {
        this.columns = columns;
    }
}
