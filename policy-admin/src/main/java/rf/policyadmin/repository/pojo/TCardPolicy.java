package rf.policyadmin.repository.pojo;

import lombok.Data;
import rf.foundation.model.BaseEntity;
import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name = "T_CARD_POLICY")
public class TCardPolicy extends BaseEntity {

    @Id
    private String uuid;
    private String productCode;
    private String sku;
    private String channelCode;
    private String cardNumber;
    private Date expiredDate;
    private Boolean isActive;
    @Lob
    private String content;

}
