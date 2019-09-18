package rf.saleshorizon.ds.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import rf.foundation.cache.GuavaCacheManager;
import rf.foundation.exception.GenericException;
import rf.foundation.utils.JsonHelper;
import rf.policyadmin.model.Endorsement;
import rf.policyadmin.model.Policy;
import rf.product.model.ProductSpec;
import rf.rating.dt.DecisionTableSpec;
import rf.saleshorizon.contract.ContractFactory;
import rf.saleshorizon.contract.InsuranceSales;
import rf.saleshorizon.ds.BlockChainService;

import java.util.List;

@Service
public class BlockChainServiceImpl implements BlockChainService {

    @Autowired
    private GuavaCacheManager cacheManager;
    @Autowired
    private ContractFactory contractFactory;
    @Autowired
    private JsonHelper jsonHelper;

    @Override
    public ProductSpec findProduct(String productCode) {
        ProductSpec productSpec = (ProductSpec) cacheManager.getCacheByKey(productCode);

        //从区块链获取产品信息，缓存产品和费率表数据
        if(productSpec == null) {
            try {
                InsuranceSales insuranceSales = contractFactory.loadContractWithProxy();
                RemoteCall<String> remoteCall = insuranceSales.findProduct(productCode);

                byte[] response = remoteCall.send().getBytes();
                String productSpecStr = new String(response);

                productSpec = jsonHelper.fromJSON(productSpecStr,ProductSpec.class);

                cacheManager.putCache(productCode,productSpec);

                List<DecisionTableSpec> decisionTableSpecs = productSpec.getAllSubComponentsByType(DecisionTableSpec.class);
                for (DecisionTableSpec spec : decisionTableSpecs) {
                    cacheManager.putCache(spec.getCode(),spec);
                }
            } catch (Exception e) {
                e.printStackTrace();
                throw new GenericException(e);
            }
        }
        return productSpec;
    }

    @Override
    public String issuePolicy(Policy policy) {
        String tx = null;
        try {
            InsuranceSales insuranceSales = contractFactory.loadContractWithProxy();
            RemoteCall<TransactionReceipt> remoteCall = insuranceSales.issuePolicy(policy.getPolicyNumber(), policy.getProductCode(), jsonHelper.toJSON(policy));

            tx = remoteCall.send().getTransactionHash();
        } catch (Exception e) {
            e.printStackTrace();
            throw new GenericException(e);
        }
        return tx;
    }

    @Override
    public String issueEndorsement(Endorsement endorsement) {
        String tx = null;
        try {
            InsuranceSales insuranceSales = contractFactory.loadContractWithProxy();
            RemoteCall<TransactionReceipt> remoteCall = insuranceSales.issueEndorsement(endorsement.getEndorsementNumber(), endorsement.getProductCode(), jsonHelper.toJSON(endorsement));

            tx = remoteCall.send().getTransactionHash();
        } catch (Exception e) {
            e.printStackTrace();
            throw new GenericException(e);
        }
        return tx;
    }

    @Override
    public Policy findPolicy(String policyNumber) {
        Policy policy = null;
        try {
            InsuranceSales insuranceSales = contractFactory.loadContractWithProxy();
            RemoteCall<String> remoteCall = insuranceSales.findPolicy(policyNumber);
            byte[] response = remoteCall.send().getBytes();
            String policyStr = new String(response);

            policy = jsonHelper.fromJSON(policyStr, Policy.class);
        } catch (Exception e) {
            e.printStackTrace();
            throw new GenericException(e);
        }
        return policy;
    }

    @Override
    public Endorsement findEndorsement(String endorsementNumber) {
        Endorsement endorsement = null;
        try {
            InsuranceSales insuranceSales = contractFactory.loadContractWithProxy();
            RemoteCall<String> remoteCall = insuranceSales.findEndorsement(endorsementNumber);
            byte[] response = remoteCall.send().getBytes();
            String endorsementStr = new String(response);

            endorsement = jsonHelper.fromJSON(endorsementStr, Endorsement.class);
        } catch (Exception e) {
            e.printStackTrace();
            throw new GenericException(e);
        }
        return endorsement;
    }

}
