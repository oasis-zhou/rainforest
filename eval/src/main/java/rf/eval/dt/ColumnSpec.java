package rf.eval.dt;

import rf.foundation.model.ModelComponent;
import rf.foundation.pub.DataType;

public class ColumnSpec extends ModelComponent {
    private String name;
    private ScopePattern scopePattern;
    private Boolean isCondition;
    private DataType dataType;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ScopePattern getScopePattern() {
        return scopePattern;
    }

    public void setScopePattern(ScopePattern scopePattern) {
        this.scopePattern = scopePattern;
    }

    public Boolean getCondition() {
        return isCondition;
    }

    public void setCondition(Boolean condition) {
        isCondition = condition;
    }

    public DataType getDataType() {
        return dataType;
    }

    public void setDataType(DataType dataType) {
        this.dataType = dataType;
    }
}
