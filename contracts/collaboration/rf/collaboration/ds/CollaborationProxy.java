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
import org.web3j.abi.datatypes.Utf8String;
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
    private static final String BINARY = "6080604052606460005561001b336001600160e01b0361002016565b610055565b604080517f636f6d2e736869652e6f776e65720000000000000000000000000000000000008152905190819003600e01902055565b610c95806100646000396000f3fe60806040526004361061007b5760003560e01c80638da5cb5b1161004e5780638da5cb5b14610350578063988a23a014610365578063a6f9dae11461038f578063fb7c6bf6146103c25761007b565b80633659cfe6146100855780635664d69c146100b85780635c60da1b146101f9578063603bebcd1461022a575b610083610473565b005b34801561009157600080fd5b50610083600480360360208110156100a857600080fd5b50356001600160a01b031661048d565b3480156100c457600080fd5b50610083600480360360608110156100db57600080fd5b810190602081018135600160201b8111156100f557600080fd5b82018360208201111561010757600080fd5b803590602001918460018302840111600160201b8311171561012857600080fd5b91908080601f0160208091040260200160405190810160405280939291908181526020018383808284376000920191909152509295949360208101935035915050600160201b81111561017a57600080fd5b82018360208201111561018c57600080fd5b803590602001918460018302840111600160201b831117156101ad57600080fd5b91908080601f016020809104026020016040519081016040528093929190818152602001838380828437600092019190915250929550505090356001600160a01b031691506104f49050565b34801561020557600080fd5b5061020e610660565b604080516001600160a01b039092168252519081900360200190f35b34801561023657600080fd5b506102db6004803603602081101561024d57600080fd5b810190602081018135600160201b81111561026757600080fd5b82018360208201111561027957600080fd5b803590602001918460018302840111600160201b8311171561029a57600080fd5b91908080601f0160208091040260200160405190810160405280939291908181526020018383808284376000920191909152509295506106cb945050505050565b6040805160208082528351818301528351919283929083019185019080838360005b838110156103155781810151838201526020016102fd565b50505050905090810190601f1680156103425780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b34801561035c57600080fd5b5061020e61081a565b34801561037157600080fd5b506100836004803603602081101561038857600080fd5b503561087f565b34801561039b57600080fd5b50610083600480360360208110156103b257600080fd5b50356001600160a01b03166108df565b3480156103ce57600080fd5b506102db600480360360208110156103e557600080fd5b810190602081018135600160201b8111156103ff57600080fd5b82018360208201111561041157600080fd5b803590602001918460018302840111600160201b8311171561043257600080fd5b91908080601f0160208091040260200160405190810160405280939291908181526020018383808284376000920191909152509295506109d3945050505050565b61047b61048b565b61048b610486610a61565b610a97565b565b610495610abb565b6001600160a01b0316336001600160a01b0316146104e8576040805162461bcd60e51b81526020600482015260176024820152600080516020610c41833981519152604482015290519081900360640190fd5b6104f181610ae2565b50565b6104fc610abb565b6001600160a01b0316336001600160a01b03161461054f576040805162461bcd60e51b81526020600482015260176024820152600080516020610c41833981519152604482015290519081900360640190fd5b816006846040518082805190602001908083835b602083106105825780518252601f199092019160209182019101610563565b51815160209384036101000a600019018019909216911617905292019485525060405193849003810190932084516105c39591949190910192509050610b7d565b50806007846040518082805190602001908083835b602083106105f75780518252601f1990920191602091820191016105d8565b51815160209384036101000a60001901801990921691161790529201948552506040805194859003820190942080546001600160a01b0319166001600160a01b03968716179055949093166000908152600890945250909120805460ff19166001179055505050565b600061066a610abb565b6001600160a01b0316336001600160a01b0316146106bd576040805162461bcd60e51b81526020600482015260176024820152600080516020610c41833981519152604482015290519081900360640190fd5b6106c5610a61565b90505b90565b60606106d5610abb565b6001600160a01b0316336001600160a01b031614610728576040805162461bcd60e51b81526020600482015260176024820152600080516020610c41833981519152604482015290519081900360640190fd5b6001826040518082805190602001908083835b6020831061075a5780518252601f19909201916020918201910161073b565b518151600019602094850361010090810a820192831692199390931691909117909252949092019687526040805197889003820188208054601f600260018316159098029095011695909504928301829004820288018201905281875292945092505083018282801561080e5780601f106107e35761010080835404028352916020019161080e565b820191906000526020600020905b8154815290600101906020018083116107f157829003601f168201915b50505050509050919050565b6000610824610abb565b6001600160a01b0316336001600160a01b031614610877576040805162461bcd60e51b81526020600482015260176024820152600080516020610c41833981519152604482015290519081900360640190fd5b6106c5610abb565b610887610abb565b6001600160a01b0316336001600160a01b0316146108da576040805162461bcd60e51b81526020600482015260176024820152600080516020610c41833981519152604482015290519081900360640190fd5b600055565b6108e7610abb565b6001600160a01b0316336001600160a01b03161461093a576040805162461bcd60e51b81526020600482015260176024820152600080516020610c41833981519152604482015290519081900360640190fd5b6001600160a01b03811661097f5760405162461bcd60e51b815260040180806020018281038252602b815260200180610c16602b913960400191505060405180910390fd5b7fb532073b38c83145e3e5135377a08bf9aab55bc0fd7c1179cd4fb995d2a5159c6109a8610abb565b604080516001600160a01b03928316815291841660208301528051918290030190a16104f181610b22565b60606109dd610abb565b6001600160a01b0316336001600160a01b031614610a30576040805162461bcd60e51b81526020600482015260176024820152600080516020610c41833981519152604482015290519081900360640190fd5b6004826040518082805190602001908083836020831061075a5780518252601f19909201916020918201910161073b565b604080517f636f6d2e736869652e70726f78792e696d706c656d656e746174696f6e0000008152905190819003601d0190205490565b3660008037600080366000845af43d6000803e808015610ab6573d6000f35b3d6000fd5b604080516d31b7b69739b434b29737bbb732b960911b8152905190819003600e0190205490565b610aeb81610b48565b6040516001600160a01b038216907fbc7cd75a20ee27fd9adebab32041f755214dbc6bffa90cc0225b39da2e5c2d3b90600090a250565b604080516d31b7b69739b434b29737bbb732b960911b8152905190819003600e01902055565b604080517f636f6d2e736869652e70726f78792e696d706c656d656e746174696f6e0000008152905190819003601d01902055565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f10610bbe57805160ff1916838001178555610beb565b82800160010185558215610beb579182015b82811115610beb578251825591602001919060010190610bd0565b50610bf7929150610bfb565b5090565b6106c891905b80821115610bf75760008155600101610c0156fe43616e6e6f74206368616e676520746865204f776e657220746f20746865207a65726f206164647265737343616c6c65722069736e277420746865206f776e657221000000000000000000a265627a7a7230582090a9e178ddf08460be5bd4b4d5994b316c0999ca3dd9a2661c96ff8f5eb97d4f64736f6c634300050a0032";

    public static final String FUNC_UPGRADETO = "upgradeTo";

    public static final String FUNC_REGISTER = "register";

    public static final String FUNC_IMPLEMENTATION = "implementation";

    public static final String FUNC_EXPORTMESSAGE = "exportMessage";

    public static final String FUNC_OWNER = "owner";

    public static final String FUNC_SETQUERYMSGMAXNUMBER = "setQueryMsgMaxNumber";

    public static final String FUNC_CHANGEOWNER = "changeOwner";

    public static final String FUNC_EXPORTTRANSACTION = "exportTransaction";

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

    public RemoteCall<String> exportMessage(String msgID) {
        final Function function = new Function(FUNC_EXPORTMESSAGE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(msgID)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
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

    public RemoteCall<String> exportTransaction(String transactionNumber) {
        final Function function = new Function(FUNC_EXPORTTRANSACTION, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(transactionNumber)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
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