package rf.channel.model;

import rf.channel.model.enums.ChannelType;
import rf.foundation.model.ModelComponent;
import rf.foundation.pub.Guid;

public class ChannelSpec extends ModelComponent {

    private String name;
    private String code;
    private ChannelType type;
    private String description;
    private String parentChannel;
    private String accessKey;
    private String secretKey;


    public ChannelSpec(){
        this.setUuid(Guid.generateStrId());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public ChannelType getType() {
        return type;
    }

    public void setType(ChannelType type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getParentChannel() {
        return parentChannel;
    }

    public void setParentChannel(String parentChannel) {
        this.parentChannel = parentChannel;
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
}
