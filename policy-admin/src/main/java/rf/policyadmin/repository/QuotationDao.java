package rf.policyadmin.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import rf.policyadmin.repository.pojo.TQuotation;

import java.util.List;

public interface QuotationDao extends JpaRepository<TQuotation, String> {

    Long count(Specification<TQuotation> specification);

    Page<TQuotation> findAll(Specification<TQuotation> specification, Pageable pageable);

    List<TQuotation> findAll(Specification<TQuotation> specification);
}
