package rf.eval.dt;

import com.google.common.collect.Maps;
import rf.foundation.model.ModelComponent;
import rf.foundation.pub.Guid;
import java.util.Date;
import java.util.Map;


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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(Date effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public Date getExpiredDate() {
        return expiredDate;
    }

    public void setExpiredDate(Date expiredDate) {
        this.expiredDate = expiredDate;
    }

    public DTStatus getStatus() {
        return status;
    }

    public void setStatus(DTStatus status) {
        this.status = status;
    }

    public Map<String, ColumnSpec> getColumnSpecs() {
        return columnSpecs;
    }

    public void setColumnSpecs(Map<String, ColumnSpec> columnSpecs) {
        this.columnSpecs = columnSpecs;
    }

}
