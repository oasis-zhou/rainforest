package rf.salesplatform.fs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import rf.foundation.pub.FunctionSlice;
import rf.policyadmin.ds.PolicyService;
import rf.policyadmin.model.Policy;
import rf.policyadmin.model.enums.ContractStatus;

import java.util.Date;
import java.util.Map;


@Component
public class PolicyIssue implements FunctionSlice<Policy> {

    @Autowired
    private PolicyService policyService;

    @Override
    public void execute(Policy policy, Map<String, Object> context){
        policy.setContractStatus(ContractStatus.EFFECTIVE);
        if(policy.getIssueDate() == null)
            policy.setIssueDate(new Date());
        policyService.issuePolicy(policy);

    }
}
