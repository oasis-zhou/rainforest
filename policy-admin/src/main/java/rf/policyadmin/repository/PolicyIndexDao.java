package rf.policyadmin.repository;

import org.springframework.data.repository.CrudRepository;
import rf.policyadmin.repository.pojo.TPolicyIndex;

/**
 * Created by zhouzheng on 2017/5/3.
 */
public interface PolicyIndexDao extends CrudRepository<TPolicyIndex, String> {
}
