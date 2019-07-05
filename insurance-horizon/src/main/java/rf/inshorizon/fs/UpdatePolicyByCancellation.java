package rf.inshorizon.fs;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import rf.foundation.pub.FunctionSlice;
import rf.policyadmin.model.EndorsementPolicy;
import rf.inshorizon.pub.Constants;
import rf.policyadmin.ds.PolicyService;
import rf.policyadmin.model.Cancellation;
import rf.policyadmin.model.Endorsement;
import rf.policyadmin.model.Policy;
import rf.policyadmin.model.enums.CancellationType;
import rf.policyadmin.model.enums.ContractStatus;
import rf.policyadmin.model.enums.TerminalReason;


import java.util.Date;
import java.util.Map;

/**
 * Created by zhouzheng on 2017/5/5.
 */
@Component
public class UpdatePolicyByCancellation implements FunctionSlice<Endorsement> {

    @Autowired
    private PolicyService policyService;

    @Override
    public void execute(Endorsement endorsement, Map<String, Object> context) {
        EndorsementPolicy endoPolicy = (EndorsementPolicy)context.get(Constants.ENDORSEMENT_POLICY_OBJECT);
        Policy policy = endoPolicy.getNewOne();

        Cancellation cancellation = (Cancellation)endorsement;

        if(cancellation.getCancellationType().equals(CancellationType.CANCELLATION_FROM_INCEPTION)){
            policy.setContractStatus(ContractStatus.TERMINAL);
            policy.setTerminalReason(TerminalReason.TERMINAL_BY_CANCELLATION);
        }else {
//            DateTime expDate = new DateTime(cancellation.getEffectiveDate());
//            expDate = expDate.plusSeconds(-1);
//            policy.setExpiredDate(expDate.toDate());
            policy.setContractStatus(ContractStatus.TERMINAL);
            policy.setTerminalReason(TerminalReason.TERMINAL_BY_CANCELLATION);
        }
        policy.setTerminalDate(new Date());
    }
}
