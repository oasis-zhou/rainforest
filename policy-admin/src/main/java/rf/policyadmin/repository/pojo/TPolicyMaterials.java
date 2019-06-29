package rf.policyadmin.repository.pojo;

import lombok.Data;
import rf.foundation.model.BaseEntity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by zhouzheng on 2017/11/23.
 */

@Entity

@Data
@Table(name = "T_POLICY_MATERIALS")
public class TPolicyMaterials extends BaseEntity {

    @Id
    private String uuid;
    private String name;
    private String path;
    private String format;
    private String size;

}
