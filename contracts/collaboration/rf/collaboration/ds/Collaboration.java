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
public class Collaboration extends Contract {
    private static final String BINARY = "6080604052606460005561001b336001600160e01b0361002016565b610055565b604080517f636f6d2e736869652e6f776e65720000000000000000000000000000000000008152905190819003600e01902055565b61215e806100646000396000f3fe608060405234801561001057600080fd5b50600436106100a95760003560e01c80638da5cb5b116100715780638da5cb5b146105f3578063988a23a0146106175780639ac31bf814610634578063a6f9dae1146106d8578063ac30b865146106fe578063d5d362c4146108ac576100a9565b80630eabeffe146100ae578063150be8cb1461025e5780634c020730146103025780635664d69c1461041b57806369a3ddc01461054f575b600080fd5b61025c600480360360608110156100c457600080fd5b810190602081018135600160201b8111156100de57600080fd5b8201836020820111156100f057600080fd5b803590602001918460018302840111600160201b8311171561011157600080fd5b91908080601f0160208091040260200160405190810160405280939291908181526020018383808284376000920191909152509295949360208101935035915050600160201b81111561016357600080fd5b82018360208201111561017557600080fd5b803590602001918460018302840111600160201b8311171561019657600080fd5b91908080601f0160208091040260200160405190810160405280939291908181526020018383808284376000920191909152509295949360208101935035915050600160201b8111156101e857600080fd5b8201836020820111156101fa57600080fd5b803590602001918460018302840111600160201b8311171561021b57600080fd5b91908080601f0160208091040260200160405190810160405280939291908181526020018383808284376000920191909152509295506108b4945050505050565b005b61025c6004803603602081101561027457600080fd5b810190602081018135600160201b81111561028e57600080fd5b8201836020820111156102a057600080fd5b803590602001918460018302840111600160201b831117156102c157600080fd5b91908080601f016020809104026020016040519081016040528093929190818152602001838380828437600092019190915250929550610bf9945050505050565b6103a66004803603602081101561031857600080fd5b810190602081018135600160201b81111561033257600080fd5b82018360208201111561034457600080fd5b803590602001918460018302840111600160201b8311171561036557600080fd5b91908080601f016020809104026020016040519081016040528093929190818152602001838380828437600092019190915250929550610ecb945050505050565b6040805160208082528351818301528351919283929083019185019080838360005b838110156103e05781810151838201526020016103c8565b50505050905090810190601f16801561040d5780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b61025c6004803603606081101561043157600080fd5b810190602081018135600160201b81111561044b57600080fd5b82018360208201111561045d57600080fd5b803590602001918460018302840111600160201b8311171561047e57600080fd5b91908080601f0160208091040260200160405190810160405280939291908181526020018383808284376000920191909152509295949360208101935035915050600160201b8111156104d057600080fd5b8201836020820111156104e257600080fd5b803590602001918460018302840111600160201b8311171561050357600080fd5b91908080601f016020809104026020016040519081016040528093929190818152602001838380828437600092019190915250929550505090356001600160a01b0316915061110e9050565b6103a66004803603602081101561056557600080fd5b810190602081018135600160201b81111561057f57600080fd5b82018360208201111561059157600080fd5b803590602001918460018302840111600160201b831117156105b257600080fd5b91908080601f016020809104026020016040519081016040528093929190818152602001838380828437600092019190915250929550611286945050505050565b6105fb611475565b604080516001600160a01b039092168252519081900360200190f35b61025c6004803603602081101561062d57600080fd5b50356114ec565b6103a66004803603602081101561064a57600080fd5b810190602081018135600160201b81111561066457600080fd5b82018360208201111561067657600080fd5b803590602001918460018302840111600160201b8311171561069757600080fd5b91908080601f016020809104026020016040519081016040528093929190818152602001838380828437600092019190915250929550611558945050505050565b61025c600480360360208110156106ee57600080fd5b50356001600160a01b03166115de565b61025c6004803603606081101561071457600080fd5b810190602081018135600160201b81111561072e57600080fd5b82018360208201111561074057600080fd5b803590602001918460018302840111600160201b8311171561076157600080fd5b91908080601f0160208091040260200160405190810160405280939291908181526020018383808284376000920191909152509295949360208101935035915050600160201b8111156107b357600080fd5b8201836020820111156107c557600080fd5b803590602001918460018302840111600160201b831117156107e657600080fd5b91908080601f0160208091040260200160405190810160405280939291908181526020018383808284376000920191909152509295949360208101935035915050600160201b81111561083857600080fd5b82018360208201111561084a57600080fd5b803590602001918460018302840111600160201b8311171561086b57600080fd5b91908080601f0160208091040260200160405190810160405280939291908181526020018383808284376000920191909152509295506116e1945050505050565b6103a6611b9b565b3360009081526008602052604090205460ff16610906576040805162461bcd60e51b8152602060048201526019602482015260008051602061210a833981519152604482015290519081900360640190fd5b816001846040518082805190602001908083835b602083106109395780518252601f19909201916020918201910161091a565b51815160209384036101000a6000190180199092169116179052920194855250604051938490038101909320845161097a9591949190910192509050611ee5565b506007816040518082805190602001908083835b602083106109ad5780518252601f19909201916020918201910161098e565b51815160209384036101000a600019018019909216911617905292019485525060405193849003810184205487516001600160a01b039091169460029450889350918291908401908083835b60208310610a185780518252601f1990920191602091820191016109f9565b51815160209384036101000a6000190180199092169116179052920194855250604051938490038101842080546001600160a01b0319166001600160a01b0396909616959095179094555050825160039260009260079286928291908401908083835b60208310610a9a5780518252601f199092019160209182019101610a7b565b51815160209384036101000a600019018019909216911617905292019485525060408051948590038201909420546001600160a01b031685528481019590955250500160009081208054600181018083559183529183902086519193610b0893919091019190870190611ee5565b5050826040518082805190602001908083835b60208310610b3a5780518252601f199092019160209182019101610b1b565b51815160209384036101000a600019018019909216911617905260405191909301819003812086519095503394506007938793508291908401908083835b60208310610b975780518252601f199092019160209182019101610b78565b51815160209384036101000a600019018019909216911617905292019485525060405193849003018320546001600160a01b0316927f41e5ed7693f15bc2c858b03d462d15c58f9bd8ffa77fe0c7aec8905f63933d5b925060009150a4505050565b3360009081526008602052604090205460ff16610c4b576040805162461bcd60e51b8152602060048201526019602482015260008051602061210a833981519152604482015290519081900360640190fd5b6002816040518082805190602001908083835b60208310610c7d5780518252601f199092019160209182019101610c5e565b51815160209384036101000a60001901801990921691161790529201948552506040519384900301909220546001600160a01b031633149150610cf390505760405162461bcd60e51b81526004018080602001828103825260218152602001806120836021913960400191505060405180910390fd5b336000908152600360205260408120905b8154811015610e42578280519060200120828281548110610d2157fe5b906000526020600020016040518082805460018160011615610100020316600290048015610d865780601f10610d64576101008083540402835291820191610d86565b820191906000526020600020905b815481529060010190602001808311610d72575b505091505060405180910390201415610e3a57805b825460001901811015610dff57828160010181548110610db757fe5b90600052602060002001838281548110610dcd57fe5b906000526020600020019080546001816001161561010002031660029004610df6929190611f63565b50600101610d9b565b50815482906000198101908110610e1257fe5b906000526020600020016000610e289190611fd8565b8154610e3883600019830161201c565b505b600101610d04565b50816040518082805190602001908083835b60208310610e735780518252601f199092019160209182019101610e54565b5181516020939093036101000a60001901801990911692169190911790526040519201829003822093503392507f0da45264d5f650a67a3246c473047872b1a6dd0bd4a57388e61071dc62c8328d9160009150a35050565b3360009081526008602052604090205460609060ff16610f20576040805162461bcd60e51b8152602060048201526019602482015260008051602061210a833981519152604482015290519081900360640190fd5b60006005836040518082805190602001908083835b60208310610f545780518252601f199092019160209182019101610f35565b51815160209384036101000a600019018019909216911617905292019485525060405193849003019092209250600091508190505b8254811015610fc757828181548110610f9e57fe5b6000918252602090912001546001600160a01b0316331415610fbf57600191505b600101610f89565b508061101a576040805162461bcd60e51b815260206004820152601f60248201527f43616c6c657220617265206e6f7420746865207061727469636970616e742100604482015290519081900360640190fd5b6004846040518082805190602001908083835b6020831061104c5780518252601f19909201916020918201910161102d565b518151600019602094850361010090810a820192831692199390931691909117909252949092019687526040805197889003820188208054601f60026001831615909802909501169590950492830182900482028801820190528187529294509250508301828280156111005780601f106110d557610100808354040283529160200191611100565b820191906000526020600020905b8154815290600101906020018083116110e357829003601f168201915b505050505092505050919050565b611116611da1565b6001600160a01b0316336001600160a01b031614611175576040805162461bcd60e51b815260206004820152601760248201527643616c6c65722069736e277420746865206f776e65722160481b604482015290519081900360640190fd5b816006846040518082805190602001908083835b602083106111a85780518252601f199092019160209182019101611189565b51815160209384036101000a600019018019909216911617905292019485525060405193849003810190932084516111e99591949190910192509050611ee5565b50806007846040518082805190602001908083835b6020831061121d5780518252601f1990920191602091820191016111fe565b51815160209384036101000a60001901801990921691161790529201948552506040805194859003820190942080546001600160a01b0319166001600160a01b03968716179055949093166000908152600890945250909120805460ff19166001179055505050565b3360009081526008602052604090205460609060ff166112db576040805162461bcd60e51b8152602060048201526019602482015260008051602061210a833981519152604482015290519081900360640190fd5b6002826040518082805190602001908083835b6020831061130d5780518252601f1990920191602091820191016112ee565b51815160209384036101000a60001901801990921691161790529201948552506040519384900301909220546001600160a01b03163314915061138390505760405162461bcd60e51b81526004018080602001828103825260218152602001806120836021913960400191505060405180910390fd5b6001826040518082805190602001908083835b602083106113b55780518252601f199092019160209182019101611396565b518151600019602094850361010090810a820192831692199390931691909117909252949092019687526040805197889003820188208054601f60026001831615909802909501169590950492830182900482028801820190528187529294509250508301828280156114695780601f1061143e57610100808354040283529160200191611469565b820191906000526020600020905b81548152906001019060200180831161144c57829003601f168201915b50505050509050919050565b600061147f611da1565b6001600160a01b0316336001600160a01b0316146114de576040805162461bcd60e51b815260206004820152601760248201527643616c6c65722069736e277420746865206f776e65722160481b604482015290519081900360640190fd5b6114e6611da1565b90505b90565b6114f4611da1565b6001600160a01b0316336001600160a01b031614611553576040805162461bcd60e51b815260206004820152601760248201527643616c6c65722069736e277420746865206f776e65722160481b604482015290519081900360640190fd5b600055565b3360009081526008602052604090205460609060ff166115ad576040805162461bcd60e51b8152602060048201526019602482015260008051602061210a833981519152604482015290519081900360640190fd5b600682604051808280519060200190808383602083106113b55780518252601f199092019160209182019101611396565b6115e6611da1565b6001600160a01b0316336001600160a01b031614611645576040805162461bcd60e51b815260206004820152601760248201527643616c6c65722069736e277420746865206f776e65722160481b604482015290519081900360640190fd5b6001600160a01b03811661168a5760405162461bcd60e51b815260040180806020018281038252602b8152602001806120a4602b913960400191505060405180910390fd5b7fb532073b38c83145e3e5135377a08bf9aab55bc0fd7c1179cd4fb995d2a5159c6116b3611da1565b604080516001600160a01b03928316815291841660208301528051918290030190a16116de81611dc8565b50565b3360009081526008602052604090205460ff16611733576040805162461bcd60e51b8152602060048201526019602482015260008051602061210a833981519152604482015290519081900360640190fd5b60006004846040518082805190602001908083835b602083106117675780518252601f199092019160209182019101611748565b518151600019602094850361010090810a820192831692199390931691909117909252949092019687526040805197889003820188208054601f600260018316159098029095011695909504928301829004820288018201905281875292965060609594508693509184019050828280156118235780601f106117f857610100808354040283529160200191611823565b820191906000526020600020905b81548152906001019060200180831161180657829003601f168201915b5050505050905060008151111561191b5760006005866040518082805190602001908083835b602083106118685780518252601f199092019160209182019101611849565b51815160209384036101000a600019018019909216911617905292019485525060405193849003019092209250600091508190505b82548110156118db578281815481106118b257fe5b6000918252602090912001546001600160a01b03163314156118d357600191505b60010161189d565b50806119185760405162461bcd60e51b815260040180806020018281038252603b8152602001806120cf603b913960400191505060405180910390fd5b50505b836004866040518082805190602001908083835b6020831061194e5780518252601f19909201916020918201910161192f565b51815160209384036101000a6000190180199092169116179052920194855250604051938490038101909320845161198f9591949190910192509050611ee5565b5060006005866040518082805190602001908083835b602083106119c45780518252601f1990920191602091820191016119a5565b51815160001960209485036101000a019081169019919091161790529201948552506040519384900381018420805460018101825560008281528390200180546001600160a01b031916331790558851909550859460079450899350918291908401908083835b60208310611a4a5780518252601f199092019160209182019101611a2b565b51815160001960209485036101000a01908116901991909116179052920194855250604051938490038101842054855460018101875560009687529582902090950180546001600160a01b0319166001600160a01b03909616959095179094555050875188928291908401908083835b60208310611ad95780518252601f199092019160209182019101611aba565b51815160209384036101000a600019018019909216911617905260405191909301819003812089519095503394506007938a93508291908401908083835b60208310611b365780518252601f199092019160209182019101611b17565b51815160209384036101000a600019018019909216911617905292019485525060405193849003018320546001600160a01b0316927f93cad124cc320903eab153f1621ff9181b36a29d8a3753faa2a6a1aa57f1adda925060009150a4505050505050565b3360009081526008602052604090205460609060ff16611bf0576040805162461bcd60e51b8152602060048201526019602482015260008051602061210a833981519152604482015290519081900360640190fd5b336000908152600360209081526040808320805482518185028101850190935280835260609492939192909184015b82821015611cca5760008481526020908190208301805460408051601f6002600019610100600187161502019094169390930492830185900485028101850190915281815292830182828015611cb65780601f10611c8b57610100808354040283529160200191611cb6565b820191906000526020600020905b815481529060010190602001808311611c9957829003601f168201915b505050505081526020019060010190611c1f565b505050509050604051806020016040528060008152509150600081519050600054811115611cf757506000545b60005b81811015611d9b576000838281518110611d1057fe5b6020026020010151511115611d935780611d3f57828181518110611d3057fe5b60200260200101519350611d93565b6040805180820190915260018152600b60fa1b6020820152611d6890859063ffffffff611dee16565b9350611d90838281518110611d7957fe5b602002602001015185611dee90919063ffffffff16565b93505b600101611cfa565b50505090565b604080516d31b7b69739b434b29737bbb732b960911b8152905190819003600e0190205490565b604080516d31b7b69739b434b29737bbb732b960911b8152905190819003600e01902055565b6060808390506060839050606081518351016040519080825280601f01601f191660200182016040528015611e2a576020820181803883390190505b509050806000805b8551811015611e8357858181518110611e4757fe5b602001015160f81c60f81b838380600101945081518110611e6457fe5b60200101906001600160f81b031916908160001a905350600101611e32565b5060005b8451811015611ed857848181518110611e9c57fe5b602001015160f81c60f81b838380600101945081518110611eb957fe5b60200101906001600160f81b031916908160001a905350600101611e87565b5090979650505050505050565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f10611f2657805160ff1916838001178555611f53565b82800160010185558215611f53579182015b82811115611f53578251825591602001919060010190611f38565b50611f5f929150612045565b5090565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f10611f9c5780548555611f53565b82800160010185558215611f5357600052602060002091601f016020900482015b82811115611f53578254825591600101919060010190611fbd565b50805460018160011615610100020316600290046000825580601f10611ffe57506116de565b601f0160209004906000526020600020908101906116de9190612045565b8154818355818111156120405760008381526020902061204091810190830161205f565b505050565b6114e991905b80821115611f5f576000815560010161204b565b6114e991905b80821115611f5f5760006120798282611fd8565b5060010161206556fe43616c6c657220617265206e6f7420746865206d657373616765206f776e65722143616e6e6f74206368616e676520746865204f776e657220746f20746865207a65726f2061646472657373546865207472616e73616374696f6e20697320657869737465642c2043616c6c657220617265206e6f7420746865207061727469636970616e742143616c6c6572206973206e6f7420726567697374657265642100000000000000a265627a7a723058203a6f1265da26b258170e5650ba0fa1b12ce4c355e55df129c373bceb5476f6fe64736f6c634300050a0032";

    public static final String FUNC_SENDMESSAGE = "sendMessage";

    public static final String FUNC_WITHDRAWPENDINGMESSAGE = "withdrawPendingMessage";

    public static final String FUNC_FINDTRANSACTION = "findTransaction";

    public static final String FUNC_REGISTER = "register";

    public static final String FUNC_FINDMESSAGE = "findMessage";

    public static final String FUNC_OWNER = "owner";

    public static final String FUNC_SETQUERYMSGMAXNUMBER = "setQueryMsgMaxNumber";

    public static final String FUNC_FINDORGPUBKEY = "findOrgPubKey";

    public static final String FUNC_CHANGEOWNER = "changeOwner";

    public static final String FUNC_SENDTRANSACTION = "sendTransaction";

    public static final String FUNC_FINDPENDINGMESSAGESBYOWNER = "findPendingMessagesByOwner";

    public static final Event SENDTRANSACTION_EVENT = new Event("SendTransaction", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}, new TypeReference<Utf8String>(true) {}));
    ;

    public static final Event SENDMESSAGE_EVENT = new Event("SendMessage", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}, new TypeReference<Utf8String>(true) {}));
    ;

    public static final Event WITHDRAWPENDINGMESSAGE_EVENT = new Event("WithdrawPendingMessage", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Utf8String>(true) {}));
    ;

    public static final Event OWNERCHANGED_EVENT = new Event("OwnerChanged", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Address>() {}));
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

    public RemoteCall<TransactionReceipt> setQueryMsgMaxNumber(BigInteger number) {
        final Function function = new Function(
                FUNC_SETQUERYMSGMAXNUMBER, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(number)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<String> findOrgPubKey(String orgCode) {
        final Function function = new Function(FUNC_FINDORGPUBKEY, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(orgCode)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<TransactionReceipt> changeOwner(String newOwner) {
        final Function function = new Function(
                FUNC_CHANGEOWNER, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(newOwner)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
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

    public static class OwnerChangedEventResponse {
        public Log log;

        public String previousOwner;

        public String newOwner;
    }
}
