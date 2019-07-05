package rf.policyadmin.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import rf.foundation.anno.FieldSpec;
import java.util.Date;


@Data
public class PersonCustomer extends Customer{
    @FieldSpec(code = "CUST_GENDER", name = "customer gender")
    private String gender;
    @FieldSpec(code = "CUST_BIRTHDAY", name = "customer birthday")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ", locale = "zh", timezone = "GMT+8")
    private Date birthday;
    private String mail;
    private String relationWithPH;

}
