package rf.finance.ds.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rf.finance.ds.BillService;
import rf.finance.model.Bill;
import rf.finance.model.PayerPayee;
import rf.finance.repository.BillDao;
import rf.finance.repository.pojo.TBill;
import rf.foundation.utils.JsonHelper;


@Service
public class BillServiceImpl implements BillService {

    @Autowired
    private BillDao arapItemDao;
    @Autowired
    private JsonHelper jsonHelper;

    @Override
    public void generateBill(Bill bill) {
        TBill po = arapItemDao.findByRefNumber(bill.getTransType().name(),bill.getRefBizNumber());

        if(po == null){
            po = new TBill();
        }else{
            return;
        }

        BeanUtils.copyProperties(bill,po);
        po.setTransType(bill.getTransType().name());
        po.setStatusCode(bill.getStatus().name());
        po.setPayerPayee(bill.getPayerPayee().getName());
        po.setPayerPayeeIdNumber(bill.getPayerPayee().getIdNumber());

        String content = jsonHelper.toJSON(bill);
        po.setContent(content);

        arapItemDao.save(po);
    }
}
