package rf.bizop.ds.impl;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import rf.bizop.contract.ContractFactory;
import rf.bizop.contract.InsuranceSales;
import rf.bizop.ds.BlockChainService;
import rf.bizop.model.Registration;
import rf.foundation.cache.GuavaCacheManager;
import rf.foundation.exception.GenericException;
import rf.foundation.utils.JsonHelper;
import rf.policyadmin.model.Endorsement;
import rf.policyadmin.model.Policy;
import rf.product.model.ProductSpec;
import rf.rating.dt.DecisionTableSpec;

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
    public String releaseProduct(ProductSpec productSpec) {
        String tx = null;
        try {
            InsuranceSales insuranceSales = contractFactory.loadContractWithProxy();
            RemoteCall<TransactionReceipt> remoteCall = insuranceSales.releaseProduct(productSpec.getCode(),jsonHelper.toJSON(productSpec));

            tx = remoteCall.send().getTransactionHash();
        } catch (Exception e) {
            e.printStackTrace();
            throw new GenericException(e);
        }
        return tx;
    }

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

    @Override
    public List<Policy> findPendingPolicies() {
        List<Policy> policies = Lists.newArrayList();
        try {
            InsuranceSales insuranceSales = contractFactory.loadContractWithProxy();
            RemoteCall<String> remoteCall = insuranceSales.findPendingPolicies();
            byte[] response = remoteCall.send().getBytes();
            String policyNumberStr = new String(response);

            String[] policyNumbers = policyNumberStr.split(",");
            for (String policyNumber : policyNumbers) {
                Policy policy = findPolicy(policyNumber);
                policies.add(policy);
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new GenericException(e);
        }
        return policies;
    }

    @Override
    public List<Endorsement> findPendingEndorsements() {
        List<Endorsement> endorsements = Lists.newArrayList();
        try {
            InsuranceSales insuranceSales = contractFactory.loadContractWithProxy();
            RemoteCall<String> remoteCall = insuranceSales.findPendingEndorsements();
            byte[] response = remoteCall.send().getBytes();
            String endorsementNumberStr = new String(response);

            String[] endorsementNumbers = endorsementNumberStr.split(",");
            for (String endorsementNumber : endorsementNumbers) {
                Endorsement endorsement = findEndorsement(endorsementNumber);
                endorsements.add(endorsement);
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new GenericException(e);
        }
        return endorsements;
    }

    @Override
    public String withdrawPendingPolicy(String policyNumber) {
        String tx = null;
        try {
            InsuranceSales insuranceSales = contractFactory.loadContractWithProxy();
            RemoteCall<TransactionReceipt> remoteCall = insuranceSales.withdrawPendingPolicy(policyNumber);

            tx = remoteCall.send().getTransactionHash();
        } catch (Exception e) {
            e.printStackTrace();
            throw new GenericException(e);
        }
        return tx;
    }

    @Override
    public String withdrawPendingEndorsement(String endorsementNumber) {
        String tx = null;
        try {
            InsuranceSales insuranceSales = contractFactory.loadContractWithProxy();
            RemoteCall<TransactionReceipt> remoteCall = insuranceSales.withdrawPendingEndorsement(endorsementNumber);

            tx = remoteCall.send().getTransactionHash();
        } catch (Exception e) {
            e.printStackTrace();
            throw new GenericException(e);
        }
        return tx;
    }

    @Override
    public String register(Registration registration) {
        String tx = null;
        try {
            InsuranceSales insuranceSales = contractFactory.loadContractWithProxy();
            RemoteCall<TransactionReceipt> remoteCall = insuranceSales.register(registration.getOrgCode(),registration.getPubKey(),registration.getAccountAddress());

            tx = remoteCall.send().getTransactionHash();
        } catch (Exception e) {
            e.printStackTrace();
            throw new GenericException(e);
        }
        return tx;
    }
}
