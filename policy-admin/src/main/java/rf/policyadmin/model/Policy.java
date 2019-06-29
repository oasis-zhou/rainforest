package rf.policyadmin.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import lombok.Data;
import org.joda.time.Days;
import org.joda.time.LocalDateTime;
import rf.foundation.anno.FieldSpec;
import rf.foundation.model.ModelComponent;
import rf.foundation.pub.Guid;
import rf.policyadmin.model.enums.ContractStatus;
import rf.policyadmin.model.enums.TerminalReason;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;


@Data
public class Policy extends ModelComponent {

    @FieldSpec(code = "PRODUCT_CODE", name = "policy product code")
    private String productCode;
    private String productName;
    @FieldSpec(code = "CHANNEL_CODE", name = "policy channel id")
    private String channelCode;
    private String policyNumber;
    @FieldSpec(code = "POLICY_EFFECTIVE_DATE", name = "policy effective date")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ", locale = "zh", timezone = "GMT+8")
    private Date effectiveDate;
    @FieldSpec(code = "POLICY_EXPIRED_DATE", name = "policy expired date")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ", locale = "zh", timezone = "GMT+8")
    private Date expiredDate;
    @FieldSpec(code = "POLICY_POI", name = "period of insurance")
    private Integer poi;
    private String proposalNumber;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ", locale = "zh", timezone = "GMT+8")
    private Date proposalDate;
    private ContractStatus contractStatus;
    private String businessOrgan;
    private String businessSource;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ", locale = "zh", timezone = "GMT+8")
    private Date issueDate;
    private String quotationNumber;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ", locale = "zh", timezone = "GMT+8")
    private Date quotationDate;
    private TerminalReason terminalReason;
    private Date terminalDate;
    private Map<String,Object> underwritingReason = Maps.newHashMap();
    private String extReferenceNumber;
    private String sku;
    private BigDecimal AOAAmount;
    private BigDecimal AOPAmount;
    private Customer policyHolder;
    private List<Customer> insureds = Lists.newArrayList();


    public Policy(){
        this.setUuid(Guid.generateStrId());
    }


    public Fee getPolicyFeeByCode(String feeCode){
        for(Fee fee : getSubComponentsByType(Fee.class)){
            if(fee.getCode().equals(feeCode))
                return fee;
        }

        return null;
    }


    public void setExpiredDate(Date expiredDate) {
        this.expiredDate = expiredDate;
        if(this.expiredDate != null && this.effectiveDate != null){
            Days days = Days.daysBetween(new LocalDateTime(this.effectiveDate), new LocalDateTime(this.expiredDate));
            this.poi = days.getDays() + 1;
        }
    }


}
