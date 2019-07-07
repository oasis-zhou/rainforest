package rf.customer.ds.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import rf.customer.ds.CustomerNumberService;
import rf.customer.model.Customer;
import rf.foundation.numbering.NumberingFactor;
import rf.foundation.numbering.NumberingService;
import rf.foundation.numbering.NumberingType;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class CustomerNumberServiceImpl implements CustomerNumberService {


    @Value("${guid.org.code}")
    private String orgCode;

    @Autowired
    private NumberingService numberingService;

    @Override
    public String generateCustomerCode(Customer customer) {
        Map<NumberingFactor, String> factors = new HashMap<NumberingFactor, String>();
        factors.put(NumberingFactor.ORG_CODE, orgCode);
        Date date = new Date();
        factors.put(NumberingFactor.TRANS_YEAR, new SimpleDateFormat("yyyy").format(date));
        factors.put(NumberingFactor.TRANS_MONTH, new SimpleDateFormat("MM").format(date));
        factors.put(NumberingFactor.TRANS_DAY, new SimpleDateFormat("dd").format(date));

        //4{TRANS_YEAR}2{TRANS_MONTH}2{TRANS_DAY}7{SEQUENCE}
        String code = numberingService.generateNumber(NumberingType.CUSTOMER_CODE,factors);
        return code;
    }
}
