package rf.claim.ds;

import org.springframework.data.domain.Pageable;
import rf.claim.model.ClaimMaterials;
import rf.claim.model.NoticeOfLoss;
import rf.claim.model.NoticeOfLossIndex;
import rf.claim.model.NoticeOfLossQueryCondition;

import java.util.Date;
import java.util.List;

/**
 * Created by zhouzheng on 2017/5/1.
 *
 */
public interface NoticeOfLossService {

     NoticeOfLoss createNoticeOfLoss(NoticeOfLoss notice);

     NoticeOfLoss loadNoticeOfLoss(String noticeNumber);

     void upload(ClaimMaterials materials);

     void saveNotice(NoticeOfLoss notice);

     List<NoticeOfLossIndex> queryNoticeOfLoss(NoticeOfLossQueryCondition condition);

     void generateNoticeOfLossIndex(NoticeOfLoss notice);

}
