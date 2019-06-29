package rf.policyadmin.model;

import lombok.Data;
import rf.foundation.model.ModelComponent;
import rf.foundation.pub.Guid;

import java.util.Date;

/**
 * Created by zhouzheng on 2017/11/23.
 */

@Data
public class PolicyMaterials extends ModelComponent {

    private String name;
    private String path;
    private Date createTime;
    private String format;
    private String size;

    public PolicyMaterials(){
        this.setUuid(Guid.generateStrId());
    }

}
