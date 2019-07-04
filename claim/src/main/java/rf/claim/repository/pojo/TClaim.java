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
@Table(name = "T_CLAIM")
public class TClaim extends BaseEntity {

    @Id
    private String uuid;
    private String claimNumber;
    private String noticeNumber;
    private String policyNumber;
    private String productCode;
    private String claimant;
    private String mobile;
    private Date noticeTime;
    private Date accidentTime;
    private Date claimTime;
    private String statusCode;
    @Lob
    private String content;
}
