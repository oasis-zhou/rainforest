package rf.policyadmin.repository;

import org.springframework.data.repository.CrudRepository;
import rf.policyadmin.repository.pojo.TPolicyMaterials;

/**
 * Created by zhouzheng on 2017/11/23.
 */
public interface PolicyMaterialsDao extends CrudRepository<TPolicyMaterials, String> {
}
