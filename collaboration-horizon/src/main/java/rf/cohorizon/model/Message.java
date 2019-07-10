package rf.cohorizon.model;

import lombok.Data;
import rf.foundation.pub.Guid;
import java.io.Serializable;

/**
 * @ClassName Message
 * @Description: TODO
 * @Author zhouzheng
 * @Date 2019/7/10
 * @Version V1.0
 **/
@Data
public class Message implements Serializable {

    private String msgID;
    private String name;
    private String transactionNumber;
    private String from;
    private String to;
    private String content;
    private String pubKey;
    private String cryptoKey;

    public Message() {
        this.msgID = Guid.generateStrId();
    }

}
