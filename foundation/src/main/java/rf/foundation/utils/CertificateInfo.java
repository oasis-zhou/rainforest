package rf.foundation.utils;

import java.util.Date;

/**
 * Created by zhouzheng on 2017/5/22.
 */
public class CertificateInfo {

    private String sex;
    private Date birthday;
    private int age;
    private String errorMessage;

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
