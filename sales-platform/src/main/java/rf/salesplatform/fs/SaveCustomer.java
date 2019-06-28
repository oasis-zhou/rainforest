package rf.salesplatform.fs;

import com.google.common.collect.Lists;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import rf.customer.ds.CustomerService;
import rf.customer.model.OrganizationCustomer;
import rf.foundation.pub.FunctionSlice;
import rf.customer.model.PersonCustomer;
import rf.policyadmin.model.Policy;

import java.util.List;
import java.util.Map;


@Component
public class SaveCustomer implements FunctionSlice<Policy> {

    @Autowired
    private CustomerService customerService;

    @Override
    public void execute(Policy policy, Map<String, Object> context){

        List<rf.policyadmin.model.Customer> customerList = Lists.newArrayList();
        customerList.add(policy.getPolicyHolder());
        customerList.addAll(policy.getInsureds());

        customerList.forEach(customer -> {
            if(customer instanceof rf.policyadmin.model.PersonCustomer){
                PersonCustomer cust = new PersonCustomer();
                String uuid = cust.getUuid();
                BeanUtils.copyProperties(customer, cust);
                cust.setUuid(uuid);
                String custCode = customerService.generateCustomer(cust);
                customer.setCode(custCode);
            }else if(customer instanceof rf.policyadmin.model.OrganizationCustomer){
                OrganizationCustomer cust = new OrganizationCustomer();
                String uuid = cust.getUuid();
                BeanUtils.copyProperties(customer, cust);
                cust.setUuid(uuid);
                String custCode = customerService.generateCustomer(cust);
                customer.setCode(custCode);
            }
        });
    }
}
