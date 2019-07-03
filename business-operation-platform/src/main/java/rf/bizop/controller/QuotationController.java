package rf.bizop.controller;

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
import rf.foundation.model.ResponsePage;
import rf.policyadmin.ds.QuotationService;
import rf.policyadmin.model.QueryCondition;
import rf.policyadmin.model.Quotation;

@RestController
@RequestMapping("v0/api/quotation")
public class QuotationController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private QuotationService quotationService;

    @PostMapping(value = "/query")
    public ResponseEntity query(@RequestBody QueryCondition condition) {

        ResponsePage<Quotation> quotations = quotationService.findQuotations(condition);

        return new ResponseEntity(quotations, HttpStatus.OK);
    }

}
