package rf.finance.ds.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import rf.finance.ds.FinanceNumberService;
import rf.finance.model.Bill;
import rf.foundation.numbering.NumberingFactor;
import rf.foundation.numbering.NumberingService;
import rf.foundation.numbering.NumberingType;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName FinanceNumberServiceImpl
 * @Description: TODO
 * @Author zhouzheng
 * @Date 2019/7/8
 * @Version V1.0
 **/
@Service
public class FinanceNumberServiceImpl implements FinanceNumberService {

    @Value("${guid.org.code}")
    private String orgCode;

    @Autowired
    private NumberingService numberingService;

    @Override
    public String generateBillNumber(Bill bill) {
        Map<NumberingFactor, String> factors = new HashMap<NumberingFactor, String>();
        factors.put(NumberingFactor.ORG_CODE, orgCode);
        Date date = new Date();
        factors.put(NumberingFactor.TRANS_YEAR, new SimpleDateFormat("yyyy").format(date));
        factors.put(NumberingFactor.TRANS_MONTH, new SimpleDateFormat("MM").format(date));
        factors.put(NumberingFactor.TRANS_DAY, new SimpleDateFormat("dd").format(date));

        //4{TRANS_YEAR}2{TRANS_MONTH}2{TRANS_DAY}7{SEQUENCE}
        String code = numberingService.generateNumber(NumberingType.BILL_TRANS_NUMBER,factors);
        return code;
    }
}
