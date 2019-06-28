package rf.customer.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import rf.customer.repository.pojo.TCustormer;


public interface CustomerDao  extends CrudRepository<TCustormer, String> {

    @Query("SELECT m FROM #{#entityName} m WHERE m.idType=:idType and idNumber=:idNumber")
    TCustormer findByIdNumber(@Param("idType") String idType, @Param("idNumber") String idNumber);
}
