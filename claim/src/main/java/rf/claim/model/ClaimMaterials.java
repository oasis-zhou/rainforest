package rf.claim.model;


import lombok.Data;
import rf.foundation.model.ModelComponent;
import java.util.Date;

/**
 * Created by zhouzheng on 2017/5/1.
 */
@Data
public class ClaimMaterials extends ModelComponent {

    private String name;
    private String description;
    private String path;
    private Date createTime;
    private String format;
    private String size;
    private String materialType;

    /**
     * 图片的base64数据， 用于直接展示缩略图数据
     */
    private String base64Data;

}
