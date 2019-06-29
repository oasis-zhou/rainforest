package rf.product.repository.pojo;

import lombok.Data;
import rf.foundation.model.BaseEntity;

import javax.annotation.sql.DataSourceDefinition;
import javax.persistence.*;


@Entity
@Data
@Table(name = "T_FACTOR")
public class TFactor extends BaseEntity {

    @Id
    private String uuid;
    private String code;
    private String name;
    private String dataType;
    private String category;
    private String validationExpress;
    @Lob
    private String content;

}
