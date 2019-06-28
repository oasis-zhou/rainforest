package rf.finance.repository;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import rf.finance.repository.pojo.TArapItem;


public interface ArapItemDao extends CrudRepository<TArapItem, String> {

    @Query("SELECT m FROM #{#entityName} m WHERE m.transType=:transType and refBizNumber=:refBizNumber")
    TArapItem findByRefNumber(@Param("transType") String transType, @Param("refBizNumber") String refBizNumber);
}
