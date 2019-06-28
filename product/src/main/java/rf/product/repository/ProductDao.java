package rf.product.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import rf.product.repository.pojo.TProduct;


public interface ProductDao extends CrudRepository<TProduct, String> {

    @Query("SELECT m FROM #{#entityName} m WHERE m.code=:code")
    TProduct findByCode(@Param("code") String code);
}
