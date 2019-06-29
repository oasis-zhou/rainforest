package rf.salesplatform.fs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import rf.finance.ds.ArapItemService;
import rf.finance.model.ArapItem;
import rf.finance.model.PayerPayee;
import rf.finance.model.enums.TransactionType;
import rf.foundation.pub.FunctionSlice;
import rf.policyadmin.pub.Constants;
import rf.policyadmin.model.Customer;
import rf.policyadmin.model.Fee;
import rf.policyadmin.model.Policy;

import java.util.Date;
import java.util.Map;


@Component
public class NewbizKeepAccounts implements FunctionSlice<Policy> {

    @Autowired
    private ArapItemService arapItemService;

    @Override
    public void execute(Policy policy, Map<String, Object> context){

        ArapItem record = new ArapItem();

        record.setTransType(TransactionType.NEWBIZ);
        record.setTransDate(new Date());
        Fee fee = policy.getPolicyFeeByCode(Constants.FEE_APP);
        record.setAmount(fee.getValue());
        record.setBalance(fee.getValue());
        record.setDueDate(new Date());
        record.setFeeCode(fee.getCode());
        record.setRefBizNumber(policy.getPolicyNumber());

        Customer customer = policy.getPolicyHolder();
        PayerPayee pp = new PayerPayee();
        pp.setName(customer.getName());
        pp.setIdType(customer.getIdType());
        pp.setIdNumber(customer.getIdNumber());
        record.setPayerPayee(pp);

        arapItemService.keepAccounts(record);
    }
}
