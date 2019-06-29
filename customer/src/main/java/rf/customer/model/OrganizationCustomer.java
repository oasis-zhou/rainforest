package rf.customer.model;

import lombok.Data;

import java.util.Date;

@Data
public class OrganizationCustomer extends Customer {
    private Date registrationDate;
    private String phone;
    private String mail;
    private String fax;
    private String contactPersonName;

}
