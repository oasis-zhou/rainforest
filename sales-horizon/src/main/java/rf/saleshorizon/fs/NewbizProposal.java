package rf.saleshorizon.fs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import rf.foundation.pub.FunctionSlice;
import rf.policyadmin.ds.BusinessNumberService;
import rf.policyadmin.model.Policy;
import rf.policyadmin.model.enums.ContractStatus;
import rf.saleshorizon.ds.PolicyService;

import java.util.Date;
import java.util.Map;


@Component
public class NewbizProposal implements FunctionSlice<Policy> {

    @Autowired
    private PolicyService policyService;
    @Autowired
    private BusinessNumberService businessNumberService;

    @Override
    public void execute(Policy policy, Map<String, Object> context){

        policy.setContractStatus(ContractStatus.WAITING_FOR_PAYMENT);
        policy.setProposalDate(new Date());
        policy.setProposalNumber(businessNumberService.generateProposalNumber(policy));
        policyService.pushToChain(policy);
    }
}
