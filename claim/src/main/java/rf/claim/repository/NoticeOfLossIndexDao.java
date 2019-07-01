package rf.claim.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import rf.claim.repository.pojo.TNoticeOfLossIndex;

/**
 * @author LiRui
 * @Date 2018/1/3
 */
public interface NoticeOfLossIndexDao extends CrudRepository<TNoticeOfLossIndex, String> {

    @Query("SELECT m FROM #{#entityName} m WHERE m.noticeNumber=:noticeNumber")
    TNoticeOfLossIndex findByNoticeNumber(@Param("noticeNumber") String noticeNumber);

    Page<TNoticeOfLossIndex> findAll(Specification<TNoticeOfLossIndex> specification, Pageable pageable);
}