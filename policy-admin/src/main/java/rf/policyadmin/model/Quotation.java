package rf.policyadmin.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.Data;
import rf.foundation.anno.FieldSpec;
import rf.foundation.model.ModelComponent;
import rf.foundation.pub.Guid;
import rf.policyadmin.model.enums.QuotationStatus;


import java.util.Date;
import java.util.List;
import java.util.Map;


@Data
public class Quotation extends ModelComponent {

    @FieldSpec(code = "PRODUCT_CODE", name = "policy product code")
    private String productCode;
    private String productName;
    @FieldSpec(code = "CHANNEL_CODE", name = "policy channel code")
    private String channelCode;
    @FieldSpec(code = "POLICY_EFFECTIVE_DATE", name = "policy effective date")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ", locale = "zh", timezone = "GMT+8")
    private Date effectiveDate;
    @FieldSpec(code = "POLICY_EXPIRED_DATE", name = "policy expired date")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ", locale = "zh", timezone = "GMT+8")
    private Date expiredDate;
    @FieldSpec(code = "POLICY_POI", name = "period of insurance")
    private Integer poi;
    private Date proposalDate;
    private QuotationStatus quotationStatus;
    private String businessOrgan;
    private String businessSource;
    private String quotationNumber;
    private Date quotationDate;
    private Map<String,Object> underwritingReason = Maps.newHashMap();
    private String rejectReason;
    private String extReferenceNumber;
    private String sku;
    private Customer policyHolder;
    private List<Customer> insureds = Lists.newArrayList();

    public Quotation(){
        this.setUuid(Guid.generateStrId());
    }

}
