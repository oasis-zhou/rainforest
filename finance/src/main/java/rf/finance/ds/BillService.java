package rf.finance.ds;

import rf.finance.model.Bill;
import rf.finance.model.QueryCondition;
import rf.foundation.model.ResponsePage;

public interface BillService {

    void generateBill(Bill bill);

    ResponsePage<Bill> findBills(QueryCondition condition);
}
