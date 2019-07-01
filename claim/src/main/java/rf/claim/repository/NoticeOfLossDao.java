package rf.claim.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import rf.claim.repository.pojo.TNoticeOfLoss;

import java.util.List;

/**
 * Created by zhouzheng on 2017/5/1.
 */
public interface NoticeOfLossDao extends CrudRepository<TNoticeOfLoss, String> {

    @Query("SELECT m FROM #{#entityName} m WHERE m.noticeNumber=:noticeNumber")
    TNoticeOfLoss findByNoticeNumber(@Param("noticeNumber") String noticeNumber);

    @Query("SELECT m FROM #{#entityName} m WHERE m.policyNumber=:policyNumber")
    List<TNoticeOfLoss> findByPolicyNumber(@Param("policyNumber") String policyNumber);
}
