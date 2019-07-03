package rf.policyadmin.model;

import lombok.Data;

import java.util.Date;

/**
 * Created by zhouzheng on 2017/5/24.
 */

@Data
public class QueryCondition {

    private String policyNumber;
    private String proposalNumber;
    private String quotationNumber;
    private String endorsementNumber;
    private String productCode;
    private String channelCode;
    private String policyHolderName;
    private String policyHolderIdNumber;
    private String policyInsuredName;
    private String policyInsuredIdNumber;
    private String mobile;
    private String status;
    private Date dateStart;
    private Date dateEnd;

    private int pageNo;
    private int pageSize;

}
