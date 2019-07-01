package rf.claim.model;

import lombok.Data;
import rf.claim.model.enums.NoticeStatus;
import rf.foundation.model.ModelComponent;

import java.util.Date;

@Data
public class NoticeOfLoss extends ModelComponent {

    /**
     * 案件单号
     */
    private String noticeNumber;
    /**
     * 保单号
     */
    private String policyNumber;
    /**
     * 出险时间
     */
    private Date accidentTime;
    /**
     * 报案时间
     */
    private Date noticeTime;
    /**
     * 出险原因
     */
    private String accidentCause;
    /**
     * 出险描述
     */
    private String accidentDescription;

    /**
     * 报案人信息
     */
    private Claimant claimant;

    /**
     * 案件状态
     */
    private NoticeStatus noticeStatus;

    /**
     * 结案日期
     */
    private Date closeDate;

    /**
     * 拒赔理由
     */
    private String rejectReason;

}
