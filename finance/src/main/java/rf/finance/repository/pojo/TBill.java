package rf.finance.repository.pojo;

import lombok.Data;
import rf.foundation.model.BaseEntity;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


@Entity
@Data
@Table(name = "T_BILL")
public class TBill extends BaseEntity {

    @Id
    private String uuid;
    private String transType;
    private String billNumber;
    private String refBizNumber;
    private String refExtNumber;
    private Date transDate;
    private String feeCode;
    private BigDecimal amount;
    private BigDecimal balance;
    private String currency;
    private Date dueDate;
    private String payerPayee;
    private String payerPayeeIdNumber;
    private String statusCode;
    @Lob
    @Column(length = 10000)
    private String content;

}
