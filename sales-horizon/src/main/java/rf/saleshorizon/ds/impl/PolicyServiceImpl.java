package rf.saleshorizon.ds.impl;

import org.springframework.stereotype.Service;
import rf.policyadmin.model.Policy;
import rf.saleshorizon.ds.PolicyService;

/**
 * @ClassName PolicyServiceImpl
 * @Description: TODO
 * @Author zhouzheng
 * @Date 2019/7/2
 * @Version V1.0
 **/
@Service
public class PolicyServiceImpl implements PolicyService {
    @Override
    public void pushToChain(Policy policy) {

    }

    @Override
    public Policy pullFromChain(String number) {
        return null;
    }
}
