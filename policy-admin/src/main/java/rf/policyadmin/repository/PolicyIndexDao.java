package rf.policyadmin.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import rf.policyadmin.repository.pojo.TPolicyIndex;

import java.util.List;

/**
 * Created by zhouzheng on 2017/5/3.
 */
public interface PolicyIndexDao extends JpaRepository<TPolicyIndex, String> {

    Long count(Specification<TPolicyIndex> specification);

    Page<TPolicyIndex> findAll(Specification<TPolicyIndex> specification, Pageable pageable);

    List<TPolicyIndex> findAll(Specification<TPolicyIndex> specification);
}
