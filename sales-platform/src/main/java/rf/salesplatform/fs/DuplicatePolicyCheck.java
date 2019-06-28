package rf.salesplatform.fs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import rf.foundation.exception.GenericException;
import rf.foundation.pub.FunctionSlice;
import rf.policyadmin.ds.PolicyService;
import rf.policyadmin.model.*;
import rf.policyadmin.model.enums.ContractStatus;
import rf.salesplatform.pub.PAFConsts;

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

        PolicyQueryCondition condition = new PolicyQueryCondition();
        condition.setProductCode(policy.getProductCode());
        condition.setPolicyInsuredIdNumber(insured.getIdNumber());
        condition.setContractStatus(ContractStatus.EFFECTIVE.name());

        List<PolicyIndex> indexs = policyService.findPolicy(condition);
        if(indexs.size() > 0)
            throw new GenericException(30007L);

        /*针对税优健康，出了上述EFFECTIVE状态外，还要排除QUOTATION，WAITING_FOR_PAYMENT，UNDERWRITING
        这三种状态的重复投保可能
        */
        if(policy.getProductCode().equals(PAFConsts.TAX_FREE_PRODUCE_CODE)){
            condition.setContractStatus(ContractStatus.UNDERWRITING.name());
            duplicationExclude(condition);
            condition.setContractStatus(ContractStatus.WAITING_FOR_PAYMENT.name());
            duplicationExclude(condition);
            condition.setContractStatus(ContractStatus.QUOTATION.name());
            duplicationExclude(condition);
        }

    }

    private void duplicationExclude(PolicyQueryCondition condition){
        List<PolicyIndex> policyIndexList = policyService.findPolicy(condition);
        if(policyIndexList.size() > 0){
            throw new GenericException(30007L);
        }
    }
}
