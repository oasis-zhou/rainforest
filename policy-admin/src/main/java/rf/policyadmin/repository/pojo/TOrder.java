package rf.policyadmin.repository.pojo;

import lombok.Data;
import rf.foundation.model.BaseEntity;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


@Entity

@Data
@Table(name = "T_ORDER")
public class TOrder extends BaseEntity {

    @Id
    private String uuid;
    private String orderNumber;
    private String businessOrgan;
    private String statusCode;
    private Date orderingTime;
    private String channelCode;
    private String customerName;
    private BigDecimal amount;
    private Date deliveryDate;
    @Lob
    private String content;

}
