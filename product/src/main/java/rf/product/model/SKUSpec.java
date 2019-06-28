package rf.product.model;

import rf.foundation.model.ModelComponent;
import rf.foundation.pub.Guid;
import rf.product.model.enums.SKUCategory;
import rf.product.model.enums.SKUStatus;
import java.util.Date;


public class SKUSpec extends ModelComponent {

    private String name;
    private String code;
    private String description;
    private SKUCategory category;
    private SKUStatus status;
    private Date effectiveDate;
    private Date expiredDate;

    public SKUSpec(){
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

    public SKUCategory getCategory() {
        return category;
    }

    public void setCategory(SKUCategory category) {
        this.category = category;
    }

    public SKUStatus getStatus() {
        return status;
    }

    public void setStatus(SKUStatus status) {
        this.status = status;
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
}
