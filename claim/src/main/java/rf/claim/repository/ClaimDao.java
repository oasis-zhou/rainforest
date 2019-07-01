package rf.claim.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import rf.claim.repository.pojo.TClaim;

import java.util.List;

/**
 * Created by zhouzheng on 2017/5/1.
 * @ Date 2017-08-22 update LiRui 增加新的查询条件
 */
public interface ClaimDao extends CrudRepository<TClaim, String> {

    @Query("SELECT m FROM #{#entityName} m WHERE m.claimNumber=:claimNumber")
    TClaim findByClaimNumber(@Param("claimNumber") String claimNumber);

    @Query("SELECT m FROM #{#entityName} m WHERE m.policyNumber=:policyNumber")
    List<TClaim> findByPolicyNumber(@Param("policyNumber") String policyNumber);

}
