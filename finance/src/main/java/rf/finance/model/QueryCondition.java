package rf.finance.model;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @ClassName QueryCondition
 * @Description: TODO
 * @Author zhouzheng
 * @Date 2019/7/3
 * @Version V1.0
 **/
@Data
public class QueryCondition {

    private String transType;
    private String refBizNumber;
    private String refExtNumber;
    private String payerPayee;
    private String payerPayeeIdNumber;
    private String statusCode;
    private Date dateStart;
    private Date dateEnd;

    private int pageNo;
    private int pageSize;
}
