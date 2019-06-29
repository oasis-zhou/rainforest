package rf.product.repository.pojo;

import lombok.Data;
import rf.foundation.model.BaseEntity;
import javax.persistence.*;
import java.util.Date;


@Entity
@Data
@Table(name = "T_SKU")
public class TSKU extends BaseEntity {

    @Id
    private String uuid;
    private String code;
    private String name;
    private Date effectiveDate;
    private Date expiredDate;
    private String status;
    @Lob
    private String content;

}
