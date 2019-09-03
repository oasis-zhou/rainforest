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
    private static final String BINARY = "608060408190526064600155600080546001600160a01b03191633178082556001600160a01b0316917f8be0079c531659141344cd1fd0a4f28419497f9722a3daafe3b4186f6b6457e0908290a36120c98061005c6000396000f3fe608060405234801561001057600080fd5b50600436106100b45760003560e01c80638f32d59b116100715780638f32d59b1461062257806394f806a41461063e5780639ac31bf81461065e578063ac30b86514610702578063d5d362c4146108b0578063f2fde38b146108b8576100b4565b80630eabeffe146100b9578063150be8cb146102695780634c0207301461030d5780635664d69c1461042657806369a3ddc01461055a5780638da5cb5b146105fe575b600080fd5b610267600480360360608110156100cf57600080fd5b810190602081018135600160201b8111156100e957600080fd5b8201836020820111156100fb57600080fd5b803590602001918460018302840111600160201b8311171561011c57600080fd5b91908080601f0160208091040260200160405190810160405280939291908181526020018383808284376000920191909152509295949360208101935035915050600160201b81111561016e57600080fd5b82018360208201111561018057600080fd5b803590602001918460018302840111600160201b831117156101a157600080fd5b91908080601f0160208091040260200160405190810160405280939291908181526020018383808284376000920191909152509295949360208101935035915050600160201b8111156101f357600080fd5b82018360208201111561020557600080fd5b803590602001918460018302840111600160201b8311171561022657600080fd5b91908080601f0160208091040260200160405190810160405280939291908181526020018383808284376000920191909152509295506108de945050505050565b005b6102676004803603602081101561027f57600080fd5b810190602081018135600160201b81111561029957600080fd5b8201836020820111156102ab57600080fd5b803590602001918460018302840111600160201b831117156102cc57600080fd5b91908080601f016020809104026020016040519081016040528093929190818152602001838380828437600092019190915250929550610c23945050505050565b6103b16004803603602081101561032357600080fd5b810190602081018135600160201b81111561033d57600080fd5b82018360208201111561034f57600080fd5b803590602001918460018302840111600160201b8311171561037057600080fd5b91908080601f016020809104026020016040519081016040528093929190818152602001838380828437600092019190915250929550610ef5945050505050565b6040805160208082528351818301528351919283929083019185019080838360005b838110156103eb5781810151838201526020016103d3565b50505050905090810190601f1680156104185780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b6102676004803603606081101561043c57600080fd5b810190602081018135600160201b81111561045657600080fd5b82018360208201111561046857600080fd5b803590602001918460018302840111600160201b8311171561048957600080fd5b91908080601f0160208091040260200160405190810160405280939291908181526020018383808284376000920191909152509295949360208101935035915050600160201b8111156104db57600080fd5b8201836020820111156104ed57600080fd5b803590602001918460018302840111600160201b8311171561050e57600080fd5b91908080601f016020809104026020016040519081016040528093929190818152602001838380828437600092019190915250929550505090356001600160a01b031691506111389050565b6103b16004803603602081101561057057600080fd5b810190602081018135600160201b81111561058a57600080fd5b82018360208201111561059c57600080fd5b803590602001918460018302840111600160201b831117156105bd57600080fd5b91908080601f0160208091040260200160405190810160405280939291908181526020018383808284376000920191909152509295506112a2945050505050565b610606611491565b604080516001600160a01b039092168252519081900360200190f35b61062a6114a1565b604080519115158252519081900360200190f35b6102676004803603602081101561065457600080fd5b503560ff166114b2565b6103b16004803603602081101561067457600080fd5b810190602081018135600160201b81111561068e57600080fd5b8201836020820111156106a057600080fd5b803590602001918460018302840111600160201b831117156106c157600080fd5b91908080601f016020809104026020016040519081016040528093929190818152602001838380828437600092019190915250929550611513945050505050565b6102676004803603606081101561071857600080fd5b810190602081018135600160201b81111561073257600080fd5b82018360208201111561074457600080fd5b803590602001918460018302840111600160201b8311171561076557600080fd5b91908080601f0160208091040260200160405190810160405280939291908181526020018383808284376000920191909152509295949360208101935035915050600160201b8111156107b757600080fd5b8201836020820111156107c957600080fd5b803590602001918460018302840111600160201b831117156107ea57600080fd5b91908080601f0160208091040260200160405190810160405280939291908181526020018383808284376000920191909152509295949360208101935035915050600160201b81111561083c57600080fd5b82018360208201111561084e57600080fd5b803590602001918460018302840111600160201b8311171561086f57600080fd5b91908080601f016020809104026020016040519081016040528093929190818152602001838380828437600092019190915250929550611599945050505050565b6103b1611a53565b610267600480360360208110156108ce57600080fd5b50356001600160a01b0316611c59565b3360009081526009602052604090205460ff16610930576040805162461bcd60e51b81526020600482015260196024820152600080516020612075833981519152604482015290519081900360640190fd5b816002846040518082805190602001908083835b602083106109635780518252601f199092019160209182019101610944565b51815160209384036101000a600019018019909216911617905292019485525060405193849003810190932084516109a49591949190910192509050611e55565b506008816040518082805190602001908083835b602083106109d75780518252601f1990920191602091820191016109b8565b51815160209384036101000a600019018019909216911617905292019485525060405193849003810184205487516001600160a01b039091169460039450889350918291908401908083835b60208310610a425780518252601f199092019160209182019101610a23565b51815160209384036101000a6000190180199092169116179052920194855250604051938490038101842080546001600160a01b0319166001600160a01b0396909616959095179094555050825160049260009260089286928291908401908083835b60208310610ac45780518252601f199092019160209182019101610aa5565b51815160209384036101000a600019018019909216911617905292019485525060408051948590038201909420546001600160a01b031685528481019590955250500160009081208054600181018083559183529183902086519193610b3293919091019190870190611e55565b5050826040518082805190602001908083835b60208310610b645780518252601f199092019160209182019101610b45565b51815160209384036101000a600019018019909216911617905260405191909301819003812086519095503394506008938793508291908401908083835b60208310610bc15780518252601f199092019160209182019101610ba2565b51815160209384036101000a600019018019909216911617905292019485525060405193849003018320546001600160a01b0316927f41e5ed7693f15bc2c858b03d462d15c58f9bd8ffa77fe0c7aec8905f63933d5b925060009150a4505050565b3360009081526009602052604090205460ff16610c75576040805162461bcd60e51b81526020600482015260196024820152600080516020612075833981519152604482015290519081900360640190fd5b6003816040518082805190602001908083835b60208310610ca75780518252601f199092019160209182019101610c88565b51815160209384036101000a60001901801990921691161790529201948552506040519384900301909220546001600160a01b031633149150610d1d90505760405162461bcd60e51b8152600401808060200182810382526021815260200180611ff36021913960400191505060405180910390fd5b336000908152600460205260408120905b8154811015610e6c578280519060200120828281548110610d4b57fe5b906000526020600020016040518082805460018160011615610100020316600290048015610db05780601f10610d8e576101008083540402835291820191610db0565b820191906000526020600020905b815481529060010190602001808311610d9c575b505091505060405180910390201415610e6457805b825460001901811015610e2957828160010181548110610de157fe5b90600052602060002001838281548110610df757fe5b906000526020600020019080546001816001161561010002031660029004610e20929190611ed3565b50600101610dc5565b50815482906000198101908110610e3c57fe5b906000526020600020016000610e529190611f48565b8154610e62836000198301611f8c565b505b600101610d2e565b50816040518082805190602001908083835b60208310610e9d5780518252601f199092019160209182019101610e7e565b5181516020939093036101000a60001901801990911692169190911790526040519201829003822093503392507f0da45264d5f650a67a3246c473047872b1a6dd0bd4a57388e61071dc62c8328d9160009150a35050565b3360009081526009602052604090205460609060ff16610f4a576040805162461bcd60e51b81526020600482015260196024820152600080516020612075833981519152604482015290519081900360640190fd5b60006006836040518082805190602001908083835b60208310610f7e5780518252601f199092019160209182019101610f5f565b51815160209384036101000a600019018019909216911617905292019485525060405193849003019092209250600091508190505b8254811015610ff157828181548110610fc857fe5b6000918252602090912001546001600160a01b0316331415610fe957600191505b600101610fb3565b5080611044576040805162461bcd60e51b815260206004820152601f60248201527f43616c6c657220617265206e6f7420746865207061727469636970616e742100604482015290519081900360640190fd5b6005846040518082805190602001908083835b602083106110765780518252601f199092019160209182019101611057565b518151600019602094850361010090810a820192831692199390931691909117909252949092019687526040805197889003820188208054601f600260018316159098029095011695909504928301829004820288018201905281875292945092505083018282801561112a5780601f106110ff5761010080835404028352916020019161112a565b820191906000526020600020905b81548152906001019060200180831161110d57829003601f168201915b505050505092505050919050565b6111406114a1565b611191576040805162461bcd60e51b815260206004820181905260248201527f4f776e61626c653a2063616c6c6572206973206e6f7420746865206f776e6572604482015290519081900360640190fd5b816007846040518082805190602001908083835b602083106111c45780518252601f1990920191602091820191016111a5565b51815160209384036101000a600019018019909216911617905292019485525060405193849003810190932084516112059591949190910192509050611e55565b50806008846040518082805190602001908083835b602083106112395780518252601f19909201916020918201910161121a565b51815160209384036101000a60001901801990921691161790529201948552506040805194859003820190942080546001600160a01b0319166001600160a01b03968716179055949093166000908152600990945250909120805460ff19166001179055505050565b3360009081526009602052604090205460609060ff166112f7576040805162461bcd60e51b81526020600482015260196024820152600080516020612075833981519152604482015290519081900360640190fd5b6003826040518082805190602001908083835b602083106113295780518252601f19909201916020918201910161130a565b51815160209384036101000a60001901801990921691161790529201948552506040519384900301909220546001600160a01b03163314915061139f90505760405162461bcd60e51b8152600401808060200182810382526021815260200180611ff36021913960400191505060405180910390fd5b6002826040518082805190602001908083835b602083106113d15780518252601f1990920191602091820191016113b2565b518151600019602094850361010090810a820192831692199390931691909117909252949092019687526040805197889003820188208054601f60026001831615909802909501169590950492830182900482028801820190528187529294509250508301828280156114855780601f1061145a57610100808354040283529160200191611485565b820191906000526020600020905b81548152906001019060200180831161146857829003601f168201915b50505050509050919050565b6000546001600160a01b03165b90565b6000546001600160a01b0316331490565b6114ba6114a1565b61150b576040805162461bcd60e51b815260206004820181905260248201527f4f776e61626c653a2063616c6c6572206973206e6f7420746865206f776e6572604482015290519081900360640190fd5b60ff16600155565b3360009081526009602052604090205460609060ff16611568576040805162461bcd60e51b81526020600482015260196024820152600080516020612075833981519152604482015290519081900360640190fd5b600782604051808280519060200190808383602083106113d15780518252601f1990920191602091820191016113b2565b3360009081526009602052604090205460ff166115eb576040805162461bcd60e51b81526020600482015260196024820152600080516020612075833981519152604482015290519081900360640190fd5b60006005846040518082805190602001908083835b6020831061161f5780518252601f199092019160209182019101611600565b518151600019602094850361010090810a820192831692199390931691909117909252949092019687526040805197889003820188208054601f600260018316159098029095011695909504928301829004820288018201905281875292965060609594508693509184019050828280156116db5780601f106116b0576101008083540402835291602001916116db565b820191906000526020600020905b8154815290600101906020018083116116be57829003601f168201915b505050505090506000815111156117d35760006006866040518082805190602001908083835b602083106117205780518252601f199092019160209182019101611701565b51815160209384036101000a600019018019909216911617905292019485525060405193849003019092209250600091508190505b82548110156117935782818154811061176a57fe5b6000918252602090912001546001600160a01b031633141561178b57600191505b600101611755565b50806117d05760405162461bcd60e51b815260040180806020018281038252603b815260200180612014603b913960400191505060405180910390fd5b50505b836005866040518082805190602001908083835b602083106118065780518252601f1990920191602091820191016117e7565b51815160209384036101000a600019018019909216911617905292019485525060405193849003810190932084516118479591949190910192509050611e55565b5060006006866040518082805190602001908083835b6020831061187c5780518252601f19909201916020918201910161185d565b51815160001960209485036101000a019081169019919091161790529201948552506040519384900381018420805460018101825560008281528390200180546001600160a01b031916331790558851909550859460089450899350918291908401908083835b602083106119025780518252601f1990920191602091820191016118e3565b51815160001960209485036101000a01908116901991909116179052920194855250604051938490038101842054855460018101875560009687529582902090950180546001600160a01b0319166001600160a01b03909616959095179094555050875188928291908401908083835b602083106119915780518252601f199092019160209182019101611972565b51815160209384036101000a600019018019909216911617905260405191909301819003812089519095503394506008938a93508291908401908083835b602083106119ee5780518252601f1990920191602091820191016119cf565b51815160209384036101000a600019018019909216911617905292019485525060405193849003018320546001600160a01b0316927f93cad124cc320903eab153f1621ff9181b36a29d8a3753faa2a6a1aa57f1adda925060009150a4505050505050565b3360009081526009602052604090205460609060ff16611aa8576040805162461bcd60e51b81526020600482015260196024820152600080516020612075833981519152604482015290519081900360640190fd5b336000908152600460209081526040808320805482518185028101850190935280835260609492939192909184015b82821015611b825760008481526020908190208301805460408051601f6002600019610100600187161502019094169390930492830185900485028101850190915281815292830182828015611b6e5780601f10611b4357610100808354040283529160200191611b6e565b820191906000526020600020905b815481529060010190602001808311611b5157829003601f168201915b505050505081526020019060010190611ad7565b505050509050604051806020016040528060008152509150600081519050600154811115611baf57506001545b60005b81811015611c53576000838281518110611bc857fe5b6020026020010151511115611c4b5780611bf757828181518110611be857fe5b60200260200101519350611c4b565b6040805180820190915260018152600b60fa1b6020820152611c2090859063ffffffff611cbe16565b9350611c48838281518110611c3157fe5b602002602001015185611cbe90919063ffffffff16565b93505b600101611bb2565b50505090565b611c616114a1565b611cb2576040805162461bcd60e51b815260206004820181905260248201527f4f776e61626c653a2063616c6c6572206973206e6f7420746865206f776e6572604482015290519081900360640190fd5b611cbb81611db5565b50565b6060808390506060839050606081518351016040519080825280601f01601f191660200182016040528015611cfa576020820181803883390190505b509050806000805b8551811015611d5357858181518110611d1757fe5b602001015160f81c60f81b838380600101945081518110611d3457fe5b60200101906001600160f81b031916908160001a905350600101611d02565b5060005b8451811015611da857848181518110611d6c57fe5b602001015160f81c60f81b838380600101945081518110611d8957fe5b60200101906001600160f81b031916908160001a905350600101611d57565b5090979650505050505050565b6001600160a01b038116611dfa5760405162461bcd60e51b815260040180806020018281038252602681526020018061204f6026913960400191505060405180910390fd5b600080546040516001600160a01b03808516939216917f8be0079c531659141344cd1fd0a4f28419497f9722a3daafe3b4186f6b6457e091a3600080546001600160a01b0319166001600160a01b0392909216919091179055565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f10611e9657805160ff1916838001178555611ec3565b82800160010185558215611ec3579182015b82811115611ec3578251825591602001919060010190611ea8565b50611ecf929150611fb5565b5090565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f10611f0c5780548555611ec3565b82800160010185558215611ec357600052602060002091601f016020900482015b82811115611ec3578254825591600101919060010190611f2d565b50805460018160011615610100020316600290046000825580601f10611f6e5750611cbb565b601f016020900490600052602060002090810190611cbb9190611fb5565b815481835581811115611fb057600083815260209020611fb0918101908301611fcf565b505050565b61149e91905b80821115611ecf5760008155600101611fbb565b61149e91905b80821115611ecf576000611fe98282611f48565b50600101611fd556fe43616c6c657220617265206e6f7420746865206d657373616765206f776e657221546865207472616e73616374696f6e20697320657869737465642c2043616c6c657220617265206e6f7420746865207061727469636970616e74214f776e61626c653a206e6577206f776e657220697320746865207a65726f206164647265737343616c6c6572206973206e6f7420726567697374657265642100000000000000a265627a7a723058203c9d954c17d3588c2a65defb3540624a22a4f55a927435221e3998d6a313233964736f6c634300050a0032";

    public static final String FUNC_SENDMESSAGE = "sendMessage";

    public static final String FUNC_WITHDRAWPENDINGMESSAGE = "withdrawPendingMessage";

    public static final String FUNC_FINDTRANSACTION = "findTransaction";

    public static final String FUNC_REGISTER = "register";

    public static final String FUNC_FINDMESSAGE = "findMessage";

    public static final String FUNC_OWNER = "owner";

    public static final String FUNC_ISOWNER = "isOwner";

    public static final String FUNC_RESETMESSAGENUMBER = "resetMessageNumber";

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

    public RemoteCall<TransactionReceipt> resetMessageNumber(BigInteger number) {
        final Function function = new Function(
                FUNC_RESETMESSAGENUMBER, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint8(number)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<String> findOrgPubKey(String orgCode) {
        final Function function = new Function(FUNC_FINDORGPUBKEY, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(orgCode)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<TransactionReceipt> sendTransaction(String transactionNumber, String transaction, String receiver) {
        final Function function = new Function(
                FUNC_SENDTRANSACTION, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(transactionNumber), 
                new org.web3j.abi.datatypes.Utf8String(transaction), 
                new org.web3j.abi.datatypes.Utf8String(receiver)), 
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
