package rf.salesplatform.fs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import rf.finance.ds.BillService;
import rf.finance.model.Bill;
import rf.finance.model.PayerPayee;
import rf.finance.model.enums.BillStatus;
import rf.finance.model.enums.TransactionType;
import rf.foundation.pub.FunctionSlice;
import rf.policyadmin.pub.Constants;
import rf.policyadmin.ds.PolicyService;
import rf.policyadmin.model.Customer;
import rf.policyadmin.model.Endorsement;
import rf.policyadmin.model.Fee;
import rf.policyadmin.model.Policy;

import java.util.Date;
import java.util.Map;

@Component
public class EndorsementBill implements FunctionSlice<Endorsement> {

    @Autowired
    private BillService billService;

    @Autowired
    private PolicyService policyService;

    @Override
    public void execute(Endorsement endorsement, Map<String, Object> context) {
        Bill record = new Bill();

        record.setTransType(TransactionType.ENDORSEMENT);
        record.setTransDate(new Date());
        Fee fee = endorsement.getEndoFeeByCode(Constants.FEE_APP);
        record.setAmount(fee.getValue());
        record.setBalance(fee.getValue());
        record.setDueDate(new Date());
        record.setFeeCode(fee.getCode());
        record.setStatus(BillStatus.PAID);
        record.setRefBizNumber(endorsement.getEndorsementNumber());

        Policy policy = policyService.loadPolicyByPolicyNumber(endorsement.getPolicyNumber());

        Customer customer = policy.getPolicyHolder();
        PayerPayee pp = new PayerPayee();
        pp.setName(customer.getName());
        pp.setIdType(customer.getIdType());
        pp.setIdNumber(customer.getIdNumber());
        record.setPayerPayee(pp);

        billService.generateBill(record);
    }
}
