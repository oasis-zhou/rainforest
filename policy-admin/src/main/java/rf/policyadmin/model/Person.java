package rf.policyadmin.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.joda.time.Days;
import org.joda.time.LocalDate;
import org.joda.time.Years;
import rf.foundation.anno.FieldSpec;
import rf.foundation.pub.Guid;
import java.util.Date;


public class Person extends InsuredObject {

    private String name;
    private String idType;
    private String idNumber;
    @FieldSpec(code = "INSURED_GENDER", name = "insured person gender")
    private String gender;
    @FieldSpec(code = "INSURED_BIRTHDAY", name = "insured person birthday")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ", locale = "zh", timezone = "GMT+8")
    private Date birthday;
    @FieldSpec(code = "INSURED_OCCUPATION", name = "insured person occupation")
    private String occupation;
    @FieldSpec(code = "INSURED_AGE", name = "insured person age")
    private Integer age;
    private String fullAddress;

    public Person(){
        this.setUuid(Guid.generateStrId());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdType() {
        return idType;
    }

    public void setIdType(String idType) {
        this.idType = idType;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

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
        if(birthday != null){
            Years years = Years.yearsBetween(new LocalDate(birthday), new LocalDate(new Date()));
            Days days = Days.daysBetween(new LocalDate(birthday), new LocalDate(new Date()));
            this.setAge(years.getYears());
            this.getDynamicFields().put("INSURED_AGE_DAYS",days.getDays());
        }
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getFullAddress() {
        return fullAddress;
    }

    public void setFullAddress(String fullAddress) {
        this.fullAddress = fullAddress;
    }
}
