package rf.policyadmin.ds;

import rf.policyadmin.model.Policy;
import rf.policyadmin.model.PolicyIndex;
import rf.policyadmin.model.PolicyQueryCondition;

import java.util.List;

/**
 * Created by zhengzhou on 16/8/8.
 * @Date 2017/11/23 LiRui update，新增处理未支付订单状态的方法
 */
public interface PolicyService {

    public String generateProposal(Policy policy);
    public String issuePolicy(Policy policy);
    public void rejectPolicy(String proposalNumber);
    public Policy loadPolicyByPolicyNumber(String policyNumber);
    public Policy loadPolicyByPolicyNumberOnLock(String policyNumber);
    public Policy loadPolicyByProposalNumber(String proposalNumber);
    public Policy loadPolicyByProposalNumberOnLock(String proposalNumber);
    public void savePolicy(Policy policy);
    public void generatePolicyIndex(Policy policy);
    public List<PolicyIndex> findPolicy(PolicyQueryCondition condition);

}
