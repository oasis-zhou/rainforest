package rf.channel.model;

import lombok.Data;
import rf.channel.model.enums.ChannelType;
import rf.foundation.model.ModelComponent;
import rf.foundation.pub.Guid;

@Data
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

}
