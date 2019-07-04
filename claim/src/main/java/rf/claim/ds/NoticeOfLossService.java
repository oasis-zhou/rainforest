package rf.claim.ds;

import rf.claim.model.ClaimMaterials;
import rf.claim.model.NoticeOfLoss;
import rf.claim.model.QueryCondition;
import rf.foundation.model.ResponsePage;

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

     ResponsePage<NoticeOfLoss> queryNoticeOfLoss(QueryCondition condition);

}
