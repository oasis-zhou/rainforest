package rf.policyadmin.model;

import lombok.Data;
import rf.foundation.model.ModelComponent;
import rf.policyadmin.model.enums.OrderStatus;
import java.math.BigDecimal;
import java.util.Date;


@Data
public class Order extends ModelComponent {

    private String orderNumber;
    private String businessOrgan;
    private OrderStatus status;
    private Date orderingTime;
    private String channelCode;
    private String customerName;
    private String customerIdType;
    private String customerIdNumber;
    private String contactInfo;
    private BigDecimal amount;
    private String deliveryAddress;
    private String postalCode;
    private Date deliveryDate;
    private String memo;
    private boolean needInvoice;

}
