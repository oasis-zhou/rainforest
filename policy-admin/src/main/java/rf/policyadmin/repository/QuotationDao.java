package rf.policyadmin.repository;

import org.springframework.data.repository.CrudRepository;
import rf.policyadmin.repository.pojo.TQuotation;

public interface QuotationDao extends CrudRepository<TQuotation, String> {
}
