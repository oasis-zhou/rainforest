package rf.inshorizon.ds.impl;

import org.springframework.stereotype.Service;
import rf.inshorizon.ds.EndorsementService;
import rf.policyadmin.model.Endorsement;

/**
 * @ClassName EndorsementServiceImpl
 * @Description: TODO
 * @Author zhouzheng
 * @Date 2019/7/2
 * @Version V1.0
 **/
@Service
public class EndorsementServiceImpl implements EndorsementService {

    @Override
    public void pushToChain(Endorsement endorsement) {

    }

    @Override
    public Endorsement pullFromChain(String endorsementNumber) {
        return null;
    }
}
