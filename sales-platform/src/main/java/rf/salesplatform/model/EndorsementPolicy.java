package rf.salesplatform.model;

import lombok.Data;
import rf.policyadmin.model.Policy;

/**
 * @ClassName EndorsementPolicy
 * @Description: TODO
 * @Author zhouzheng
 * @Date 2019/7/2
 * @Version V1.0
 **/
@Data
public class EndorsementPolicy {

    private Policy original;
    private Policy newOne;

    public EndorsementPolicy(Policy original, Policy newOne) {
        this.original = original;
        this.newOne = newOne;
    }
}
