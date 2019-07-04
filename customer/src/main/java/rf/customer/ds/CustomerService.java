package rf.customer.ds;

import rf.customer.model.Customer;

public interface CustomerService {

    String generateCustomer(Customer customer);

    Customer findCustomerById(String idType, String idNumber);
}
