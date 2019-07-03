package rf.salesplatform.fs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import rf.foundation.exception.GenericException;
import rf.foundation.pub.FunctionSlice;
import rf.policyadmin.ds.PolicyService;
import rf.policyadmin.model.*;
import rf.policyadmin.model.enums.ContractStatus;

import java.util.List;
import java.util.Map;

/**
 * Created by zhouzheng on 2017/6/9.
 */
@Component
public class DuplicatePolicyCheck implements FunctionSlice<Policy> {

    @Autowired
    private PolicyService policyService;

    @Override
    public void execute(Policy policy, Map<String, Object> context) {

        List<Customer> insureds = policy.getInsureds();
        PersonCustomer insured = (PersonCustomer) insureds.get(0);

        QueryCondition condition = new QueryCondition();
        condition.setProductCode(policy.getProductCode());
        condition.setPolicyInsuredIdNumber(insured.getIdNumber());
        condition.setStatus(ContractStatus.EFFECTIVE.name());

       Long count = policyService.countPolicy(condition);
        if(count > 0)
            throw new GenericException(30007L);
    }

}
