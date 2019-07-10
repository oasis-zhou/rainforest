package rf.saleshorizon.ds;

import rf.claim.model.NoticeOfLoss;

public interface NoticeOfLossService {

    void pushToChain(NoticeOfLoss noticeOfLoss);
    NoticeOfLoss pullFromChain(String noticeNumber);
}
