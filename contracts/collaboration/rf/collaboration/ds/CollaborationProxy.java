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
public class CollaborationProxy extends Contract {
    private static final String BINARY = "6080604052606460005561001b336001600160e01b0361002016565b610055565b604080517f636f6d2e736869652e6f776e65720000000000000000000000000000000000008152905190819003600e01902055565b6108bf806100646000396000f3fe6080604052600436106100555760003560e01c80633659cfe61461005f5780635664d69c146100925780635c60da1b146101d75780638da5cb5b14610208578063988a23a01461021d578063a6f9dae114610247575b61005d61027a565b005b34801561006b57600080fd5b5061005d6004803603602081101561008257600080fd5b50356001600160a01b0316610294565b34801561009e57600080fd5b5061005d600480360360608110156100b557600080fd5b8101906020810181356401000000008111156100d057600080fd5b8201836020820111156100e257600080fd5b8035906020019184600183028401116401000000008311171561010457600080fd5b91908080601f016020809104026020016040519081016040528093929190818152602001838380828437600092019190915250929594936020810193503591505064010000000081111561015757600080fd5b82018360208201111561016957600080fd5b8035906020019184600183028401116401000000008311171561018b57600080fd5b91908080601f016020809104026020016040519081016040528093929190818152602001838380828437600092019190915250929550505090356001600160a01b031691506102fb9050565b3480156101e357600080fd5b506101ec610467565b604080516001600160a01b039092168252519081900360200190f35b34801561021457600080fd5b506101ec6104d2565b34801561022957600080fd5b5061005d6004803603602081101561024057600080fd5b5035610537565b34801561025357600080fd5b5061005d6004803603602081101561026a57600080fd5b50356001600160a01b0316610597565b610282610292565b61029261028d61068b565b6106c1565b565b61029c6106e5565b6001600160a01b0316336001600160a01b0316146102ef576040805162461bcd60e51b8152602060048201526017602482015260008051602061086b833981519152604482015290519081900360640190fd5b6102f88161070c565b50565b6103036106e5565b6001600160a01b0316336001600160a01b031614610356576040805162461bcd60e51b8152602060048201526017602482015260008051602061086b833981519152604482015290519081900360640190fd5b816006846040518082805190602001908083835b602083106103895780518252601f19909201916020918201910161036a565b51815160209384036101000a600019018019909216911617905292019485525060405193849003810190932084516103ca95919491909101925090506107a7565b50806007846040518082805190602001908083835b602083106103fe5780518252601f1990920191602091820191016103df565b51815160209384036101000a60001901801990921691161790529201948552506040805194859003820190942080546001600160a01b0319166001600160a01b03968716179055949093166000908152600890945250909120805460ff19166001179055505050565b60006104716106e5565b6001600160a01b0316336001600160a01b0316146104c4576040805162461bcd60e51b8152602060048201526017602482015260008051602061086b833981519152604482015290519081900360640190fd5b6104cc61068b565b90505b90565b60006104dc6106e5565b6001600160a01b0316336001600160a01b03161461052f576040805162461bcd60e51b8152602060048201526017602482015260008051602061086b833981519152604482015290519081900360640190fd5b6104cc6106e5565b61053f6106e5565b6001600160a01b0316336001600160a01b031614610592576040805162461bcd60e51b8152602060048201526017602482015260008051602061086b833981519152604482015290519081900360640190fd5b600055565b61059f6106e5565b6001600160a01b0316336001600160a01b0316146105f2576040805162461bcd60e51b8152602060048201526017602482015260008051602061086b833981519152604482015290519081900360640190fd5b6001600160a01b0381166106375760405162461bcd60e51b815260040180806020018281038252602b815260200180610840602b913960400191505060405180910390fd5b7fb532073b38c83145e3e5135377a08bf9aab55bc0fd7c1179cd4fb995d2a5159c6106606106e5565b604080516001600160a01b03928316815291841660208301528051918290030190a16102f88161074c565b604080517f636f6d2e736869652e70726f78792e696d706c656d656e746174696f6e0000008152905190819003601d0190205490565b3660008037600080366000845af43d6000803e8080156106e0573d6000f35b3d6000fd5b604080516d31b7b69739b434b29737bbb732b960911b8152905190819003600e0190205490565b61071581610772565b6040516001600160a01b038216907fbc7cd75a20ee27fd9adebab32041f755214dbc6bffa90cc0225b39da2e5c2d3b90600090a250565b604080516d31b7b69739b434b29737bbb732b960911b8152905190819003600e01902055565b604080517f636f6d2e736869652e70726f78792e696d706c656d656e746174696f6e0000008152905190819003601d01902055565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f106107e857805160ff1916838001178555610815565b82800160010185558215610815579182015b828111156108155782518255916020019190600101906107fa565b50610821929150610825565b5090565b6104cf91905b80821115610821576000815560010161082b56fe43616e6e6f74206368616e676520746865204f776e657220746f20746865207a65726f206164647265737343616c6c65722069736e277420746865206f776e657221000000000000000000a265627a7a72305820f9c74ec76c44bff564c7053869ef3894ef696d3435e4f47973bdfcfa8ad277da64736f6c634300050a0032";

    public static final String FUNC_UPGRADETO = "upgradeTo";

    public static final String FUNC_REGISTER = "register";

    public static final String FUNC_IMPLEMENTATION = "implementation";

    public static final String FUNC_OWNER = "owner";

    public static final String FUNC_SETQUERYMSGMAXNUMBER = "setQueryMsgMaxNumber";

    public static final String FUNC_CHANGEOWNER = "changeOwner";

    public static final Event OWNERCHANGED_EVENT = new Event("OwnerChanged", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Address>() {}));
    ;

    public static final Event UPGRADED_EVENT = new Event("Upgraded", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}));
    ;

    @Deprecated
    protected CollaborationProxy(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected CollaborationProxy(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected CollaborationProxy(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected CollaborationProxy(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
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

    public RemoteCall<String> owner() {
        final Function function = new Function(FUNC_OWNER, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<TransactionReceipt> setQueryMsgMaxNumber(BigInteger number) {
        final Function function = new Function(
                FUNC_SETQUERYMSGMAXNUMBER, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(number)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
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
    public static CollaborationProxy load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new CollaborationProxy(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static CollaborationProxy load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new CollaborationProxy(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static CollaborationProxy load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new CollaborationProxy(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static CollaborationProxy load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new CollaborationProxy(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<CollaborationProxy> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(CollaborationProxy.class, web3j, credentials, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<CollaborationProxy> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(CollaborationProxy.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    public static RemoteCall<CollaborationProxy> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(CollaborationProxy.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<CollaborationProxy> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(CollaborationProxy.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }

    public static class OwnerChangedEventResponse {
        public Log log;

        public String previousOwner;

        public String newOwner;
    }

    public static class UpgradedEventResponse {
        public Log log;

        public String implementation;
    }
}
