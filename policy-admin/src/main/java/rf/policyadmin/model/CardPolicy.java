package rf.policyadmin.model;

import lombok.Data;
import rf.foundation.model.ModelComponent;
import java.util.Date;


@Data
public class CardPolicy extends ModelComponent {

    private String productCode;
    private String sku;
    private String channelCode;
    private String cardNumber;
    private Date expiredDate;
    private Boolean active;
    private Customer owner;

}
