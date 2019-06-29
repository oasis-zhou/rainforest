package rf.policyadmin.model;

import lombok.Data;

import java.util.Date;

/**
 * Created by zhouzheng on 2017/5/24.
 */

@Data
public class PolicyQueryCondition {

    private String policyNumber;
    private String proposalNumber;
    private String productCode;
    private String channelCode;
    private String policyHolderName;
    private String policyHolderIdNumber;
    private String policyInsuredName;
    private String policyInsuredIdNumber;
    private String mobile;
    private String contractStatus;
    private Date proposalDateStart;
    private Date proposalDateEnd;

}
