package rf.bizop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rf.customer.ds.CustomerService;
import rf.customer.model.Customer;
import rf.customer.model.QueryCondition;
import rf.foundation.model.ResponsePage;

import javax.websocket.server.PathParam;

/**
 * @ClassName CustomerController
 * @Description: TODO
 * @Author zhouzheng
 * @Date 2019/7/2
 * @Version V1.0
 **/
@RestController
@RequestMapping("v0/api/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @PostMapping("/query")
    public ResponseEntity query(@RequestBody QueryCondition condition) {
        ResponsePage<Customer> customers = customerService.findCustomers(condition);

        return new ResponseEntity(customers, HttpStatus.OK);
    }

    @GetMapping(value = "/load/{code}")
    public ResponseEntity loadCustomer(@PathParam("code") String code) {
        Customer customer = customerService.loadByCode(code);

        return new ResponseEntity(customer, HttpStatus.OK);
    }
}
