package rf.policyadmin.model;

import lombok.Data;
import rf.foundation.model.ModelComponent;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by zhouzheng on 2017/5/3.
 */

@Data
public class PolicyIndex extends ModelComponent {

    private String policyNumber;
    private String proposalNumber;
    private String quotationNumber;
    private String productName;
    private String productCode;
    private String channelCode;
    private BigDecimal sgp;
    private BigDecimal snp;
    private BigDecimal app;
    private BigDecimal limitAmount;
    private String policyHolderName;
    private String policyHolderIdType;
    private String policyHolderIdNumber;
    private String policyInsuredName;
    private String policyInsuredIdType;
    private String policyInsuredIdNumber;
    private String mobile;
    private Date effectiveDate;
    private Date expiredDate;
    private Date proposalDate;
    private Date issueDate;
    private String contractStatusCode;
    private Date terminalDate;
    private String terminalReasonCode;
    private String businessOrgan;
    private String businessSource;
    private String extReferenceNumber;

}
