package rf.claim.model;

import lombok.Data;

import java.util.Date;

@Data
public class QueryCondition {

    private String policyNumber;
    private String noticeNumber;
    private String claimNumber;
    private String claimant;
    private String claimantIdNumber;
    private String mobile;
    private String status;
    private Date dateStart;
    private Date dateEnd;

    private int pageNo;
    private int pageSize;
}
