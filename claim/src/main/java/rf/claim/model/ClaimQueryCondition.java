package rf.claim.model;

import lombok.Data;
import java.util.Date;

/**
 * Created by zhouzheng on 2017/5/24.
 *
 */
@Data
public class ClaimQueryCondition {
    /**
     * 保单号
     */
    private String policyNumber;
    /**
     * 理赔号
     */
    private String productCode;
    /**
     * 理赔号
     */
    private String claimNumber;
    /**
     * 理赔申请人
     */
    private String claimantName;
    /**
     * 理赔状态
     */
    private String status;
    /**
     * 申请日期起
     */
    private Date claimTimeStart;
    /**
     * 申请日期止
     */
    private Date claimTimeEnd;

}
