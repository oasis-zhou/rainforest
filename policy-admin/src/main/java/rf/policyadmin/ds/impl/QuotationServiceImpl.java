package rf.policyadmin.ds.impl;


import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rf.foundation.utils.JsonHelper;
import rf.policyadmin.ds.BusinessNumberService;
import rf.policyadmin.ds.QuotationService;
import rf.policyadmin.model.Quotation;
import rf.policyadmin.model.enums.QuotationStatus;
import rf.policyadmin.repository.QuotationDao;
import rf.policyadmin.repository.pojo.TQuotation;

import java.util.Date;

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
    private BusinessNumberService businessNumberService;

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
            String quotationNumber = businessNumberService.generateQuotationNumber(quotation);
            po.setQuotationNumber(quotationNumber);
        }

        String content = jsonHelper.toJSON(quotation);
        po.setContent(content);

        dao.save(po);

        return po.getQuotationNumber();
    }
}
