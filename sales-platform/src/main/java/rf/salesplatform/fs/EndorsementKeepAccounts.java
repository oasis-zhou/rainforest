package rf.salesplatform.fs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import rf.finance.ds.ArapItemService;
import rf.finance.model.ArapItem;
import rf.finance.model.PayerPayee;
import rf.finance.model.enums.TransactionType;
import rf.foundation.pub.FunctionSlice;
import rf.policyadmin.constants.PolicyConstants;
import rf.policyadmin.ds.PolicyService;
import rf.policyadmin.model.Customer;
import rf.policyadmin.model.Endorsement;
import rf.policyadmin.model.Fee;
import rf.policyadmin.model.Policy;

import java.util.Date;
import java.util.Map;

@Component
public class EndorsementKeepAccounts implements FunctionSlice<Endorsement> {

    @Autowired
    private ArapItemService arapItemService;

    @Autowired
    private PolicyService policyService;

    @Override
    public void execute(Endorsement endorsement, Map<String, Object> context) {

        ArapItem record = new ArapItem();

        record.setTransType(TransactionType.ENDORSEMENT);
        record.setTransDate(new Date());
        Fee fee = endorsement.getEndoFeeByCode(PolicyConstants.FEE_APP);
        record.setAmount(fee.getValue());
        record.setBalance(fee.getValue());
        record.setDueDate(new Date());
        record.setFeeCode(fee.getCode());
        record.setRefBizNumber(endorsement.getEndorsementNumber());

        Policy policy = policyService.loadPolicyByPolicyNumber(endorsement.getPolicyNumber());

        Customer customer = policy.getPolicyHolder();
        PayerPayee pp = new PayerPayee();
        pp.setName(customer.getName());
        pp.setIdType(customer.getIdType());
        pp.setIdNumber(customer.getIdNumber());
        record.setPayerPayee(pp);

        arapItemService.keepAccounts(record);
    }
}
