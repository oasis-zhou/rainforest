package rf.cohorizon.model;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import lombok.Data;
import rf.foundation.pub.Guid;

import java.io.Serializable;
import java.util.Map;
import java.util.Set;

/**
 * @ClassName Transaction
 * @Description: TODO
 * @Author zhouzheng
 * @Date 2019/7/10
 * @Version V1.0
 **/
@Data
public class Transaction implements Serializable {

    private String transactionNumber;
    private String businessData;
    private String from;
    private String to;
    private Set<String> participants = Sets.newHashSet();
    private Map<String,String> cryptoKeys = Maps.newHashMap();

    public Transaction() {
        this.transactionNumber = Guid.generateStrId();
    }
}
