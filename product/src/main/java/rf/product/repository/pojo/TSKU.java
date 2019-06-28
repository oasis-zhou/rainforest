package rf.product.repository.pojo;

import rf.foundation.model.BaseEntity;
import javax.persistence.*;
import java.util.Date;


@Entity
@Table(name = "T_SKU")
public class TSKU extends BaseEntity {

    @Id
    private String uuid;
    @Column
    private String code;
    @Column
    private String name;
    @Column
    private Date effectiveDate;
    @Column
    private Date expiredDate;
    @Column
    private String status;
    @Lob
    private String content;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(Date effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public Date getExpiredDate() {
        return expiredDate;
    }

    public void setExpiredDate(Date expiredDate) {
        this.expiredDate = expiredDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
