package rf.salesplatform.controller;

import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rf.foundation.context.AppContext;
import rf.foundation.numbering.NumberingFactor;
import rf.foundation.numbering.NumberingService;
import rf.foundation.numbering.NumberingType;
import rf.foundation.pub.FunctionSliceBundle;
import rf.policyadmin.ds.BusinessNumberService;
import rf.policyadmin.model.Fee;
import rf.policyadmin.model.Policy;
import rf.policyadmin.model.Quotation;
import rf.policyadmin.model.trans.PolicyTransformer;
import rf.salesplatform.event.QuotationEvent;
import rf.salesplatform.fs.AutoUnderwriting;
import rf.salesplatform.fs.DataValidation;
import rf.salesplatform.fs.NewbizPricing;
import rf.salesplatform.fs.SetupPolicyForFixCoverage;
import rf.salesplatform.pub.Constants;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("v0/api/quotation")
public class QuotationController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private PolicyTransformer policyTransformer;
    @Autowired
    private BusinessNumberService businessNumberService;

    @Transactional
    @PostMapping(value = "/pricing")
    public ResponseEntity price(@RequestBody Quotation quotation) {

            long s = System.currentTimeMillis();
            if (quotation.getQuotationNumber() == null) {
                String quotationNumber = businessNumberService.generateQuotationNumber(quotation);
                quotation.setQuotationNumber(quotationNumber);
            }

            Policy policy = policyTransformer.transFromQuotation(quotation);

            Map<String, Object> context = Maps.newHashMap();

            context.put(Constants.AUTO_UNDERWRITING_RULE_SET, Constants.RULE_SET_UW);

            new FunctionSliceBundle(policy, context)
                    .register(SetupPolicyForFixCoverage.class)
                    .register(DataValidation.class)
                    .register(AutoUnderwriting.class)
                    .register(NewbizPricing.class)
                    .execute();

            quotation.getSubComponents().addAll(policy.getSubComponentsByType(Fee.class));
            quotation.getUnderwritingReason().putAll(policy.getUnderwritingReason());
            QuotationEvent event = new QuotationEvent(quotation);
            AppContext.getApplicationContext().publishEvent(event);

            Map<String, Object> response = Maps.newHashMap();
            response.put("quotationNumber", quotation.getQuotationNumber());
            response.put("policyFees", policy.getSubComponentsByType(Fee.class));
//            response.put("underwritingReason",policy.getUnderwritingReason());
            long e = System.currentTimeMillis();
            logger.info("保费计算耗时" + (e - s) + "ms");
            return new ResponseEntity(response, HttpStatus.OK);


    }

}
