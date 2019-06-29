package rf.policyadmin.model;

import lombok.Data;
import rf.foundation.model.ModelComponent;
import rf.policyadmin.model.enums.PaymentMethod;
import rf.policyadmin.model.enums.PaymentSatus;

import java.util.Date;


@Data
public class Payment extends ModelComponent {

    private PaymentMethod method;
    private String payer;
    private String payerIdType;
    private String payerIdNumber;
    private String amount;
    private String paymentNumber;
    private Date paymentDate;
    private PaymentSatus satus;

}
