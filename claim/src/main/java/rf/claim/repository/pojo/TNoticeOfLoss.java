package rf.claim.repository.pojo;

import lombok.Data;
import rf.foundation.model.BaseEntity;
import javax.persistence.*;
import java.util.Date;

/**
 * Created by zhouzheng on 2017/5/1.
 */
@Entity
@Data
@Table(name = "T_NOTICE_OF_LOSS")
public class TNoticeOfLoss extends BaseEntity {

    @Id
    private String uuid;
    private String noticeNumber;
    private String policyNumber;
    private Date accidentTime;
    private Date noticeTime;
    private String noticeStatus;
    @Lob
    private String content;
}
