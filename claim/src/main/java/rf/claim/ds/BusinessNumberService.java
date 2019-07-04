package rf.claim.ds;

import rf.claim.model.NoticeOfLoss;

public interface BusinessNumberService {

    String generateNoticeNumber(NoticeOfLoss noticeOfLoss);
}
