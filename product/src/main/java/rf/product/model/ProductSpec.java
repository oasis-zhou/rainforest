package rf.product.model;


import lombok.Data;
import rf.foundation.model.ModelComponent;
import rf.foundation.pub.Guid;
import rf.product.model.enums.ProductStatus;

import java.util.Date;


@Data
public class ProductSpec extends ModelComponent {
    private String code;
    private String name;
    private String description;
    private String version;
    private ProductStatus status;
    private String organization;
    private Date effectiveDate;
    private Date expiredDate;
    private Boolean fixedCoverage;

    public ProductSpec(){
        this.setUuid(Guid.generateStrId());
    }

}
