package rf.bizop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rf.finance.ds.BillService;
import rf.finance.model.Bill;
import rf.finance.model.QueryCondition;
import rf.foundation.model.ResponsePage;

import rf.policyadmin.model.Quotation;

/**
 * @ClassName FinanceController
 * @Description: TODO
 * @Author zhouzheng
 * @Date 2019/7/2
 * @Version V1.0
 **/
@RestController
@RequestMapping("v0/api/bill")
public class FinanceController {

    @Autowired
    private BillService billService;

    @PostMapping(value = "/query")
    public ResponseEntity query(@RequestBody QueryCondition condition) {

        ResponsePage<Bill> bills = billService.findBills(condition);

        return new ResponseEntity(bills, HttpStatus.OK);
    }
}
