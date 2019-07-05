package rf.salesplatform.fs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import rf.foundation.pub.FunctionSlice;
import rf.policyadmin.ds.EndorsementService;
import rf.policyadmin.ds.PolicyService;
import rf.policyadmin.model.Endorsement;
import rf.policyadmin.model.EndorsementPolicy;
import rf.policyadmin.model.Policy;
import rf.salesplatform.pub.Constants;

import java.util.Map;

@Component
public class EndorsementIssue implements FunctionSlice<Endorsement> {

    @Autowired
    private EndorsementService endorsementService;
    @Autowired
    private PolicyService policyService;

    @Override
    public void execute(Endorsement endorsement, Map<String, Object> context){
        EndorsementPolicy endoPolicy = (EndorsementPolicy)context.get(Constants.ENDORSEMENT_POLICY_OBJECT);
        Policy policy = endoPolicy.getNewOne();

        policyService.savePolicy(policy);
        policyService.generatePolicyIndex(policy);
        endorsementService.issue(endorsement);
    }
}
