package rf.customer.ds;

import rf.customer.model.Customer;

public interface CustomerNumberService {

    String generateCustomerCode(Customer customer);
}
