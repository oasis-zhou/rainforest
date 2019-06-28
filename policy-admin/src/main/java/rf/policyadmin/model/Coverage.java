package rf.policyadmin.model;

import rf.foundation.anno.FieldSpec;
import rf.foundation.model.ModelComponent;
import rf.foundation.pub.Guid;

import java.math.BigDecimal;

/**
 * Created by zhengzhou on 16/4/12.
 */
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public BigDecimal getAOAAmount() {
        return AOAAmount;
    }

    public void setAOAAmount(BigDecimal AOAAmount) {
        this.AOAAmount = AOAAmount;
    }

    public BigDecimal getAOPAmount() {
        return AOPAmount;
    }

    public void setAOPAmount(BigDecimal AOPAmount) {
        this.AOPAmount = AOPAmount;
    }

    public Limit getLimit() {
        return limit;
    }

    public void setLimit(Limit limit) {
        this.limit = limit;
    }

    public Deductible getDeductible() {
        return deductible;
    }

    public void setDeductible(Deductible deductible) {
        this.deductible = deductible;
    }
}
