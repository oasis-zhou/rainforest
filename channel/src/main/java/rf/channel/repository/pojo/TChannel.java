package rf.channel.repository.pojo;

import lombok.Data;
import rf.foundation.model.BaseEntity;
import javax.persistence.*;

/**
 * Created by admin on 2017/8/22.
 */
@Entity
@Data
@Table(name = "T_CHANNEL")
public class TChannel extends BaseEntity {
    @Id
    private String uuid;
    private String name;
    private String code;
    private String type;
    private String accessKey;
    private String secretKey;
    private String parentChannel;
    @Lob
    private String content;

}
