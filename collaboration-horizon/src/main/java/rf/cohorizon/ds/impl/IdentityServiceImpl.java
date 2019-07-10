package rf.cohorizon.ds.impl;

import org.springframework.stereotype.Service;
import rf.cohorizon.ds.IdentityService;

/**
 * @ClassName IdentityServiceImpl
 * @Description: TODO
 * @Author zhouzheng
 * @Date 2019/7/10
 * @Version V1.0
 **/
@Service
public class IdentityServiceImpl implements IdentityService {

    @Override
    public String getToken() {
        return null;
    }

    @Override
    public void register(String org, String pubKey) {

    }

    @Override
    public String getPubKey(String org) {
        return null;
    }
}
