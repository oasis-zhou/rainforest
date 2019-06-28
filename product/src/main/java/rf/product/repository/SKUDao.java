package rf.product.repository;

import org.springframework.data.repository.CrudRepository;
import rf.product.repository.pojo.TSKU;

public interface SKUDao extends CrudRepository<TSKU, String> {
}
