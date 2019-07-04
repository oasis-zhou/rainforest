package rf.claim.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import rf.claim.repository.pojo.TNoticeOfLoss;

import java.util.List;

/**
 * Created by zhouzheng on 2017/5/1.
 */
public interface NoticeOfLossDao extends JpaRepository<TNoticeOfLoss, String> {

    @Query("SELECT m FROM #{#entityName} m WHERE m.noticeNumber=:noticeNumber")
    TNoticeOfLoss findByNoticeNumber(@Param("noticeNumber") String noticeNumber);

    @Query("SELECT m FROM #{#entityName} m WHERE m.policyNumber=:policyNumber")
    List<TNoticeOfLoss> findByPolicyNumber(@Param("policyNumber") String policyNumber);

    Long count(Specification<TNoticeOfLoss> specification);

    Page<TNoticeOfLoss> findAll(Specification<TNoticeOfLoss> specification, Pageable pageable);

    List<TNoticeOfLoss> findAll(Specification<TNoticeOfLoss> specification);
}
