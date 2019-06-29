package rf.policyadmin.model;


import lombok.Data;

@Data
public class OrganizationCustomer extends Customer {

    private String phone;
    private String mail;
    private String fax;
    private String contactPersonName;

}
