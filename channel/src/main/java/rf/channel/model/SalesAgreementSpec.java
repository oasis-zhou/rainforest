package rf.channel.model;

import lombok.Data;
import rf.channel.model.enums.AgreementStatus;
import rf.foundation.model.ModelComponent;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @ClassName SalesAgreementSpec
 * @Description: TODO
 * @Author zhouzheng
 * @Date 2019/7/5
 * @Version V1.0
 **/
@Data
public class SalesAgreementSpec extends ModelComponent {

    private String channelCode;
    private String productCode;
    private Date effectiveDate;
    private Date expiredDate;
    private AgreementStatus status;
    private BigDecimal commissionRate;
    private String description;
}
