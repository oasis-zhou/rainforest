package rf.eval.dt.repository.pojo;

import lombok.Data;
import rf.foundation.model.BaseEntity;
import javax.persistence.*;
import java.util.Date;


@Entity
@Data
@Table(name = "T_DECISION_TABLE")
public class TDecisionTable extends BaseEntity {

    @Id
    private String uuid;
    private String name;
    private String code;
    private Date effectiveDate;
    private Date expiredDate;
    private String status;
    @Lob
    private String content;

}
