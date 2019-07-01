package rf.claim.repository.pojo;

import lombok.Data;
import rf.foundation.model.BaseEntity;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Data
@Table(name = "T_NOTICE_OF_LOSS_INDEX")
public class TNoticeOfLossIndex extends BaseEntity {

    @Id
    private String uuid;
    /**
     * 案件单号，申请号
     */
    private String noticeNumber;
    /**
     * 报案人姓名
     */
    private String claimant;
    /**以下是新增字段 by  hcy**/
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
