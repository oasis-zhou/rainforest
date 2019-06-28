package rf.policyadmin.repository;

import org.springframework.data.repository.CrudRepository;
import rf.policyadmin.repository.pojo.TOrder;

public interface OrderDao extends CrudRepository<TOrder, String> {
}
