package rf.saleshorizon.contract;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.web3j.crypto.*;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.gas.DefaultGasProvider;
import rf.foundation.context.AppContext;
import rf.foundation.exception.GenericException;

/**
 * Created by admin on 2018/9/6.
 */
@Component
public class ContractFactory {

    @Value("${contract.address}")
    private String contractAddress;

    @Value("${node.url}")
    private String nodeUrl;

    private Web3j web3j;

    public final static String DEFAULT_PASSWORD = "1qaz2wsx";

    private static final ObjectMapper objectMapper = new ObjectMapper();

    private Logger logger = LoggerFactory.getLogger(getClass());

    public RemoteCall<InsuranceSales> deployContract() {
        return InsuranceSales.deploy(getWweb3(),loadCredentials(),new DefaultGasProvider());
    }

    public InsuranceSales loadContract() {
        return InsuranceSales.load(contractAddress, getWweb3(), loadCredentials(), new DefaultGasProvider());
    }

    public InsuranceSales loadContract(String address, String password, String keystore) {
        return InsuranceSales.load(address, getWweb3(), loadCredentials(password, keystore), new DefaultGasProvider());
    }

    public Credentials loadCredentials(String password, String keystore) {
        try {
            WalletFile walletFile = objectMapper.readValue(keystore, WalletFile.class);
            Credentials credentials = Credentials.create(Wallet.decrypt(password, walletFile));
            return credentials;
        } catch (Exception e) {
            throw new GenericException(e);
        }

    }

    public Web3j getWweb3() {
        if (web3j == null) {
            synchronized (this) {
                if (web3j == null) {
                    web3j = Web3j.build(new HttpService(nodeUrl));
                    return web3j;
                }
            }
        }
        return web3j;
    }

    private Credentials loadCredentials() {
        try {
            String keystorePath = "classpath:keystore.json";
            Resource resource = AppContext.getApplicationContext().getResource(keystorePath);

            Credentials credentials = WalletUtils.loadCredentials(DEFAULT_PASSWORD, resource.getFile());

            return credentials;

        } catch (Exception e) {
            throw new GenericException(e);
        }
    }

    public String generateKeystore(){
        ECKeyPair ecKeyPair = null;
        String keystore = null;
        try {
            ecKeyPair = Keys.createEcKeyPair();
            WalletFile walletFile = Wallet.createStandard(DEFAULT_PASSWORD, ecKeyPair);
            keystore = JSON.toJSONString(walletFile);

        } catch (Exception e) {
            throw new GenericException(e);
        }
        return keystore;
    }
}