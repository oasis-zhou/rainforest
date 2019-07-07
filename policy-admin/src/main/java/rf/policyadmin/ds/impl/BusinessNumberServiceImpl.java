package rf.policyadmin.ds.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import rf.foundation.numbering.NumberingFactor;
import rf.foundation.numbering.NumberingService;
import rf.foundation.numbering.NumberingType;
import rf.policyadmin.ds.BusinessNumberService;
import rf.policyadmin.model.Endorsement;
import rf.policyadmin.model.Policy;
import rf.policyadmin.model.Quotation;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName BusinessNumberServiceImpl
 * @Description: TODO
 * @Author zhouzheng
 * @Date 2019/7/1
 * @Version V1.0
 **/
@Service
public class BusinessNumberServiceImpl implements BusinessNumberService {

    @Value("${guid.org.code}")
    private String orgCode;
    @Autowired
    private NumberingService numberingService;

    @Override
    public String generatePolicyNumber(Policy policy) {
        Map<NumberingFactor, String> factors = new HashMap<NumberingFactor, String>();
        factors.put(NumberingFactor.ORG_CODE, orgCode);
        Date date = new Date();
        factors.put(NumberingFactor.TRANS_YEAR, new SimpleDateFormat("yyyy").format(date));
        factors.put(NumberingFactor.TRANS_MONTH, new SimpleDateFormat("MM").format(date));
        factors.put(NumberingFactor.TRANS_DAY, new SimpleDateFormat("dd").format(date));

        String policyNumber = numberingService.generateNumber(NumberingType.POLICY_NUMBER, factors);
        return policyNumber;
    }

    @Override
    public String generateProposalNumber(Policy policy) {
        Map<NumberingFactor, String> factors = new HashMap<NumberingFactor, String>();
        factors.put(NumberingFactor.ORG_CODE, orgCode);
        Date date = new Date();
        factors.put(NumberingFactor.TRANS_YEAR, new SimpleDateFormat("yyyy").format(date));
        factors.put(NumberingFactor.TRANS_MONTH, new SimpleDateFormat("MM").format(date));
        factors.put(NumberingFactor.TRANS_DAY, new SimpleDateFormat("dd").format(date));

        String proposalNumber = numberingService.generateNumber(NumberingType.PROPOSAL_NUMBER, factors);
        return proposalNumber;
    }

    @Override
    public String generateQuotationNumber(Quotation quotation) {
        Map<NumberingFactor, String> factors = new HashMap<NumberingFactor, String>();
        factors.put(NumberingFactor.ORG_CODE, orgCode);
        Date date = new Date();
        factors.put(NumberingFactor.TRANS_YEAR, new SimpleDateFormat("yyyy").format(date));
        factors.put(NumberingFactor.TRANS_MONTH, new SimpleDateFormat("MM").format(date));
        factors.put(NumberingFactor.TRANS_DAY, new SimpleDateFormat("dd").format(date));

        String quotationNumber = numberingService.generateNumber(NumberingType.QUOTATION_NUMBER,factors);
        return quotationNumber;
    }

    @Override
    public String generateEndorsementNumber(Endorsement endorsement) {
        Map<NumberingFactor, String> factors = new HashMap<NumberingFactor, String>();
        factors.put(NumberingFactor.ORG_CODE, orgCode);
        Date date = new Date();
        factors.put(NumberingFactor.TRANS_YEAR, new SimpleDateFormat("yyyy").format(date));
        factors.put(NumberingFactor.TRANS_MONTH, new SimpleDateFormat("MM").format(date));
        factors.put(NumberingFactor.TRANS_DAY, new SimpleDateFormat("dd").format(date));

        String endorsementNumber = numberingService.generateNumber(NumberingType.ENDORSEMENT_NUMBER,factors);
        return endorsementNumber;
    }
}
