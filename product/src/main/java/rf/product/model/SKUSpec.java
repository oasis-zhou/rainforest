package rf.product.model;

import lombok.Data;
import rf.foundation.model.ModelComponent;
import rf.foundation.pub.Guid;
import rf.product.model.enums.SKUCategory;
import rf.product.model.enums.SKUStatus;
import java.util.Date;


@Data
public class SKUSpec extends ModelComponent {

    private String name;
    private String code;
    private String description;
    private SKUCategory category;
    private SKUStatus status;
    private Date effectiveDate;
    private Date expiredDate;

    public SKUSpec(){
        this.setUuid(Guid.generateStrId());
    }

}
