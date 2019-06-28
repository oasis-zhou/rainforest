package rf.product.model;

import rf.foundation.model.ModelComponent;
import rf.product.model.enums.InsuredObjectCate;

public class InsuredObjectSpec extends ModelComponent {
    private InsuredObjectCate cate;
    private boolean isMultiple;

    public InsuredObjectCate getCate() {
        return cate;
    }

    public void setCate(InsuredObjectCate cate) {
        this.cate = cate;
    }

    public boolean isMultiple() {
        return isMultiple;
    }

    public void setIsMultiple(boolean isMultiple) {
        this.isMultiple = isMultiple;
    }
}
