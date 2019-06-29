package rf.policyadmin.model;

import lombok.Data;

import java.util.Date;


@Data
public class EndorsementQueryCondition {
    private String policyNumber;
    private String endorsementNumber;
    private String productCode;
    private String channelCode;
    private String applicationName;
    private String statusCode;
    private Date applicationDateStart;
    private Date applicationDateEnd;

}
