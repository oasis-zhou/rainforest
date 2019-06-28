package rf.salesplatform.fs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import rf.foundation.pub.FunctionSlice;
import rf.policyadmin.ds.PolicyService;
import rf.policyadmin.model.Policy;
import rf.policyadmin.model.enums.ContractStatus;
import rf.salesplatform.pub.PAFConsts;

import java.util.Date;
import java.util.Map;


@Component
public class NewbizProposal implements FunctionSlice<Policy> {

    @Autowired
    private PolicyService policyService;

    @Override
    public void execute(Policy policy, Map<String, Object> context){


        if (PAFConsts.TAX_FREE_PRODUCE_CODE.equals(policy.getProductCode()) && PAFConsts.CHANNEL_TIANCHENG.equals(policy.getChannelCode())) {
            policy.setContractStatus(ContractStatus.UNDERWRITING);
        } else {
            policy.setContractStatus(ContractStatus.WAITING_FOR_PAYMENT);
        }

        policy.setProposalDate(new Date());
        policyService.generateProposal(policy);
    }
}
