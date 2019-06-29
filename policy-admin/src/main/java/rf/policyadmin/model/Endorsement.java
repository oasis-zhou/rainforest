package rf.policyadmin.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import rf.foundation.anno.FieldSpec;
import rf.foundation.model.ModelComponent;
import rf.foundation.pub.Guid;
import rf.policyadmin.model.enums.EndorsementApplicationType;
import rf.policyadmin.model.enums.EndorsementStatus;
import rf.policyadmin.model.enums.EndorsementType;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class Endorsement extends ModelComponent {

    private String policyNumber;
    private String productCode;
    private String channelCode;
    @FieldSpec(code = "ENDO_TYPE", name = "endorsement type")
    private EndorsementType endorsementType;
    @FieldSpec(code = "ENDO_CODE", name = "endorsement sub type code")
    private String code;
    private String name;
    private EndorsementStatus endorsementStatus;
    @FieldSpec(code = "ENDO_EFFECTIVE_DATE", name = "endorsement effective date")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ", locale = "zh", timezone = "GMT+8")
    private Date effectiveDate;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ", locale = "zh", timezone = "GMT+8")
    private Date applicationDate;
    private Date issueDate;
    private String applicationName;
    private String endorsementNumber;
    private String wording;
    private EndorsementApplicationType applicationType;
    private Integer sequence;
    private BigDecimal premium;

    public Endorsement(){
        this.setUuid(Guid.generateStrId());
    }

    public Fee getEndoFeeByCode(String feeCode){

        List<EndorsementFee> EndorsementFees = getSubComponentsByType(EndorsementFee.class);
        if(EndorsementFees.size() > 0){
            EndorsementFee endorsementFee = EndorsementFees.get(0);
            List<Fee> fees = endorsementFee.getSubComponentsByType(Fee.class);
            for(Fee fee : fees){
                if(fee.getCode().equals(feeCode))
                    return fee;
            }
        }

        return null;
    }

}
