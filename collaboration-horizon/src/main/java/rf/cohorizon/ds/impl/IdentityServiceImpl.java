package rf.cohorizon.ds.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import rf.cohorizon.contract.Collaboration;
import rf.cohorizon.contract.ContractFactory;
import rf.cohorizon.ds.IdentityService;
import rf.cohorizon.model.Registration;
import rf.foundation.exception.GenericException;
import rf.foundation.utils.JsonHelper;

/**
 * @ClassName IdentityServiceImpl
 * @Description: TODO
 * @Author zhouzheng
 * @Date 2019/7/10
 * @Version V1.0
 **/
@Service
public class IdentityServiceImpl implements IdentityService {

    @Autowired
    private ContractFactory contractFactory;

    @Override
    public String register(Registration registration) {
        String tx = null;
        try {
            Collaboration collaboration = contractFactory.loadContract();
            RemoteCall<TransactionReceipt> remoteCall = collaboration.register(registration.getOrgCode(), registration.getPubKey(), registration.getAccountAddress());

            tx = remoteCall.send().getTransactionHash();
        }catch (Exception e) {
            e.printStackTrace();
            throw new GenericException(e);
        }
        return tx;
    }

    @Override
    public String getPubKey(String orgCode) {
        String pubKey = null;
        try {
            Collaboration collaboration = contractFactory.loadContract();
            RemoteCall<String> remoteCall = collaboration.findOrgPubKey(orgCode);

            byte[] response = remoteCall.send().getBytes();
            pubKey = new String(response);
        }catch (Exception e) {
            e.printStackTrace();
            throw new GenericException(e);
        }
        return pubKey;
    }
}
