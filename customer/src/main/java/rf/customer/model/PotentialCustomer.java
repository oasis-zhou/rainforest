package rf.customer.model;

import lombok.Data;

@Data
public class PotentialCustomer extends Customer {
    private String name;
    private String contractInfo;

}
