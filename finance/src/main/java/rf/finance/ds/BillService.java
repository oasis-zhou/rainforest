package rf.finance.ds;

import rf.finance.model.Bill;
import rf.finance.model.QueryCondition;
import rf.foundation.model.ResponsePage;

public interface BillService {

    String generateBill(Bill bill);

    ResponsePage<Bill> findBills(QueryCondition condition);
}
