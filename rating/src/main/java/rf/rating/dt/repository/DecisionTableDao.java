package rf.rating.dt.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import rf.rating.dt.repository.pojo.TDecisionTable;


public interface DecisionTableDao extends CrudRepository<TDecisionTable, String> {

    @Query("SELECT m FROM #{#entityName} m WHERE m.code=:code")
    TDecisionTable findByCode(@Param("code") String code);
}
