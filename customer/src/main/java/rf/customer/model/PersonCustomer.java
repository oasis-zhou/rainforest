package rf.customer.model;

import lombok.Data;

import java.util.Date;

@Data
public class PersonCustomer extends Customer {

    private String gender;
    private Date birthday;
    private String mail;

}
