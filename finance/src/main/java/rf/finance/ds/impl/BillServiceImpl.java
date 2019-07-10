package rf.finance.ds.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import rf.finance.ds.BillService;
import rf.finance.ds.FinanceNumberService;
import rf.finance.model.Bill;
import rf.finance.model.QueryCondition;
import rf.finance.repository.BillDao;
import rf.finance.repository.pojo.TBill;
import rf.foundation.exception.GenericException;
import rf.foundation.model.ResponsePage;
import rf.foundation.utils.JsonHelper;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;


@Service
public class BillServiceImpl implements BillService {

    @Autowired
    private BillDao billDao;
    @Autowired
    private FinanceNumberService financeNumberService;
    @Autowired
    private JsonHelper jsonHelper;

    @Override
    public String generateBill(Bill bill) {
        TBill po = billDao.findByRefNumber(bill.getTransType().name(),bill.getRefBizNumber());

        if(po == null){
            po = new TBill();
        }else{
            throw new GenericException(80000L);
        }

        String billNumber = financeNumberService.generateBillNumber(bill);
        bill.setBillNumber(billNumber);
        BeanUtils.copyProperties(bill,po);
        po.setTransType(bill.getTransType().name());
        po.setStatusCode(bill.getStatus().name());
        po.setPayerPayee(bill.getPayerPayee().getName());
        po.setPayerPayeeIdNumber(bill.getPayerPayee().getIdNumber());

        String content = jsonHelper.toJSON(bill);
        po.setContent(content);

        billDao.save(po);

        return billNumber;
    }

    @Override
    public ResponsePage<Bill> findBills(QueryCondition condition) {

        // 构造自定义查询条件
        Specification<TBill> queryCondition = new Specification<TBill>() {
            @Override
            public Predicate toPredicate(Root<TBill> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicateList = new ArrayList<>();

                if (condition.getTransType()!=null&&!"".equals(condition.getTransType())){
                    predicateList.add(criteriaBuilder.equal(root.get("transType"),condition.getTransType()));
                }
                if (condition.getBillNumber()!=null&&!"".equals(condition.getBillNumber())){
                    predicateList.add(criteriaBuilder.equal(root.get("billNumber"),condition.getBillNumber()));
                }
                if (condition.getRefBizNumber()!=null&&!"".equals(condition.getRefBizNumber())){
                    predicateList.add(criteriaBuilder.equal(root.get("refBizNumber"),condition.getRefBizNumber()));
                }
                if (condition.getRefExtNumber()!=null&&!"".equals(condition.getRefExtNumber())){
                    predicateList.add(criteriaBuilder.equal(root.get("refExtNumber"),condition.getRefExtNumber()));
                }
                if (condition.getPayerPayee()!=null&&!"".equals(condition.getPayerPayee())){
                    predicateList.add(criteriaBuilder.equal(root.get("payerPayee"),"%" + condition.getPayerPayee() + "%"));
                }
                if (condition.getPayerPayeeIdNumber()!=null&&!"".equals(condition.getPayerPayeeIdNumber())){
                    predicateList.add(criteriaBuilder.equal(root.get("payerPayeeIdNumber"),condition.getPayerPayeeIdNumber()));
                }
                if (condition.getStatusCode()!=null&&!"".equals(condition.getStatusCode())){
                    predicateList.add(criteriaBuilder.equal(root.get("statusCode"),condition.getStatusCode()));
                }
                if (condition.getDateStart()!=null&&condition.getDateEnd()!=null){
                    predicateList.add(criteriaBuilder.between(root.get("transDate"),condition.getDateStart(),condition.getDateEnd()));
                }
                return criteriaBuilder.and(predicateList.toArray(new Predicate[predicateList.size()]));
            }
        };

        Page<TBill> page = billDao.findAll(queryCondition, PageRequest.of(condition.getPageNo() - 1, condition.getPageSize(), Sort.by(Sort.Direction.DESC, "transDate")));

        return buildResponsePage(page);
    }

    private ResponsePage<Bill> buildResponsePage(Page<TBill> page){
        List<Bill> datas = page.getContent().stream()
                .map(po -> {
                    Bill bill = jsonHelper.fromJSON(po.getContent(), Bill.class);
                    return bill;
                })
                .collect(toList());
        return new ResponsePage<>(page, datas);
    }
}
