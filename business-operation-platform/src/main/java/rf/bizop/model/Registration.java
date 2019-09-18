package rf.bizop.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName Registration
 * @Description: TODO
 * @Author zhouzheng
 * @Date 2019/8/9
 * @Version V1.0
 **/
@Data
public class Registration implements Serializable {

    private String orgCode;
    private String pubKey;
    private String accountAddress;
}
