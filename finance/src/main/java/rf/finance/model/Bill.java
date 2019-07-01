package rf.finance.model;

import lombok.Data;
import rf.finance.model.enums.BillStatus;
import rf.finance.model.enums.TransactionType;
import rf.foundation.model.ModelComponent;
import rf.foundation.pub.Guid;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class Bill extends ModelComponent {

    private TransactionType transType;
    private String refBizNumber;
    private String refExtNumber;
    private Date transDate;
    private String feeCode;
    private BigDecimal amount;
    private BigDecimal balance;
    private String currency;
    private Date dueDate;
    private BillStatus status;
    private PayerPayee payerPayee;

}
