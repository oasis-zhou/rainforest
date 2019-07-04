package rf.claim.model;


import lombok.Data;
import org.springframework.boot.autoconfigure.session.StoreType;
import rf.foundation.model.ModelComponent;
import java.util.Date;

/**
 * Created by zhouzheng on 2017/5/1.
 */
@Data
public class ClaimMaterials extends ModelComponent {

    private String key;
    private String name;
    private String description;
    private String bucket;
    private String url;
    private Date createTime;
    private String format;
    private String size;
    private String materialType;
    private String ipfsHash;
    private StoreType storeType;

}
