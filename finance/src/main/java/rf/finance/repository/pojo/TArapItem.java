package rf.finance.repository.pojo;

import lombok.Data;
import rf.foundation.model.BaseEntity;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


@Entity
@Data
@Table(name = "T_ARAP_ITEM")
public class TArapItem extends BaseEntity {

    @Id
    private String uuid;
    private String transType;
    private String refBizNumber;
    private String refExtNumber;
    private Date transDate;
    private String feeCode;
    private BigDecimal amount;
    private BigDecimal balance;
    private String currency;
    private Date dueDate;
    @Lob
    @Column(length = 10000)
    private String content;

}
