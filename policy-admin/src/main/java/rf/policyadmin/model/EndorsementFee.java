package rf.policyadmin.model;

import rf.foundation.model.ModelComponent;
import rf.policyadmin.model.enums.EndorsementFeeLevel;

/**
 * Created by zhouzheng on 2017/5/5.
 */
public class EndorsementFee extends ModelComponent {

    private EndorsementFeeLevel feeLevel;
    private String refBizobjectId;
    private String name;
    private String code;

    public EndorsementFeeLevel getFeeLevel() {
        return feeLevel;
    }

    public void setFeeLevel(EndorsementFeeLevel feeLevel) {
        this.feeLevel = feeLevel;
    }

    public String getRefBizobjectId() {
        return refBizobjectId;
    }

    public void setRefBizobjectId(String refBizobjectId) {
        this.refBizobjectId = refBizobjectId;
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
}
