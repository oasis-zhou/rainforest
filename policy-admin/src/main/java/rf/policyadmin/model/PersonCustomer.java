package rf.policyadmin.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import rf.foundation.anno.FieldSpec;
import java.util.Date;


public class PersonCustomer extends Customer{
    @FieldSpec(code = "CUST_GENDER", name = "customer gender")
    private String gender;
    @FieldSpec(code = "CUST_BIRTHDAY", name = "customer birthday")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ", locale = "zh", timezone = "GMT+8")
    private Date birthday;
    private String mobile;
    private String phone;
    private String mail;
    private String relationWithPH;

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getRelationWithPH() {
        return relationWithPH;
    }

    public void setRelationWithPH(String relationWithPH) {
        this.relationWithPH = relationWithPH;
    }
}
