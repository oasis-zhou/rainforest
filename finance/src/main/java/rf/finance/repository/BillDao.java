package rf.finance.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import rf.finance.repository.pojo.TBill;

import java.util.List;


public interface BillDao extends JpaRepository<TBill, String> {

    @Query("SELECT m FROM #{#entityName} m WHERE m.transType=:transType and refBizNumber=:refBizNumber")
    TBill findByRefNumber(@Param("transType") String transType, @Param("refBizNumber") String refBizNumber);

    Long count(Specification<TBill> specification);

    Page<TBill> findAll(Specification<TBill> specification, Pageable pageable);

    List<TBill> findAll(Specification<TBill> specification);
}
