package rf.policyadmin.ds;

import rf.foundation.model.ResponsePage;
import rf.policyadmin.model.Policy;
import rf.policyadmin.model.PolicyIndex;
import rf.policyadmin.model.QueryCondition;
import rf.policyadmin.model.Quotation;

import java.util.List;

/**
 * Created by zhengzhou on 16/8/8.
 * @Date 2017/11/23 LiRui update，新增处理未支付订单状态的方法
 */
public interface PolicyService {

    String generateProposal(Policy policy);
    String issuePolicy(Policy policy);
    void rejectPolicy(String proposalNumber);
    Policy loadPolicyByPolicyNumber(String policyNumber);
    Policy loadPolicyByPolicyNumberOnLock(String policyNumber);
    Policy loadPolicyByProposalNumber(String proposalNumber);
    Policy loadPolicyByProposalNumberOnLock(String proposalNumber);
    void savePolicy(Policy policy);
    void generatePolicyIndex(Policy policy);
    ResponsePage<PolicyIndex> findPolicy(QueryCondition condition);
    Long countPolicy(QueryCondition condition);
}
