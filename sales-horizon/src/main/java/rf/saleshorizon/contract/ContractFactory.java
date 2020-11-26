package rf.saleshorizon.contract;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.web3j.crypto.*;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.RawTransactionManager;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.DefaultGasProvider;
import org.web3j.tx.gas.StaticGasProvider;
import rf.foundation.context.AppContext;
import rf.foundation.exception.GenericException;
import rf.foundation.utils.JsonHelper;

import java.math.BigInteger;

/**
 * Created by admin on 2018/9/6.
 */
@Component
public class ContractFactory {

    private static final int DEFAULT_POLLING_ATTEMPTS_PER_TX_HASH = 60;
    private static final int DEFAULT_POLLING_FREQUENCY = 300;
    private static final BigInteger GAS_LIMIT = BigInteger.valueOf(1_000_000_000L);
    private static final BigInteger GAS_PRICE = BigInteger.valueOf(1_000_000_000L);

    @Value("${contract.address}")
    private String contractAddress;
    @Value("${proxy.address}")
    private String proxyAddress;
    @Value("${node.url}")
    private String nodeUrl;
    @Value("${account.keystore.password}")
    private String password;
    @Value("${account.keystore}")
    private String keystore;
    @Autowired
    private JsonHelper jsonHelper;

    private static final StaticGasProvider gasProvider = new StaticGasProvider(GAS_PRICE,GAS_LIMIT);

    private Web3j web3j;

    private InsuranceSales insuranceSales;

    private InsuranceSalesProxy proxy;

    private InsuranceSales insuranceSalesProxy;

    private static final ObjectMapper objectMapper = new ObjectMapper();

    private Logger logger = LoggerFactory.getLogger(getClass());

    public RemoteCall<InsuranceSales> deployContract() {
        return InsuranceSales.deploy(getWweb3(),loadCredentials(password,keystore),gasProvider);
    }

    public RemoteCall<InsuranceSalesProxy> deployProxy() {
        return InsuranceSalesProxy.deploy(getWweb3(),loadCredentials(password,keystore),gasProvider);
    }

    public InsuranceSales loadContract() {
        if(insuranceSales == null) {
            Credentials credentials = loadCredentials(password,keystore);
            TransactionManager rawTransactionManager = new RawTransactionManager(getWweb3(), credentials, DEFAULT_POLLING_ATTEMPTS_PER_TX_HASH, DEFAULT_POLLING_FREQUENCY);
            insuranceSales = InsuranceSales.load(contractAddress, getWweb3(), rawTransactionManager, gasProvider);
        }
        return insuranceSales;
    }

    public InsuranceSales loadContractWithProxy() {
        if(insuranceSalesProxy == null) {
            Credentials credentials = loadCredentials(password,keystore);
            TransactionManager rawTransactionManager = new RawTransactionManager(getWweb3(), credentials, DEFAULT_POLLING_ATTEMPTS_PER_TX_HASH, DEFAULT_POLLING_FREQUENCY);
            insuranceSalesProxy = InsuranceSales.load(proxyAddress, getWweb3(), rawTransactionManager, gasProvider);
        }
        return insuranceSalesProxy;
    }

    public InsuranceSalesProxy loadProxy() {
        if(proxy == null) {
            Credentials credentials = loadCredentials(password,keystore);
            TransactionManager rawTransactionManager = new RawTransactionManager(getWweb3(), credentials, DEFAULT_POLLING_ATTEMPTS_PER_TX_HASH, DEFAULT_POLLING_FREQUENCY);
            proxy = InsuranceSalesProxy.load(proxyAddress, getWweb3(), rawTransactionManager, gasProvider);
        }
        return proxy;
    }

    public InsuranceSales loadContract(String address, String password, String keystore) {
        return InsuranceSales.load(address, getWweb3(), loadCredentials(password, keystore), gasProvider);
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
            Credentials credentials = WalletUtils.loadCredentials(password, resource.getFile());

            return credentials;
        } catch (Exception e) {
            throw new GenericException(e);
        }
    }

    public String generateKeystore(String password){
        String keystore = null;
        try {
            ECKeyPair ecKeyPair = Keys.createEcKeyPair();
            WalletFile walletFile = Wallet.createStandard(password, ecKeyPair);
            keystore = jsonHelper.toJSON(walletFile);

        } catch (Exception e) {
            throw new GenericException(e);
        }
        return keystore;
    }
}
