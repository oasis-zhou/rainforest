package rf.salesplatform.fs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import rf.foundation.pub.FunctionSlice;
import rf.policyadmin.ds.PolicyService;
import rf.policyadmin.model.Policy;

import java.util.Map;

/**
 * Created by zhouzheng on 2017/5/3.
 */
@Component
public class CreatePolicyIndex implements FunctionSlice<Policy> {
    @Autowired
    private PolicyService policyService;

    @Override
    public void execute(Policy policy, Map<String, Object> context) {

        policyService.generatePolicyIndex(policy);
    }
}
