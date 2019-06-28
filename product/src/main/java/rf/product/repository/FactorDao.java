package rf.product.repository;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import rf.product.repository.pojo.TFactor;


public interface FactorDao extends CrudRepository<TFactor, String> {

    @Query("SELECT m FROM #{#entityName} m WHERE m.code=:code")
    TFactor findByCode(@Param("code") String code);
}
