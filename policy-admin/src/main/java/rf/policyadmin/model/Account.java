package rf.policyadmin.model;

import lombok.Data;
import rf.foundation.model.ModelComponent;

/**
 * Created by zhouzheng on 2017/6/13.
 */

@Data
public class Account extends ModelComponent {

    private String accountName;
    private String idNumber;
    private String accountNumber;
    private String bankCode;

}
