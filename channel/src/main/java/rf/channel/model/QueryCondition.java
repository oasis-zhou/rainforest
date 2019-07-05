package rf.channel.model;

import lombok.Data;

/**
 * @ClassName QueryCondition
 * @Description: TODO
 * @Author zhouzheng
 * @Date 2019/7/5
 * @Version V1.0
 **/
@Data
public class QueryCondition {
    private String name;
    private String idType;
    private String idNumber;
    private String code;

    private int pageNo;
    private int pageSize;
}
