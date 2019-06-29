package rf.policyadmin.repository.pojo;

import lombok.Data;
import rf.foundation.model.BaseEntity;
import javax.persistence.*;

@Entity

@Data
@Table(name = "T_POLICY_LOG")
public class TPolicyLog extends BaseEntity {

    @Id
    private String uuid;
    private String endoId;
    private String policyNumber;
    private String logType;
    @Lob
    private String content;

}
