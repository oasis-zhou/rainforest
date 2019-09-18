package rf.saleshorizon.ds;

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
public class InsuranceSalesProxy extends Contract {
    private static final String BINARY = "6080604052606460005561001b336001600160e01b0361002016565b610055565b604080517f636f6e74726163742e6f776e65720000000000000000000000000000000000008152905190819003600e01902055565b6108ec806100646000396000f3fe6080604052600436106100555760003560e01c80633659cfe61461005f5780635664d69c146100925780635c60da1b146101d75780637a57b1c8146102085780638da5cb5b14610232578063a6f9dae114610247575b61005d61027a565b005b34801561006b57600080fd5b5061005d6004803603602081101561008257600080fd5b50356001600160a01b0316610294565b34801561009e57600080fd5b5061005d600480360360608110156100b557600080fd5b8101906020810181356401000000008111156100d057600080fd5b8201836020820111156100e257600080fd5b8035906020019184600183028401116401000000008311171561010457600080fd5b91908080601f016020809104026020016040519081016040528093929190818152602001838380828437600092019190915250929594936020810193503591505064010000000081111561015757600080fd5b82018360208201111561016957600080fd5b8035906020019184600183028401116401000000008311171561018b57600080fd5b91908080601f016020809104026020016040519081016040528093929190818152602001838380828437600092019190915250929550505090356001600160a01b031691506102fb9050565b3480156101e357600080fd5b506101ec6104a6565b604080516001600160a01b039092168252519081900360200190f35b34801561021457600080fd5b5061005d6004803603602081101561022b57600080fd5b5035610511565b34801561023e57600080fd5b506101ec610571565b34801561025357600080fd5b5061005d6004803603602081101561026a57600080fd5b50356001600160a01b03166105d6565b610282610292565b61029261028d6106ca565b6106f7565b565b61029c61071b565b6001600160a01b0316336001600160a01b0316146102ef576040805162461bcd60e51b81526020600482015260176024820152600080516020610898833981519152604482015290519081900360640190fd5b6102f881610742565b50565b61030361071b565b6001600160a01b0316336001600160a01b031614610356576040805162461bcd60e51b81526020600482015260176024820152600080516020610898833981519152604482015290519081900360640190fd5b816001846040518082805190602001908083835b602083106103895780518252601f19909201916020918201910161036a565b51815160209384036101000a600019018019909216911617905292019485525060405193849003810190932084516103ca95919491909101925090506107d4565b50806002846040518082805190602001908083835b602083106103fe5780518252601f1990920191602091820191016103df565b51815160209384036101000a60001901801990921691161790529201948552506040805194859003820190942080546001600160a01b0319166001600160a01b039687161790559385166000818152600390955292909320805460ff1916600117905550905061046c61071b565b6001600160a01b03167f8c2f3fdaecbc84c2b1655605773e1c97e3e06afb3fc7506992085c2ad6f06e1b60405160405180910390a3505050565b60006104b061071b565b6001600160a01b0316336001600160a01b031614610503576040805162461bcd60e51b81526020600482015260176024820152600080516020610898833981519152604482015290519081900360640190fd5b61050b6106ca565b90505b90565b61051961071b565b6001600160a01b0316336001600160a01b03161461056c576040805162461bcd60e51b81526020600482015260176024820152600080516020610898833981519152604482015290519081900360640190fd5b600055565b600061057b61071b565b6001600160a01b0316336001600160a01b0316146105ce576040805162461bcd60e51b81526020600482015260176024820152600080516020610898833981519152604482015290519081900360640190fd5b61050b61071b565b6105de61071b565b6001600160a01b0316336001600160a01b031614610631576040805162461bcd60e51b81526020600482015260176024820152600080516020610898833981519152604482015290519081900360640190fd5b6001600160a01b0381166106765760405162461bcd60e51b815260040180806020018281038252602b81526020018061086d602b913960400191505060405180910390fd5b7fb532073b38c83145e3e5135377a08bf9aab55bc0fd7c1179cd4fb995d2a5159c61069f61071b565b604080516001600160a01b03928316815291841660208301528051918290030190a16102f881610782565b6040805173383937bc3c9734b6b83632b6b2b73a30ba34b7b760611b815290519081900360140190205490565b3660008037600080366000845af43d6000803e808015610716573d6000f35b3d6000fd5b604080516d31b7b73a3930b1ba1737bbb732b960911b8152905190819003600e0190205490565b61074b816107a8565b6040516001600160a01b038216907fbc7cd75a20ee27fd9adebab32041f755214dbc6bffa90cc0225b39da2e5c2d3b90600090a250565b604080516d31b7b73a3930b1ba1737bbb732b960911b8152905190819003600e01902055565b6040805173383937bc3c9734b6b83632b6b2b73a30ba34b7b760611b8152905190819003601401902055565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f1061081557805160ff1916838001178555610842565b82800160010185558215610842579182015b82811115610842578251825591602001919060010190610827565b5061084e929150610852565b5090565b61050e91905b8082111561084e576000815560010161085856fe43616e6e6f74206368616e676520746865204f776e657220746f20746865207a65726f206164647265737343616c6c65722069736e277420746865206f776e657221000000000000000000a265627a7a7230582073786f07e6c651404aa9abbaed196b122662a78fb0ee9fdbd1b54a23d799489a64736f6c634300050a0032";

    public static final String FUNC_UPGRADETO = "upgradeTo";

    public static final String FUNC_REGISTER = "register";

    public static final String FUNC_IMPLEMENTATION = "implementation";

    public static final String FUNC_SETMAXQUERYNUMBER = "setMaxQueryNumber";

    public static final String FUNC_OWNER = "owner";

    public static final String FUNC_CHANGEOWNER = "changeOwner";

    public static final Event OWNERCHANGED_EVENT = new Event("OwnerChanged", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Address>() {}));
    ;

    public static final Event REGISTERORG_EVENT = new Event("RegisterOrg", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}));
    ;

    public static final Event UPGRADED_EVENT = new Event("Upgraded", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}));
    ;

    @Deprecated
    protected InsuranceSalesProxy(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected InsuranceSalesProxy(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected InsuranceSalesProxy(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected InsuranceSalesProxy(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public RemoteCall<TransactionReceipt> upgradeTo(String newImplementation) {
        final Function function = new Function(
                FUNC_UPGRADETO, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(newImplementation)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> register(String orgCode, String pubKey, String orgAddress) {
        final Function function = new Function(
                FUNC_REGISTER, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(orgCode), 
                new org.web3j.abi.datatypes.Utf8String(pubKey), 
                new org.web3j.abi.datatypes.Address(orgAddress)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<String> implementation() {
        final Function function = new Function(FUNC_IMPLEMENTATION, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<TransactionReceipt> setMaxQueryNumber(BigInteger number) {
        final Function function = new Function(
                FUNC_SETMAXQUERYNUMBER, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(number)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<String> owner() {
        final Function function = new Function(FUNC_OWNER, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<TransactionReceipt> changeOwner(String newOwner) {
        final Function function = new Function(
                FUNC_CHANGEOWNER, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(newOwner)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public List<OwnerChangedEventResponse> getOwnerChangedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(OWNERCHANGED_EVENT, transactionReceipt);
        ArrayList<OwnerChangedEventResponse> responses = new ArrayList<OwnerChangedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            OwnerChangedEventResponse typedResponse = new OwnerChangedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.previousOwner = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.newOwner = (String) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<OwnerChangedEventResponse> ownerChangedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new io.reactivex.functions.Function<Log, OwnerChangedEventResponse>() {
            @Override
            public OwnerChangedEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(OWNERCHANGED_EVENT, log);
                OwnerChangedEventResponse typedResponse = new OwnerChangedEventResponse();
                typedResponse.log = log;
                typedResponse.previousOwner = (String) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.newOwner = (String) eventValues.getNonIndexedValues().get(1).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<OwnerChangedEventResponse> ownerChangedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(OWNERCHANGED_EVENT));
        return ownerChangedEventFlowable(filter);
    }

    public List<RegisterOrgEventResponse> getRegisterOrgEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(REGISTERORG_EVENT, transactionReceipt);
        ArrayList<RegisterOrgEventResponse> responses = new ArrayList<RegisterOrgEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            RegisterOrgEventResponse typedResponse = new RegisterOrgEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.owner = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.orgAddress = (String) eventValues.getIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<RegisterOrgEventResponse> registerOrgEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new io.reactivex.functions.Function<Log, RegisterOrgEventResponse>() {
            @Override
            public RegisterOrgEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(REGISTERORG_EVENT, log);
                RegisterOrgEventResponse typedResponse = new RegisterOrgEventResponse();
                typedResponse.log = log;
                typedResponse.owner = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.orgAddress = (String) eventValues.getIndexedValues().get(1).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<RegisterOrgEventResponse> registerOrgEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(REGISTERORG_EVENT));
        return registerOrgEventFlowable(filter);
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
    public static InsuranceSalesProxy load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new InsuranceSalesProxy(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static InsuranceSalesProxy load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new InsuranceSalesProxy(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static InsuranceSalesProxy load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new InsuranceSalesProxy(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static InsuranceSalesProxy load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new InsuranceSalesProxy(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<InsuranceSalesProxy> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(InsuranceSalesProxy.class, web3j, credentials, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<InsuranceSalesProxy> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(InsuranceSalesProxy.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    public static RemoteCall<InsuranceSalesProxy> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(InsuranceSalesProxy.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<InsuranceSalesProxy> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(InsuranceSalesProxy.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }

    public static class OwnerChangedEventResponse {
        public Log log;

        public String previousOwner;

        public String newOwner;
    }

    public static class RegisterOrgEventResponse {
        public Log log;

        public String owner;

        public String orgAddress;
    }

    public static class UpgradedEventResponse {
        public Log log;

        public String implementation;
    }
}
