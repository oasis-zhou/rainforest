package rf.customer.repository.pojo;

import rf.foundation.model.BaseEntity;
import javax.persistence.*;


@Entity
@Table(name = "T_CUSTOMER")
public class TCustormer extends BaseEntity {

    @Id
    private String uuid;
    @Column
    private String name;
    @Column
    private String idType;
    @Column
    private String idNumber;
    @Column
    private String code;
    @Lob
    private String content;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
