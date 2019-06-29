package rf.finance.model;

import lombok.Data;
import rf.foundation.model.ModelComponent;
import rf.foundation.pub.Guid;
import java.math.BigDecimal;

@Data
public class Payment extends ModelComponent {

    private String transactionType;
    private String refBizNumber;
    private String refExtNumber;
    private String feeCode;
    private BigDecimal amount;
    private String currency;
    private PayerPayee payerPayee;

    public Payment(){
        this.setUuid(Guid.generateStrId());
    }

}
