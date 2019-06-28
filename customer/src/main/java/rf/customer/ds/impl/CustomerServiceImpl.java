package rf.customer.ds.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rf.customer.ds.CustomerService;
import rf.customer.model.Customer;
import rf.customer.repository.CustomerDao;
import rf.customer.repository.pojo.TCustormer;
import rf.foundation.numbering.NumberingFactor;
import rf.foundation.numbering.NumberingService;
import rf.foundation.numbering.NumberingType;
import rf.foundation.utils.JsonHelper;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private JsonHelper jsonHelper;

    @Autowired
    private CustomerDao customerDao;

    @Autowired
    private NumberingService numberingService;

    @Override
    public String generateCustomer(Customer customer) {

        TCustormer po = customerDao.findByIdNumber(customer.getIdType(),customer.getIdNumber());
        if(po == null) {
            Map<NumberingFactor, String> factors = new HashMap<NumberingFactor, String>();
            Date date = new Date();
            factors.put(NumberingFactor.TRANS_YEAR, new SimpleDateFormat("yyyy").format(date));
            factors.put(NumberingFactor.TRANS_MONTH, new SimpleDateFormat("MM").format(date));
            factors.put(NumberingFactor.TRANS_DAY, new SimpleDateFormat("dd").format(date));

            //4{TRANS_YEAR}2{TRANS_MONTH}2{TRANS_DAY}7{SEQUENCE}
            String code = numberingService.generateNumber(NumberingType.CUSTOMER_CODE,factors);

            po = new TCustormer();
            po.setCode(code);
        }

        if(po.getUuid() == null)
            po.setUuid(customer.getUuid());
        po.setName(customer.getName());
        po.setIdType(customer.getIdType());
        po.setIdNumber(customer.getIdNumber());

        customer.setCode(po.getCode());

        String content = jsonHelper.toJSON(customer);
        po.setContent(content);

        customerDao.save(po);

        return customer.getCode();
    }

    @Override
    public Customer findCustomerById(String idType, String idNumber) {

        TCustormer po = customerDao.findByIdNumber(idType,idNumber);
        String content = po.getContent();

        Customer customer = jsonHelper.fromJSON(content,Customer.class);

        return customer;
    }
}
