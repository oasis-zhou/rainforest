package rf.customer.ds;

import rf.customer.model.Customer;

public interface CustomerService {

    public String generateCustomer(Customer customer);

    public Customer findCustomerById(String idType, String idNumber);
}
