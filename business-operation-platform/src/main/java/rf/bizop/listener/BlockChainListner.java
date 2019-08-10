package rf.bizop.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import rf.bizop.ds.BlockChainService;
import rf.policyadmin.ds.EndorsementService;
import rf.policyadmin.ds.PolicyService;
import rf.policyadmin.model.Endorsement;
import rf.policyadmin.model.Policy;

import java.util.List;

/**
 * @ClassName MessageListner
 * @Description: TODO
 * @Author zhouzheng
 * @Date 2019/7/10
 * @Version V1.0
 **/
@Component
public class BlockChainListner {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private BlockChainService blockChainService;
    @Autowired
    private PolicyService policyService;
    @Autowired
    private EndorsementService endorsementService;

    @Scheduled(cron="0 */1 * * * ?")
    public void listenPolicy() {
        logger.info("Start to get policy from chain!");

        List<Policy> policies = blockChainService.findPendingPolicies();

        for (Policy policy : policies) {
            policyService.savePolicy(policy);
            policyService.generatePolicyIndex(policy);
            blockChainService.withdrawPendingPolicy(policy.getPolicyNumber());
        }
    }

    @Scheduled(cron="0 */1 * * * ?")
    public void listenEndorsement() {
        logger.info("Start to get endorsement from chain!");

        List<Endorsement> endorsements = blockChainService.findPendingEndorsements();

        for (Endorsement endorsement : endorsements) {
            endorsementService.save(endorsement);
            blockChainService.withdrawPendingEndorsement(endorsement.getEndorsementNumber());
        }
    }
}
