package rf.customer.ds;

import rf.customer.model.Customer;
import rf.customer.model.QueryCondition;
import rf.foundation.model.ResponsePage;

public interface CustomerService {

    String generateCustomer(Customer customer);

    Customer loadById(String idType, String idNumber);

    Customer loadByCode(String code);

    ResponsePage<Customer> findCustomers(QueryCondition condition);
}
