package rf.policyadmin.model;

import lombok.Data;
import rf.foundation.anno.FieldSpec;
import rf.foundation.model.ModelComponent;
import rf.foundation.pub.Guid;

import java.math.BigDecimal;

/**
 * Created by zhengzhou on 16/4/12.
 */

@Data
public class Coverage extends ModelComponent {

    private String name;
    @FieldSpec(code = "COVERAGE_CODE", name = "coverage spec code")
    private String code;
    private BigDecimal AOAAmount;
    private BigDecimal AOPAmount;
    private Limit limit;
    private Deductible deductible;

    public Coverage(){
        this.setUuid(Guid.generateStrId());
    }

    public Fee getPolicyFeeByCode(String feeCode){

        for(Fee fee : getSubComponentsByType(Fee.class)){
            if(fee.getCode().equals(feeCode))
                return fee;
        }

        return null;
    }

}
