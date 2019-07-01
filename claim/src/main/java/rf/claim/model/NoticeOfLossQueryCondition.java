package rf.claim.model;

import lombok.Data;

@Data
public class NoticeOfLossQueryCondition {

    /**
     * 理赔申请人，投保人或者被保人
     */
    private String claimant;
    /**
     * 理赔申请人证件号码，投保人或者被保人
     */
    private String idNumber;
    /**
     * 理赔申请人手机号码
     */
    private String mobile;
    /**
     * 案件号
     */
    private String noticeNumber;
    /**
     * 案件状态
     */
    private String noticeStatus;

    /**当前页码*/
    private int page=1;

    /**每页展示数据数，默认为10*/
    private int size=10;

}
