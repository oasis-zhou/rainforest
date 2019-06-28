package rf.product.model;


import rf.foundation.model.ModelComponent;
import rf.foundation.pub.Guid;
import rf.product.model.enums.ProductStatus;

import java.util.Date;


public class ProductSpec extends ModelComponent {
    private String code;
    private String name;
    private String description;
    private String version;
    private ProductStatus status;
    private String organization;
    private Date effectiveDate;
    private Date expiredDate;
    private Boolean isfixedCoverage;

    public ProductSpec(){
        this.setUuid(Guid.generateStrId());
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public ProductStatus getStatus() {
        return status;
    }

    public void setStatus(ProductStatus status) {
        this.status = status;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
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

    public Boolean getIsfixedCoverage() {
        return isfixedCoverage;
    }

    public void setIsfixedCoverage(Boolean isfixedCoverage) {
        this.isfixedCoverage = isfixedCoverage;
    }
}
