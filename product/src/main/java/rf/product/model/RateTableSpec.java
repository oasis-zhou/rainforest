package rf.product.model;

import lombok.Data;
import rf.foundation.model.ModelComponent;
import java.util.Date;

/**
 * Created by zhouzheng on 2017/4/28.
 */

@Data
public class RateTableSpec extends ModelComponent {

    private String name;
    private String code;
    private String description;
    private Date effectiveDate;
    private Date expiredDate;
    private String status;

}
