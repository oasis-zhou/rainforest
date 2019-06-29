package rf.customer.repository.pojo;

import lombok.Data;
import rf.foundation.model.BaseEntity;
import javax.persistence.*;


@Entity
@Data
@Table(name = "T_CUSTOMER")
public class TCustormer extends BaseEntity {

    @Id
    private String uuid;
    private String name;
    private String idType;
    private String idNumber;
    private String code;
    @Lob
    private String content;

}
