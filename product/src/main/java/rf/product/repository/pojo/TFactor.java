package rf.product.repository.pojo;

import rf.foundation.model.BaseEntity;
import javax.persistence.*;


@Entity
@Table(name = "T_FACTOR")
public class TFactor extends BaseEntity {

    @Id
    private String uuid;
    @Column
    private String code;
    @Column
    private String name;
    @Column
    private String dataType;
    @Column
    private String category;
    @Column
    private String validationExpress;
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

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getValidationExpress() {
        return validationExpress;
    }

    public void setValidationExpress(String validationExpress) {
        this.validationExpress = validationExpress;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
