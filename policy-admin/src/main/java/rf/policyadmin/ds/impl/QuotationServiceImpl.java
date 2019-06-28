package rf.policyadmin.ds.impl;


import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rf.foundation.numbering.NumberingFactor;
import rf.foundation.numbering.NumberingService;
import rf.foundation.numbering.NumberingType;
import rf.foundation.utils.JsonHelper;
import rf.policyadmin.ds.QuotationService;
import rf.policyadmin.model.Quotation;
import rf.policyadmin.model.enums.QuotationStatus;
import rf.policyadmin.repository.QuotationDao;
import rf.policyadmin.repository.pojo.TQuotation;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhengzhou on 16/8/8.
 */
@Service
public class QuotationServiceImpl implements QuotationService {

    @Autowired
    private QuotationDao dao;

    @Autowired
    private JsonHelper jsonHelper;
    @Autowired
    private NumberingService numberingService;

    @Override
    public String generateQuotation(Quotation quotation){

//        TQuotation quotation = dao.findOne(policy.getUuid());
//
//        if(quotation == null)
//            quotation = new TQuotation();

        TQuotation po = new TQuotation();
        BeanUtils.copyProperties(quotation,po);

        po.setQuotationStatusCode(QuotationStatus.QUOTATION_INPROCESS.name());
        po.setQuotationDate(new Date());
        if(quotation.getQuotationNumber() == null){
            Map<NumberingFactor, String> factors = new HashMap<NumberingFactor, String>();
            Date date = new Date();
            factors.put(NumberingFactor.TRANS_YEAR, new SimpleDateFormat("yyyy").format(date));

            //Q{863100}4{TRANS_YEAR}{023}7{SEQUENCE}
            String quotationNumber = numberingService.generateNumber(NumberingType.QUOTATION_NUMBER,factors);
            po.setQuotationNumber(quotationNumber);
        }

        String content = jsonHelper.toJSON(quotation);
        po.setContent(content);

        dao.save(po);

        return po.getQuotationNumber();
    }
}
