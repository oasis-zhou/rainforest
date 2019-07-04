package rf.claim.ds.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rf.claim.ds.BusinessNumberService;
import rf.claim.model.NoticeOfLoss;
import rf.foundation.numbering.NumberingFactor;
import rf.foundation.numbering.NumberingService;
import rf.foundation.numbering.NumberingType;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName BusinessNumberServiceImpl
 * @Description: TODO
 * @Author zhouzheng
 * @Date 2019/7/4
 * @Version V1.0
 **/
@Service
public class BusinessNumberServiceImpl implements BusinessNumberService {

    @Autowired
    private NumberingService numberingService;

    @Override
    public String generateNoticeNumber(NoticeOfLoss noticeOfLoss) {
        Map<NumberingFactor, String> factors = new HashMap<NumberingFactor, String>();
        Date date = new Date();
        factors.put(NumberingFactor.TRANS_YEAR, new SimpleDateFormat("yyyy").format(date));
        factors.put(NumberingFactor.TRANS_MONTH, new SimpleDateFormat("MM").format(date));
        factors.put(NumberingFactor.TRANS_DAY, new SimpleDateFormat("dd").format(date));
        String noticeNumber = numberingService.generateNumber(NumberingType.NOTICE_NUMBER, factors);
        return noticeNumber;
    }
}
