package rf.saleshorizon.ds.impl;

import org.springframework.stereotype.Service;
import rf.claim.model.NoticeOfLoss;
import rf.saleshorizon.ds.NoticeOfLossService;

/**
 * @ClassName NoticeOfLossServiceImpl
 * @Description: TODO
 * @Author zhouzheng
 * @Date 2019/7/4
 * @Version V1.0
 **/
@Service
public class NoticeOfLossServiceImpl implements NoticeOfLossService {
    @Override
    public void pushToChain(NoticeOfLoss noticeOfLoss) {

    }

    @Override
    public NoticeOfLoss pullFromChain(String noticeNumber) {
        return null;
    }
}
