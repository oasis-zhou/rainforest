package rf.collaboration.ds;

import io.reactivex.Flowable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 4.3.0.
 */
public class OwnedUpgradeabilityProxy extends Contract {
    private static final String BINARY = "608060405234801561001057600080fd5b50610023336001600160e01b0361002816565b61005d565b604080517f636f6d2e736869652e70726f78792e6f776e65720000000000000000000000008152905190819003601401902055565b6104d68061006c6000396000f3fe60806040526004361061003f5760003560e01c8063025313a2146100b55780633659cfe6146100e65780635c60da1b1461011b578063f1739cae14610130575b6000610049610163565b90506001600160a01b0381166100905760405162461bcd60e51b81526004018080602001828103825260218152602001806104816021913960400191505060405180910390fd5b60405136600082376000803683855af43d806000843e8180156100b1578184f35b8184fd5b3480156100c157600080fd5b506100ca610199565b604080516001600160a01b039092168252519081900360200190f35b3480156100f257600080fd5b506101196004803603602081101561010957600080fd5b50356001600160a01b03166101c6565b005b34801561012757600080fd5b506100ca610163565b34801561013c57600080fd5b506101196004803603602081101561015357600080fd5b50356001600160a01b0316610239565b604080517f636f6d2e736869652e70726f78792e696d706c656d656e746174696f6e0000008152905190819003601d0190205490565b604080517331b7b69739b434b297383937bc3c9737bbb732b960611b815290519081900360140190205490565b6101ce610199565b6001600160a01b0316336001600160a01b03161461022d576040805162461bcd60e51b815260206004820152601760248201527643616c6c65722069736e277420746865206f776e65722160481b604482015290519081900360640190fd5b6102368161034f565b50565b610241610199565b6001600160a01b0316336001600160a01b0316146102a0576040805162461bcd60e51b815260206004820152601760248201527643616c6c65722069736e277420746865206f776e65722160481b604482015290519081900360640190fd5b6001600160a01b0381166102fb576040805162461bcd60e51b815260206004820152601860248201527f4e6577206f776e65722063616e2774206265206e756c6c210000000000000000604482015290519081900360640190fd5b7f5a3e66efaa1e445ebd894728a69d6959842ea1e97bd79b892797106e270efcd9610324610199565b604080516001600160a01b03928316815291841660208301528051918290030190a1610236816103ed565b6000610359610163565b9050816001600160a01b0316816001600160a01b031614156103ac5760405162461bcd60e51b815260040180806020018281038252603281526020018061044f6032913960400191505060405180910390fd5b6103b582610419565b6040516001600160a01b038316907fbc7cd75a20ee27fd9adebab32041f755214dbc6bffa90cc0225b39da2e5c2d3b90600090a25050565b604080517331b7b69739b434b297383937bc3c9737bbb732b960611b8152905190819003601401902055565b604080517f636f6d2e736869652e70726f78792e696d706c656d656e746174696f6e0000008152905190819003601d0190205556fe546865206e657720696d706c656d656e746174696f6e2063616e27742062652073616d652077697468206f6c64206f6e652154686520696d706c656d656e746174696f6e2063616e2774206265206e756c6c21a265627a7a723058201fb47d9cb4fd62de24e165c12d00ca58f5f49305675c94e4e799bbb05dcc6f7964736f6c634300050a0032";

    public static final String FUNC_PROXYOWNER = "proxyOwner";

    public static final String FUNC_UPGRADETO = "upgradeTo";

    public static final String FUNC_IMPLEMENTATION = "implementation";

    public static final String FUNC_TRANSFERPROXYOWNERSHIP = "transferProxyOwnership";

    public static final Event PROXYOWNERSHIPTRANSFERRED_EVENT = new Event("ProxyOwnershipTransferred", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Address>() {}));
    ;

    public static final Event UPGRADED_EVENT = new Event("Upgraded", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}));
    ;

    @Deprecated
    protected OwnedUpgradeabilityProxy(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected OwnedUpgradeabilityProxy(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected OwnedUpgradeabilityProxy(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected OwnedUpgradeabilityProxy(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public RemoteCall<String> proxyOwner() {
        final Function function = new Function(FUNC_PROXYOWNER, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<TransactionReceipt> upgradeTo(String implementation) {
        final Function function = new Function(
                FUNC_UPGRADETO, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(implementation)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<String> implementation() {
        final Function function = new Function(FUNC_IMPLEMENTATION, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<TransactionReceipt> transferProxyOwnership(String newOwner) {
        final Function function = new Function(
                FUNC_TRANSFERPROXYOWNERSHIP, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(newOwner)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public List<ProxyOwnershipTransferredEventResponse> getProxyOwnershipTransferredEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(PROXYOWNERSHIPTRANSFERRED_EVENT, transactionReceipt);
        ArrayList<ProxyOwnershipTransferredEventResponse> responses = new ArrayList<ProxyOwnershipTransferredEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            ProxyOwnershipTransferredEventResponse typedResponse = new ProxyOwnershipTransferredEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.previousOwner = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.newOwner = (String) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<ProxyOwnershipTransferredEventResponse> proxyOwnershipTransferredEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new io.reactivex.functions.Function<Log, ProxyOwnershipTransferredEventResponse>() {
            @Override
            public ProxyOwnershipTransferredEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(PROXYOWNERSHIPTRANSFERRED_EVENT, log);
                ProxyOwnershipTransferredEventResponse typedResponse = new ProxyOwnershipTransferredEventResponse();
                typedResponse.log = log;
                typedResponse.previousOwner = (String) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.newOwner = (String) eventValues.getNonIndexedValues().get(1).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<ProxyOwnershipTransferredEventResponse> proxyOwnershipTransferredEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(PROXYOWNERSHIPTRANSFERRED_EVENT));
        return proxyOwnershipTransferredEventFlowable(filter);
    }

    public List<UpgradedEventResponse> getUpgradedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(UPGRADED_EVENT, transactionReceipt);
        ArrayList<UpgradedEventResponse> responses = new ArrayList<UpgradedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            UpgradedEventResponse typedResponse = new UpgradedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.implementation = (String) eventValues.getIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<UpgradedEventResponse> upgradedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new io.reactivex.functions.Function<Log, UpgradedEventResponse>() {
            @Override
            public UpgradedEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(UPGRADED_EVENT, log);
                UpgradedEventResponse typedResponse = new UpgradedEventResponse();
                typedResponse.log = log;
                typedResponse.implementation = (String) eventValues.getIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<UpgradedEventResponse> upgradedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(UPGRADED_EVENT));
        return upgradedEventFlowable(filter);
    }

    @Deprecated
    public static OwnedUpgradeabilityProxy load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new OwnedUpgradeabilityProxy(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static OwnedUpgradeabilityProxy load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new OwnedUpgradeabilityProxy(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static OwnedUpgradeabilityProxy load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new OwnedUpgradeabilityProxy(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static OwnedUpgradeabilityProxy load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new OwnedUpgradeabilityProxy(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<OwnedUpgradeabilityProxy> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(OwnedUpgradeabilityProxy.class, web3j, credentials, contractGasProvider, BINARY, "");
    }

    public static RemoteCall<OwnedUpgradeabilityProxy> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(OwnedUpgradeabilityProxy.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<OwnedUpgradeabilityProxy> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(OwnedUpgradeabilityProxy.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<OwnedUpgradeabilityProxy> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(OwnedUpgradeabilityProxy.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }

    public static class ProxyOwnershipTransferredEventResponse {
        public Log log;

        public String previousOwner;

        public String newOwner;
    }

    public static class UpgradedEventResponse {
        public Log log;

        public String implementation;
    }
}
