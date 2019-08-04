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
import org.web3j.abi.datatypes.Bool;
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
public class Collaboration extends Contract {
    private static final String BINARY = "60806040819052600080546001600160a01b03191633178082556001600160a01b0316917f8be0079c531659141344cd1fd0a4f28419497f9722a3daafe3b4186f6b6457e0908290a3612028806100576000396000f3fe608060405234801561001057600080fd5b50600436106100a95760003560e01c80638da5cb5b116100715780638da5cb5b146105f35780638f32d59b146106175780639ac31bf814610633578063ac30b865146106d7578063d5d362c414610885578063f2fde38b1461088d576100a9565b80630eabeffe146100ae578063150be8cb1461025e5780634c020730146103025780635664d69c1461041b57806369a3ddc01461054f575b600080fd5b61025c600480360360608110156100c457600080fd5b810190602081018135600160201b8111156100de57600080fd5b8201836020820111156100f057600080fd5b803590602001918460018302840111600160201b8311171561011157600080fd5b91908080601f0160208091040260200160405190810160405280939291908181526020018383808284376000920191909152509295949360208101935035915050600160201b81111561016357600080fd5b82018360208201111561017557600080fd5b803590602001918460018302840111600160201b8311171561019657600080fd5b91908080601f0160208091040260200160405190810160405280939291908181526020018383808284376000920191909152509295949360208101935035915050600160201b8111156101e857600080fd5b8201836020820111156101fa57600080fd5b803590602001918460018302840111600160201b8311171561021b57600080fd5b91908080601f0160208091040260200160405190810160405280939291908181526020018383808284376000920191909152509295506108b3945050505050565b005b61025c6004803603602081101561027457600080fd5b810190602081018135600160201b81111561028e57600080fd5b8201836020820111156102a057600080fd5b803590602001918460018302840111600160201b831117156102c157600080fd5b91908080601f016020809104026020016040519081016040528093929190818152602001838380828437600092019190915250929550610bf8945050505050565b6103a66004803603602081101561031857600080fd5b810190602081018135600160201b81111561033257600080fd5b82018360208201111561034457600080fd5b803590602001918460018302840111600160201b8311171561036557600080fd5b91908080601f016020809104026020016040519081016040528093929190818152602001838380828437600092019190915250929550610eca945050505050565b6040805160208082528351818301528351919283929083019185019080838360005b838110156103e05781810151838201526020016103c8565b50505050905090810190601f16801561040d5780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b61025c6004803603606081101561043157600080fd5b810190602081018135600160201b81111561044b57600080fd5b82018360208201111561045d57600080fd5b803590602001918460018302840111600160201b8311171561047e57600080fd5b91908080601f0160208091040260200160405190810160405280939291908181526020018383808284376000920191909152509295949360208101935035915050600160201b8111156104d057600080fd5b8201836020820111156104e257600080fd5b803590602001918460018302840111600160201b8311171561050357600080fd5b91908080601f016020809104026020016040519081016040528093929190818152602001838380828437600092019190915250929550505090356001600160a01b0316915061110d9050565b6103a66004803603602081101561056557600080fd5b810190602081018135600160201b81111561057f57600080fd5b82018360208201111561059157600080fd5b803590602001918460018302840111600160201b831117156105b257600080fd5b91908080601f016020809104026020016040519081016040528093929190818152602001838380828437600092019190915250929550611277945050505050565b6105fb611466565b604080516001600160a01b039092168252519081900360200190f35b61061f611476565b604080519115158252519081900360200190f35b6103a66004803603602081101561064957600080fd5b810190602081018135600160201b81111561066357600080fd5b82018360208201111561067557600080fd5b803590602001918460018302840111600160201b8311171561069657600080fd5b91908080601f016020809104026020016040519081016040528093929190818152602001838380828437600092019190915250929550611487945050505050565b61025c600480360360608110156106ed57600080fd5b810190602081018135600160201b81111561070757600080fd5b82018360208201111561071957600080fd5b803590602001918460018302840111600160201b8311171561073a57600080fd5b91908080601f0160208091040260200160405190810160405280939291908181526020018383808284376000920191909152509295949360208101935035915050600160201b81111561078c57600080fd5b82018360208201111561079e57600080fd5b803590602001918460018302840111600160201b831117156107bf57600080fd5b91908080601f0160208091040260200160405190810160405280939291908181526020018383808284376000920191909152509295949360208101935035915050600160201b81111561081157600080fd5b82018360208201111561082357600080fd5b803590602001918460018302840111600160201b8311171561084457600080fd5b91908080601f01602080910402602001604051908101604052809392919081815260200183838082843760009201919091525092955061150d945050505050565b6103a66119c7565b61025c600480360360208110156108a357600080fd5b50356001600160a01b0316611bb8565b3360009081526008602052604090205460ff16610905576040805162461bcd60e51b81526020600482015260196024820152600080516020611fd4833981519152604482015290519081900360640190fd5b816001846040518082805190602001908083835b602083106109385780518252601f199092019160209182019101610919565b51815160209384036101000a600019018019909216911617905292019485525060405193849003810190932084516109799591949190910192509050611db4565b506007816040518082805190602001908083835b602083106109ac5780518252601f19909201916020918201910161098d565b51815160209384036101000a600019018019909216911617905292019485525060405193849003810184205487516001600160a01b039091169460029450889350918291908401908083835b60208310610a175780518252601f1990920191602091820191016109f8565b51815160209384036101000a6000190180199092169116179052920194855250604051938490038101842080546001600160a01b0319166001600160a01b0396909616959095179094555050825160039260009260079286928291908401908083835b60208310610a995780518252601f199092019160209182019101610a7a565b51815160209384036101000a600019018019909216911617905292019485525060408051948590038201909420546001600160a01b031685528481019590955250500160009081208054600181018083559183529183902086519193610b0793919091019190870190611db4565b5050826040518082805190602001908083835b60208310610b395780518252601f199092019160209182019101610b1a565b51815160209384036101000a600019018019909216911617905260405191909301819003812086519095503394506007938793508291908401908083835b60208310610b965780518252601f199092019160209182019101610b77565b51815160209384036101000a600019018019909216911617905292019485525060405193849003018320546001600160a01b0316927f41e5ed7693f15bc2c858b03d462d15c58f9bd8ffa77fe0c7aec8905f63933d5b925060009150a4505050565b3360009081526008602052604090205460ff16610c4a576040805162461bcd60e51b81526020600482015260196024820152600080516020611fd4833981519152604482015290519081900360640190fd5b6002816040518082805190602001908083835b60208310610c7c5780518252601f199092019160209182019101610c5d565b51815160209384036101000a60001901801990921691161790529201948552506040519384900301909220546001600160a01b031633149150610cf290505760405162461bcd60e51b8152600401808060200182810382526021815260200180611f526021913960400191505060405180910390fd5b336000908152600360205260408120905b8154811015610e41578280519060200120828281548110610d2057fe5b906000526020600020016040518082805460018160011615610100020316600290048015610d855780601f10610d63576101008083540402835291820191610d85565b820191906000526020600020905b815481529060010190602001808311610d71575b505091505060405180910390201415610e3957805b825460001901811015610dfe57828160010181548110610db657fe5b90600052602060002001838281548110610dcc57fe5b906000526020600020019080546001816001161561010002031660029004610df5929190611e32565b50600101610d9a565b50815482906000198101908110610e1157fe5b906000526020600020016000610e279190611ea7565b8154610e37836000198301611eeb565b505b600101610d03565b50816040518082805190602001908083835b60208310610e725780518252601f199092019160209182019101610e53565b5181516020939093036101000a60001901801990911692169190911790526040519201829003822093503392507f0da45264d5f650a67a3246c473047872b1a6dd0bd4a57388e61071dc62c8328d9160009150a35050565b3360009081526008602052604090205460609060ff16610f1f576040805162461bcd60e51b81526020600482015260196024820152600080516020611fd4833981519152604482015290519081900360640190fd5b60006005836040518082805190602001908083835b60208310610f535780518252601f199092019160209182019101610f34565b51815160209384036101000a600019018019909216911617905292019485525060405193849003019092209250600091508190505b8254811015610fc657828181548110610f9d57fe5b6000918252602090912001546001600160a01b0316331415610fbe57600191505b600101610f88565b5080611019576040805162461bcd60e51b815260206004820152601f60248201527f43616c6c657220617265206e6f7420746865207061727469636970616e742100604482015290519081900360640190fd5b6004846040518082805190602001908083835b6020831061104b5780518252601f19909201916020918201910161102c565b518151600019602094850361010090810a820192831692199390931691909117909252949092019687526040805197889003820188208054601f60026001831615909802909501169590950492830182900482028801820190528187529294509250508301828280156110ff5780601f106110d4576101008083540402835291602001916110ff565b820191906000526020600020905b8154815290600101906020018083116110e257829003601f168201915b505050505092505050919050565b611115611476565b611166576040805162461bcd60e51b815260206004820181905260248201527f4f776e61626c653a2063616c6c6572206973206e6f7420746865206f776e6572604482015290519081900360640190fd5b816006846040518082805190602001908083835b602083106111995780518252601f19909201916020918201910161117a565b51815160209384036101000a600019018019909216911617905292019485525060405193849003810190932084516111da9591949190910192509050611db4565b50806007846040518082805190602001908083835b6020831061120e5780518252601f1990920191602091820191016111ef565b51815160209384036101000a60001901801990921691161790529201948552506040805194859003820190942080546001600160a01b0319166001600160a01b03968716179055949093166000908152600890945250909120805460ff19166001179055505050565b3360009081526008602052604090205460609060ff166112cc576040805162461bcd60e51b81526020600482015260196024820152600080516020611fd4833981519152604482015290519081900360640190fd5b6002826040518082805190602001908083835b602083106112fe5780518252601f1990920191602091820191016112df565b51815160209384036101000a60001901801990921691161790529201948552506040519384900301909220546001600160a01b03163314915061137490505760405162461bcd60e51b8152600401808060200182810382526021815260200180611f526021913960400191505060405180910390fd5b6001826040518082805190602001908083835b602083106113a65780518252601f199092019160209182019101611387565b518151600019602094850361010090810a820192831692199390931691909117909252949092019687526040805197889003820188208054601f600260018316159098029095011695909504928301829004820288018201905281875292945092505083018282801561145a5780601f1061142f5761010080835404028352916020019161145a565b820191906000526020600020905b81548152906001019060200180831161143d57829003601f168201915b50505050509050919050565b6000546001600160a01b03165b90565b6000546001600160a01b0316331490565b3360009081526008602052604090205460609060ff166114dc576040805162461bcd60e51b81526020600482015260196024820152600080516020611fd4833981519152604482015290519081900360640190fd5b600682604051808280519060200190808383602083106113a65780518252601f199092019160209182019101611387565b3360009081526008602052604090205460ff1661155f576040805162461bcd60e51b81526020600482015260196024820152600080516020611fd4833981519152604482015290519081900360640190fd5b60006004846040518082805190602001908083835b602083106115935780518252601f199092019160209182019101611574565b518151600019602094850361010090810a820192831692199390931691909117909252949092019687526040805197889003820188208054601f6002600183161590980290950116959095049283018290048202880182019052818752929650606095945086935091840190508282801561164f5780601f106116245761010080835404028352916020019161164f565b820191906000526020600020905b81548152906001019060200180831161163257829003601f168201915b505050505090506000815111156117475760006005866040518082805190602001908083835b602083106116945780518252601f199092019160209182019101611675565b51815160209384036101000a600019018019909216911617905292019485525060405193849003019092209250600091508190505b8254811015611707578281815481106116de57fe5b6000918252602090912001546001600160a01b03163314156116ff57600191505b6001016116c9565b50806117445760405162461bcd60e51b815260040180806020018281038252603b815260200180611f73603b913960400191505060405180910390fd5b50505b836004866040518082805190602001908083835b6020831061177a5780518252601f19909201916020918201910161175b565b51815160209384036101000a600019018019909216911617905292019485525060405193849003810190932084516117bb9591949190910192509050611db4565b5060006005866040518082805190602001908083835b602083106117f05780518252601f1990920191602091820191016117d1565b51815160001960209485036101000a019081169019919091161790529201948552506040519384900381018420805460018101825560008281528390200180546001600160a01b031916331790558851909550859460079450899350918291908401908083835b602083106118765780518252601f199092019160209182019101611857565b51815160001960209485036101000a01908116901991909116179052920194855250604051938490038101842054855460018101875560009687529582902090950180546001600160a01b0319166001600160a01b03909616959095179094555050875188928291908401908083835b602083106119055780518252601f1990920191602091820191016118e6565b51815160209384036101000a600019018019909216911617905260405191909301819003812089519095503394506007938a93508291908401908083835b602083106119625780518252601f199092019160209182019101611943565b51815160209384036101000a600019018019909216911617905292019485525060405193849003018320546001600160a01b0316927f93cad124cc320903eab153f1621ff9181b36a29d8a3753faa2a6a1aa57f1adda925060009150a4505050505050565b3360009081526008602052604090205460609060ff16611a1c576040805162461bcd60e51b81526020600482015260196024820152600080516020611fd4833981519152604482015290519081900360640190fd5b336000908152600360209081526040808320805482518185028101850190935280835260609492939192909184015b82821015611af65760008481526020908190208301805460408051601f6002600019610100600187161502019094169390930492830185900485028101850190915281815292830182828015611ae25780601f10611ab757610100808354040283529160200191611ae2565b820191906000526020600020905b815481529060010190602001808311611ac557829003601f168201915b505050505081526020019060010190611a4b565b50506040805160208101909152600080825290955092935050505b8151811015611bb3576000828281518110611b2857fe5b6020026020010151511115611bab5780611b5757818181518110611b4857fe5b60200260200101519250611bab565b6040805180820190915260018152600b60fa1b6020820152611b8090849063ffffffff611c1d16565b9250611ba8828281518110611b9157fe5b602002602001015184611c1d90919063ffffffff16565b92505b600101611b11565b505090565b611bc0611476565b611c11576040805162461bcd60e51b815260206004820181905260248201527f4f776e61626c653a2063616c6c6572206973206e6f7420746865206f776e6572604482015290519081900360640190fd5b611c1a81611d14565b50565b6060808390506060839050606081518351016040519080825280601f01601f191660200182016040528015611c59576020820181803883390190505b509050806000805b8551811015611cb257858181518110611c7657fe5b602001015160f81c60f81b838380600101945081518110611c9357fe5b60200101906001600160f81b031916908160001a905350600101611c61565b5060005b8451811015611d0757848181518110611ccb57fe5b602001015160f81c60f81b838380600101945081518110611ce857fe5b60200101906001600160f81b031916908160001a905350600101611cb6565b5090979650505050505050565b6001600160a01b038116611d595760405162461bcd60e51b8152600401808060200182810382526026815260200180611fae6026913960400191505060405180910390fd5b600080546040516001600160a01b03808516939216917f8be0079c531659141344cd1fd0a4f28419497f9722a3daafe3b4186f6b6457e091a3600080546001600160a01b0319166001600160a01b0392909216919091179055565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f10611df557805160ff1916838001178555611e22565b82800160010185558215611e22579182015b82811115611e22578251825591602001919060010190611e07565b50611e2e929150611f14565b5090565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f10611e6b5780548555611e22565b82800160010185558215611e2257600052602060002091601f016020900482015b82811115611e22578254825591600101919060010190611e8c565b50805460018160011615610100020316600290046000825580601f10611ecd5750611c1a565b601f016020900490600052602060002090810190611c1a9190611f14565b815481835581811115611f0f57600083815260209020611f0f918101908301611f2e565b505050565b61147391905b80821115611e2e5760008155600101611f1a565b61147391905b80821115611e2e576000611f488282611ea7565b50600101611f3456fe43616c6c657220617265206e6f7420746865206d657373616765206f776e657221546865207472616e73616374696f6e20697320657869737465642c2043616c6c657220617265206e6f7420746865207061727469636970616e74214f776e61626c653a206e6577206f776e657220697320746865207a65726f206164647265737343616c6c6572206973206e6f7420726567697374657265642100000000000000a265627a7a72305820254debfa5c429c5707f80cdee0238446daa6186d8d68de3c361eaea4d175fdaa64736f6c634300050a0032";

    public static final String FUNC_SENDMESSAGE = "sendMessage";

    public static final String FUNC_WITHDRAWPENDINGMESSAGE = "withdrawPendingMessage";

    public static final String FUNC_FINDTRANSACTION = "findTransaction";

    public static final String FUNC_REGISTER = "register";

    public static final String FUNC_FINDMESSAGE = "findMessage";

    public static final String FUNC_OWNER = "owner";

    public static final String FUNC_ISOWNER = "isOwner";

    public static final String FUNC_FINDORGPUBKEY = "findOrgPubKey";

    public static final String FUNC_SENDTRANSACTION = "sendTransaction";

    public static final String FUNC_FINDPENDINGMESSAGESBYOWNER = "findPendingMessagesByOwner";

    public static final String FUNC_TRANSFEROWNERSHIP = "transferOwnership";

    public static final Event SENDTRANSACTION_EVENT = new Event("SendTransaction", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}, new TypeReference<Utf8String>(true) {}));
    ;

    public static final Event SENDMESSAGE_EVENT = new Event("SendMessage", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}, new TypeReference<Utf8String>(true) {}));
    ;

    public static final Event WITHDRAWPENDINGMESSAGE_EVENT = new Event("WithdrawPendingMessage", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Utf8String>(true) {}));
    ;

    public static final Event OWNERSHIPTRANSFERRED_EVENT = new Event("OwnershipTransferred", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}));
    ;

    @Deprecated
    protected Collaboration(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected Collaboration(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected Collaboration(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected Collaboration(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public RemoteCall<TransactionReceipt> sendMessage(String msgID, String message, String owner) {
        final Function function = new Function(
                FUNC_SENDMESSAGE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(msgID), 
                new org.web3j.abi.datatypes.Utf8String(message), 
                new org.web3j.abi.datatypes.Utf8String(owner)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> withdrawPendingMessage(String msgID) {
        final Function function = new Function(
                FUNC_WITHDRAWPENDINGMESSAGE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(msgID)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<String> findTransaction(String transactionNumber) {
        final Function function = new Function(FUNC_FINDTRANSACTION, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(transactionNumber)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
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

    public RemoteCall<String> findMessage(String msgID) {
        final Function function = new Function(FUNC_FINDMESSAGE, 
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

    public RemoteCall<Boolean> isOwner() {
        final Function function = new Function(FUNC_ISOWNER, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteCall<String> findOrgPubKey(String orgCode) {
        final Function function = new Function(FUNC_FINDORGPUBKEY, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(orgCode)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<TransactionReceipt> sendTransaction(String transactionNumber, String transaction, String participant) {
        final Function function = new Function(
                FUNC_SENDTRANSACTION, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(transactionNumber), 
                new org.web3j.abi.datatypes.Utf8String(transaction), 
                new org.web3j.abi.datatypes.Utf8String(participant)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<String> findPendingMessagesByOwner() {
        final Function function = new Function(FUNC_FINDPENDINGMESSAGESBYOWNER, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<TransactionReceipt> transferOwnership(String newOwner) {
        final Function function = new Function(
                FUNC_TRANSFEROWNERSHIP, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(newOwner)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public List<SendTransactionEventResponse> getSendTransactionEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(SENDTRANSACTION_EVENT, transactionReceipt);
        ArrayList<SendTransactionEventResponse> responses = new ArrayList<SendTransactionEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            SendTransactionEventResponse typedResponse = new SendTransactionEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.owner = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.sender = (String) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.transactionNumber = (byte[]) eventValues.getIndexedValues().get(2).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<SendTransactionEventResponse> sendTransactionEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new io.reactivex.functions.Function<Log, SendTransactionEventResponse>() {
            @Override
            public SendTransactionEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(SENDTRANSACTION_EVENT, log);
                SendTransactionEventResponse typedResponse = new SendTransactionEventResponse();
                typedResponse.log = log;
                typedResponse.owner = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.sender = (String) eventValues.getIndexedValues().get(1).getValue();
                typedResponse.transactionNumber = (byte[]) eventValues.getIndexedValues().get(2).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<SendTransactionEventResponse> sendTransactionEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(SENDTRANSACTION_EVENT));
        return sendTransactionEventFlowable(filter);
    }

    public List<SendMessageEventResponse> getSendMessageEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(SENDMESSAGE_EVENT, transactionReceipt);
        ArrayList<SendMessageEventResponse> responses = new ArrayList<SendMessageEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            SendMessageEventResponse typedResponse = new SendMessageEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.owner = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.sender = (String) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.msgID = (byte[]) eventValues.getIndexedValues().get(2).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<SendMessageEventResponse> sendMessageEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new io.reactivex.functions.Function<Log, SendMessageEventResponse>() {
            @Override
            public SendMessageEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(SENDMESSAGE_EVENT, log);
                SendMessageEventResponse typedResponse = new SendMessageEventResponse();
                typedResponse.log = log;
                typedResponse.owner = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.sender = (String) eventValues.getIndexedValues().get(1).getValue();
                typedResponse.msgID = (byte[]) eventValues.getIndexedValues().get(2).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<SendMessageEventResponse> sendMessageEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(SENDMESSAGE_EVENT));
        return sendMessageEventFlowable(filter);
    }

    public List<WithdrawPendingMessageEventResponse> getWithdrawPendingMessageEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(WITHDRAWPENDINGMESSAGE_EVENT, transactionReceipt);
        ArrayList<WithdrawPendingMessageEventResponse> responses = new ArrayList<WithdrawPendingMessageEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            WithdrawPendingMessageEventResponse typedResponse = new WithdrawPendingMessageEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.owner = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.msgID = (byte[]) eventValues.getIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<WithdrawPendingMessageEventResponse> withdrawPendingMessageEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new io.reactivex.functions.Function<Log, WithdrawPendingMessageEventResponse>() {
            @Override
            public WithdrawPendingMessageEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(WITHDRAWPENDINGMESSAGE_EVENT, log);
                WithdrawPendingMessageEventResponse typedResponse = new WithdrawPendingMessageEventResponse();
                typedResponse.log = log;
                typedResponse.owner = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.msgID = (byte[]) eventValues.getIndexedValues().get(1).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<WithdrawPendingMessageEventResponse> withdrawPendingMessageEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(WITHDRAWPENDINGMESSAGE_EVENT));
        return withdrawPendingMessageEventFlowable(filter);
    }

    public List<OwnershipTransferredEventResponse> getOwnershipTransferredEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(OWNERSHIPTRANSFERRED_EVENT, transactionReceipt);
        ArrayList<OwnershipTransferredEventResponse> responses = new ArrayList<OwnershipTransferredEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            OwnershipTransferredEventResponse typedResponse = new OwnershipTransferredEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.previousOwner = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.newOwner = (String) eventValues.getIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<OwnershipTransferredEventResponse> ownershipTransferredEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new io.reactivex.functions.Function<Log, OwnershipTransferredEventResponse>() {
            @Override
            public OwnershipTransferredEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(OWNERSHIPTRANSFERRED_EVENT, log);
                OwnershipTransferredEventResponse typedResponse = new OwnershipTransferredEventResponse();
                typedResponse.log = log;
                typedResponse.previousOwner = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.newOwner = (String) eventValues.getIndexedValues().get(1).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<OwnershipTransferredEventResponse> ownershipTransferredEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(OWNERSHIPTRANSFERRED_EVENT));
        return ownershipTransferredEventFlowable(filter);
    }

    @Deprecated
    public static Collaboration load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new Collaboration(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static Collaboration load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new Collaboration(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static Collaboration load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new Collaboration(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static Collaboration load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new Collaboration(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<Collaboration> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(Collaboration.class, web3j, credentials, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<Collaboration> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(Collaboration.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    public static RemoteCall<Collaboration> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(Collaboration.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<Collaboration> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(Collaboration.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }

    public static class SendTransactionEventResponse {
        public Log log;

        public String owner;

        public String sender;

        public byte[] transactionNumber;
    }

    public static class SendMessageEventResponse {
        public Log log;

        public String owner;

        public String sender;

        public byte[] msgID;
    }

    public static class WithdrawPendingMessageEventResponse {
        public Log log;

        public String owner;

        public byte[] msgID;
    }

    public static class OwnershipTransferredEventResponse {
        public Log log;

        public String previousOwner;

        public String newOwner;
    }
}
