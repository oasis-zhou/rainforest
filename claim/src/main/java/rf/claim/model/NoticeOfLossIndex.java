package rf.claim.model;



import rf.claim.model.enums.NoticeStatus;
import rf.foundation.model.ModelComponent;
import java.util.Date;


public class NoticeOfLossIndex  extends ModelComponent {

    /**
     * 案件单号，申请号
     */
    private String noticeNumber;
    /**
     * 报案人姓名
     */
    private String noticeAntName;
    /**
     * 案件状态
     */
    private String noticeStatus;
    /**
     * 理赔申请人手机号码
     */
    private String mobile;
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

}
