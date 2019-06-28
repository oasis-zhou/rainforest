package rf.product.model;

import rf.foundation.model.ModelComponent;
import rf.product.model.enums.CoverageStatus;

public class CoverageSpec extends ModelComponent {
    private String code;
    private String name;
    private String description;
    private CoverageStatus status;
    private Boolean primary;
    private String riskPackage;

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

    public CoverageStatus getStatus() {
        return status;
    }

    public void setStatus(CoverageStatus status) {
        this.status = status;
    }

    public Boolean getPrimary() {
        return primary;
    }

    public void setPrimary(Boolean primary) {
        this.primary = primary;
    }

    public String getRiskPackage() {
        return riskPackage;
    }

    public void setRiskPackage(String riskPackage) {
        this.riskPackage = riskPackage;
    }
}
