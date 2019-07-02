package rf.bizop.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rf.policyadmin.model.PolicyQueryCondition;

@RestController
@RequestMapping("v0/api/quotation")
public class QuotationController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Transactional
    @PostMapping(value = "/pricing")
    public ResponseEntity query(@RequestBody PolicyQueryCondition condition) {

        return new ResponseEntity(null, HttpStatus.OK);
    }

}
