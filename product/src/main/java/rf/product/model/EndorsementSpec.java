package rf.product.model;

import rf.foundation.model.ModelComponent;
import rf.product.model.enums.EndorsementType;

public class EndorsementSpec extends ModelComponent {

    private String name;
    private String code;
    private EndorsementType endorsementType;
    private String description;

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

    public EndorsementType getEndorsementType() {
        return endorsementType;
    }

    public void setEndorsementType(EndorsementType endorsementType) {
        this.endorsementType = endorsementType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
