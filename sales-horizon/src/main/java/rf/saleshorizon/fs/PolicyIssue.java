package rf.saleshorizon.fs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import rf.foundation.pub.FunctionSlice;
import rf.policyadmin.ds.BusinessNumberService;
import rf.policyadmin.model.Policy;
import rf.policyadmin.model.enums.ContractStatus;
import rf.saleshorizon.ds.BlockChainService;

import java.util.Date;
import java.util.Map;


@Component
public class PolicyIssue implements FunctionSlice<Policy> {

    @Autowired
    private BlockChainService blockChainService;
    @Autowired
    private BusinessNumberService businessNumberService;

    @Override
    public void execute(Policy policy, Map<String, Object> context){
        policy.setContractStatus(ContractStatus.EFFECTIVE);
        if(policy.getIssueDate() == null)
            policy.setIssueDate(new Date());
        policy.setPolicyNumber(businessNumberService.generatePolicyNumber(policy));
        blockChainService.issuePolicy(policy);
    }
}
