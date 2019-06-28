package rf.channel.repository.pojo;

import rf.foundation.model.BaseEntity;
import javax.persistence.*;

/**
 * Created by admin on 2017/8/22.
 */
@Entity
@Table(name = "T_CHANNEL")
public class TChannel extends BaseEntity {
    @Id
    private String uuid;
    @Column
    private String name;
    @Column
    private String accessKey;
    @Column
    private String secretKey;
    @Lob
    private String content;
    @Column
    private String code;
    @Column
    private String type;
    @Column
    private String parentChannel;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getParentChannel() {
        return parentChannel;
    }

    public void setParentChannel(String parentChannel) {
        this.parentChannel = parentChannel;
    }
}
