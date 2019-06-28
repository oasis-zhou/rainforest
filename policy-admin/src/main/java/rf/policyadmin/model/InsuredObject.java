package rf.policyadmin.model;

import rf.foundation.model.ModelComponent;

public class InsuredObject extends ModelComponent {

    private String insuredObjectName;
    private String insuredObjectCategory;

    public String getInsuredObjectName() {
        return insuredObjectName;
    }

    public void setInsuredObjectName(String insuredObjectName) {
        this.insuredObjectName = insuredObjectName;
    }

    public String getInsuredObjectCategory() {
        return insuredObjectCategory;
    }

    public void setInsuredObjectCategory(String insuredObjectCategory) {
        this.insuredObjectCategory = insuredObjectCategory;
    }
}
