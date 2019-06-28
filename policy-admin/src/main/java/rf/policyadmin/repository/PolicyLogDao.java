package rf.policyadmin.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import rf.policyadmin.repository.pojo.TPolicyLog;


public interface PolicyLogDao extends CrudRepository<TPolicyLog, String> {

    @Query("SELECT m FROM #{#entityName} m WHERE m.endoId=:endoId and m.logType=:logType")
    TPolicyLog findByEndoInfo(@Param("endoId") String endoId, @Param("logType") String logType);
}
