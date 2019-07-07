package rf.claim.ds;

import rf.claim.model.NoticeOfLoss;

public interface ClaimNumberService {

    String generateNoticeNumber(NoticeOfLoss noticeOfLoss);
}
