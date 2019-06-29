package rf.policyadmin.model;

import lombok.Data;
import rf.foundation.model.ModelComponent;
import rf.policyadmin.model.enums.EndorsementFeeLevel;

/**
 * Created by zhouzheng on 2017/5/5.
 */

@Data
public class EndorsementFee extends ModelComponent {

    private EndorsementFeeLevel feeLevel;
    private String refBizobjectId;
    private String name;
    private String code;

}
