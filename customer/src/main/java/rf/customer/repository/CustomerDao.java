package rf.customer.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import rf.customer.repository.pojo.TCustormer;

import java.util.List;


public interface CustomerDao  extends JpaRepository<TCustormer, String> {

    @Query("SELECT m FROM #{#entityName} m WHERE m.idType=:idType and idNumber=:idNumber")
    TCustormer findByIdNumber(@Param("idType") String idType, @Param("idNumber") String idNumber);

    @Query("SELECT m FROM #{#entityName} m WHERE m.code=:code")
    TCustormer loadByCode(@Param("code") String code);

    Long count(Specification<TCustormer> specification);

    Page<TCustormer> findAll(Specification<TCustormer> specification, Pageable pageable);

    List<TCustormer> findAll(Specification<TCustormer> specification);
}
