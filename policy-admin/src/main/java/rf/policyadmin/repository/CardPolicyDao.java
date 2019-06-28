package rf.policyadmin.repository;

import org.springframework.data.repository.CrudRepository;
import rf.policyadmin.repository.pojo.TCardPolicy;

public interface CardPolicyDao extends CrudRepository<TCardPolicy, String> {
}
