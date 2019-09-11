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
    private static final String BINARY = "6080604052606460005561001b336001600160e01b0361002016565b610055565b604080517f636f6d2e736869652e6f776e65720000000000000000000000000000000000008152905190819003600e01902055565b6123a7806100646000396000f3fe608060405234801561001057600080fd5b50600436106100cf5760003560e01c80638da5cb5b1161008c578063a6f9dae111610066578063a6f9dae1146107a2578063ac30b865146107c8578063d5d362c414610976578063fb7c6bf61461097e576100cf565b80638da5cb5b146106bd578063988a23a0146106e15780639ac31bf8146106fe576100cf565b80630eabeffe146100d4578063150be8cb146102845780634c020730146103285780635664d69c14610441578063603bebcd1461057557806369a3ddc014610619575b600080fd5b610282600480360360608110156100ea57600080fd5b810190602081018135600160201b81111561010457600080fd5b82018360208201111561011657600080fd5b803590602001918460018302840111600160201b8311171561013757600080fd5b91908080601f0160208091040260200160405190810160405280939291908181526020018383808284376000920191909152509295949360208101935035915050600160201b81111561018957600080fd5b82018360208201111561019b57600080fd5b803590602001918460018302840111600160201b831117156101bc57600080fd5b91908080601f0160208091040260200160405190810160405280939291908181526020018383808284376000920191909152509295949360208101935035915050600160201b81111561020e57600080fd5b82018360208201111561022057600080fd5b803590602001918460018302840111600160201b8311171561024157600080fd5b91908080601f016020809104026020016040519081016040528093929190818152602001838380828437600092019190915250929550610a22945050505050565b005b6102826004803603602081101561029a57600080fd5b810190602081018135600160201b8111156102b457600080fd5b8201836020820111156102c657600080fd5b803590602001918460018302840111600160201b831117156102e757600080fd5b91908080601f016020809104026020016040519081016040528093929190818152602001838380828437600092019190915250929550610d67945050505050565b6103cc6004803603602081101561033e57600080fd5b810190602081018135600160201b81111561035857600080fd5b82018360208201111561036a57600080fd5b803590602001918460018302840111600160201b8311171561038b57600080fd5b91908080601f016020809104026020016040519081016040528093929190818152602001838380828437600092019190915250929550611039945050505050565b6040805160208082528351818301528351919283929083019185019080838360005b838110156104065781810151838201526020016103ee565b50505050905090810190601f1680156104335780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b6102826004803603606081101561045757600080fd5b810190602081018135600160201b81111561047157600080fd5b82018360208201111561048357600080fd5b803590602001918460018302840111600160201b831117156104a457600080fd5b91908080601f0160208091040260200160405190810160405280939291908181526020018383808284376000920191909152509295949360208101935035915050600160201b8111156104f657600080fd5b82018360208201111561050857600080fd5b803590602001918460018302840111600160201b8311171561052957600080fd5b91908080601f016020809104026020016040519081016040528093929190818152602001838380828437600092019190915250929550505090356001600160a01b0316915061127c9050565b6103cc6004803603602081101561058b57600080fd5b810190602081018135600160201b8111156105a557600080fd5b8201836020820111156105b757600080fd5b803590602001918460018302840111600160201b831117156105d857600080fd5b91908080601f0160208091040260200160405190810160405280939291908181526020018383808284376000920191909152509295506113e8945050505050565b6103cc6004803603602081101561062f57600080fd5b810190602081018135600160201b81111561064957600080fd5b82018360208201111561065b57600080fd5b803590602001918460018302840111600160201b8311171561067c57600080fd5b91908080601f016020809104026020016040519081016040528093929190818152602001838380828437600092019190915250929550611537945050505050565b6106c5611634565b604080516001600160a01b039092168252519081900360200190f35b610282600480360360208110156106f757600080fd5b503561169f565b6103cc6004803603602081101561071457600080fd5b810190602081018135600160201b81111561072e57600080fd5b82018360208201111561074057600080fd5b803590602001918460018302840111600160201b8311171561076157600080fd5b91908080601f0160208091040260200160405190810160405280939291908181526020018383808284376000920191909152509295506116ff945050505050565b610282600480360360208110156107b857600080fd5b50356001600160a01b0316611785565b610282600480360360608110156107de57600080fd5b810190602081018135600160201b8111156107f857600080fd5b82018360208201111561080a57600080fd5b803590602001918460018302840111600160201b8311171561082b57600080fd5b91908080601f0160208091040260200160405190810160405280939291908181526020018383808284376000920191909152509295949360208101935035915050600160201b81111561087d57600080fd5b82018360208201111561088f57600080fd5b803590602001918460018302840111600160201b831117156108b057600080fd5b91908080601f0160208091040260200160405190810160405280939291908181526020018383808284376000920191909152509295949360208101935035915050600160201b81111561090257600080fd5b82018360208201111561091457600080fd5b803590602001918460018302840111600160201b8311171561093557600080fd5b91908080601f01602080910402602001604051908101604052809392919081815260200183838082843760009201919091525092955061187c945050505050565b6103cc611d36565b6103cc6004803603602081101561099457600080fd5b810190602081018135600160201b8111156109ae57600080fd5b8201836020820111156109c057600080fd5b803590602001918460018302840111600160201b831117156109e157600080fd5b91908080601f016020809104026020016040519081016040528093929190818152602001838380828437600092019190915250929550611f3c945050505050565b3360009081526008602052604090205460ff16610a74576040805162461bcd60e51b81526020600482015260196024820152600080516020612353833981519152604482015290519081900360640190fd5b816001846040518082805190602001908083835b60208310610aa75780518252601f199092019160209182019101610a88565b51815160209384036101000a60001901801990921691161790529201948552506040519384900381019093208451610ae8959194919091019250905061210e565b506007816040518082805190602001908083835b60208310610b1b5780518252601f199092019160209182019101610afc565b51815160209384036101000a600019018019909216911617905292019485525060405193849003810184205487516001600160a01b039091169460029450889350918291908401908083835b60208310610b865780518252601f199092019160209182019101610b67565b51815160209384036101000a6000190180199092169116179052920194855250604051938490038101842080546001600160a01b0319166001600160a01b0396909616959095179094555050825160039260009260079286928291908401908083835b60208310610c085780518252601f199092019160209182019101610be9565b51815160209384036101000a600019018019909216911617905292019485525060408051948590038201909420546001600160a01b031685528481019590955250500160009081208054600181018083559183529183902086519193610c769391909101919087019061210e565b5050826040518082805190602001908083835b60208310610ca85780518252601f199092019160209182019101610c89565b51815160209384036101000a600019018019909216911617905260405191909301819003812086519095503394506007938793508291908401908083835b60208310610d055780518252601f199092019160209182019101610ce6565b51815160209384036101000a600019018019909216911617905292019485525060405193849003018320546001600160a01b0316927f41e5ed7693f15bc2c858b03d462d15c58f9bd8ffa77fe0c7aec8905f63933d5b925060009150a4505050565b3360009081526008602052604090205460ff16610db9576040805162461bcd60e51b81526020600482015260196024820152600080516020612353833981519152604482015290519081900360640190fd5b6002816040518082805190602001908083835b60208310610deb5780518252601f199092019160209182019101610dcc565b51815160209384036101000a60001901801990921691161790529201948552506040519384900301909220546001600160a01b031633149150610e6190505760405162461bcd60e51b81526004018080602001828103825260218152602001806122ac6021913960400191505060405180910390fd5b336000908152600360205260408120905b8154811015610fb0578280519060200120828281548110610e8f57fe5b906000526020600020016040518082805460018160011615610100020316600290048015610ef45780601f10610ed2576101008083540402835291820191610ef4565b820191906000526020600020905b815481529060010190602001808311610ee0575b505091505060405180910390201415610fa857805b825460001901811015610f6d57828160010181548110610f2557fe5b90600052602060002001838281548110610f3b57fe5b906000526020600020019080546001816001161561010002031660029004610f6492919061218c565b50600101610f09565b50815482906000198101908110610f8057fe5b906000526020600020016000610f969190612201565b8154610fa6836000198301612245565b505b600101610e72565b50816040518082805190602001908083835b60208310610fe15780518252601f199092019160209182019101610fc2565b5181516020939093036101000a60001901801990911692169190911790526040519201829003822093503392507f0da45264d5f650a67a3246c473047872b1a6dd0bd4a57388e61071dc62c8328d9160009150a35050565b3360009081526008602052604090205460609060ff1661108e576040805162461bcd60e51b81526020600482015260196024820152600080516020612353833981519152604482015290519081900360640190fd5b60006005836040518082805190602001908083835b602083106110c25780518252601f1990920191602091820191016110a3565b51815160209384036101000a600019018019909216911617905292019485525060405193849003019092209250600091508190505b82548110156111355782818154811061110c57fe5b6000918252602090912001546001600160a01b031633141561112d57600191505b6001016110f7565b5080611188576040805162461bcd60e51b815260206004820152601f60248201527f43616c6c657220617265206e6f7420746865207061727469636970616e742100604482015290519081900360640190fd5b6004846040518082805190602001908083835b602083106111ba5780518252601f19909201916020918201910161119b565b518151600019602094850361010090810a820192831692199390931691909117909252949092019687526040805197889003820188208054601f600260018316159098029095011695909504928301829004820288018201905281875292945092505083018282801561126e5780601f106112435761010080835404028352916020019161126e565b820191906000526020600020905b81548152906001019060200180831161125157829003601f168201915b505050505092505050919050565b611284611fca565b6001600160a01b0316336001600160a01b0316146112d7576040805162461bcd60e51b81526020600482015260176024820152600080516020612333833981519152604482015290519081900360640190fd5b816006846040518082805190602001908083835b6020831061130a5780518252601f1990920191602091820191016112eb565b51815160209384036101000a6000190180199092169116179052920194855250604051938490038101909320845161134b959194919091019250905061210e565b50806007846040518082805190602001908083835b6020831061137f5780518252601f199092019160209182019101611360565b51815160209384036101000a60001901801990921691161790529201948552506040805194859003820190942080546001600160a01b0319166001600160a01b03968716179055949093166000908152600890945250909120805460ff19166001179055505050565b60606113f2611fca565b6001600160a01b0316336001600160a01b031614611445576040805162461bcd60e51b81526020600482015260176024820152600080516020612333833981519152604482015290519081900360640190fd5b6001826040518082805190602001908083835b602083106114775780518252601f199092019160209182019101611458565b518151600019602094850361010090810a820192831692199390931691909117909252949092019687526040805197889003820188208054601f600260018316159098029095011695909504928301829004820288018201905281875292945092505083018282801561152b5780601f106115005761010080835404028352916020019161152b565b820191906000526020600020905b81548152906001019060200180831161150e57829003601f168201915b50505050509050919050565b3360009081526008602052604090205460609060ff1661158c576040805162461bcd60e51b81526020600482015260196024820152600080516020612353833981519152604482015290519081900360640190fd5b6002826040518082805190602001908083835b602083106115be5780518252601f19909201916020918201910161159f565b51815160209384036101000a60001901801990921691161790529201948552506040519384900301909220546001600160a01b03163314915061144590505760405162461bcd60e51b81526004018080602001828103825260218152602001806122ac6021913960400191505060405180910390fd5b600061163e611fca565b6001600160a01b0316336001600160a01b031614611691576040805162461bcd60e51b81526020600482015260176024820152600080516020612333833981519152604482015290519081900360640190fd5b611699611fca565b90505b90565b6116a7611fca565b6001600160a01b0316336001600160a01b0316146116fa576040805162461bcd60e51b81526020600482015260176024820152600080516020612333833981519152604482015290519081900360640190fd5b600055565b3360009081526008602052604090205460609060ff16611754576040805162461bcd60e51b81526020600482015260196024820152600080516020612353833981519152604482015290519081900360640190fd5b600682604051808280519060200190808383602083106114775780518252601f199092019160209182019101611458565b61178d611fca565b6001600160a01b0316336001600160a01b0316146117e0576040805162461bcd60e51b81526020600482015260176024820152600080516020612333833981519152604482015290519081900360640190fd5b6001600160a01b0381166118255760405162461bcd60e51b815260040180806020018281038252602b8152602001806122cd602b913960400191505060405180910390fd5b7fb532073b38c83145e3e5135377a08bf9aab55bc0fd7c1179cd4fb995d2a5159c61184e611fca565b604080516001600160a01b03928316815291841660208301528051918290030190a161187981611ff1565b50565b3360009081526008602052604090205460ff166118ce576040805162461bcd60e51b81526020600482015260196024820152600080516020612353833981519152604482015290519081900360640190fd5b60006004846040518082805190602001908083835b602083106119025780518252601f1990920191602091820191016118e3565b518151600019602094850361010090810a820192831692199390931691909117909252949092019687526040805197889003820188208054601f600260018316159098029095011695909504928301829004820288018201905281875292965060609594508693509184019050828280156119be5780601f10611993576101008083540402835291602001916119be565b820191906000526020600020905b8154815290600101906020018083116119a157829003601f168201915b50505050509050600081511115611ab65760006005866040518082805190602001908083835b60208310611a035780518252601f1990920191602091820191016119e4565b51815160209384036101000a600019018019909216911617905292019485525060405193849003019092209250600091508190505b8254811015611a7657828181548110611a4d57fe5b6000918252602090912001546001600160a01b0316331415611a6e57600191505b600101611a38565b5080611ab35760405162461bcd60e51b815260040180806020018281038252603b8152602001806122f8603b913960400191505060405180910390fd5b50505b836004866040518082805190602001908083835b60208310611ae95780518252601f199092019160209182019101611aca565b51815160209384036101000a60001901801990921691161790529201948552506040519384900381019093208451611b2a959194919091019250905061210e565b5060006005866040518082805190602001908083835b60208310611b5f5780518252601f199092019160209182019101611b40565b51815160001960209485036101000a019081169019919091161790529201948552506040519384900381018420805460018101825560008281528390200180546001600160a01b031916331790558851909550859460079450899350918291908401908083835b60208310611be55780518252601f199092019160209182019101611bc6565b51815160001960209485036101000a01908116901991909116179052920194855250604051938490038101842054855460018101875560009687529582902090950180546001600160a01b0319166001600160a01b03909616959095179094555050875188928291908401908083835b60208310611c745780518252601f199092019160209182019101611c55565b51815160209384036101000a600019018019909216911617905260405191909301819003812089519095503394506007938a93508291908401908083835b60208310611cd15780518252601f199092019160209182019101611cb2565b51815160209384036101000a600019018019909216911617905292019485525060405193849003018320546001600160a01b0316927f93cad124cc320903eab153f1621ff9181b36a29d8a3753faa2a6a1aa57f1adda925060009150a4505050505050565b3360009081526008602052604090205460609060ff16611d8b576040805162461bcd60e51b81526020600482015260196024820152600080516020612353833981519152604482015290519081900360640190fd5b336000908152600360209081526040808320805482518185028101850190935280835260609492939192909184015b82821015611e655760008481526020908190208301805460408051601f6002600019610100600187161502019094169390930492830185900485028101850190915281815292830182828015611e515780601f10611e2657610100808354040283529160200191611e51565b820191906000526020600020905b815481529060010190602001808311611e3457829003601f168201915b505050505081526020019060010190611dba565b505050509050604051806020016040528060008152509150600081519050600054811115611e9257506000545b60005b81811015611f36576000838281518110611eab57fe5b6020026020010151511115611f2e5780611eda57828181518110611ecb57fe5b60200260200101519350611f2e565b6040805180820190915260018152600b60fa1b6020820152611f0390859063ffffffff61201716565b9350611f2b838281518110611f1457fe5b60200260200101518561201790919063ffffffff16565b93505b600101611e95565b50505090565b6060611f46611fca565b6001600160a01b0316336001600160a01b031614611f99576040805162461bcd60e51b81526020600482015260176024820152600080516020612333833981519152604482015290519081900360640190fd5b600482604051808280519060200190808383602083106114775780518252601f199092019160209182019101611458565b604080516d31b7b69739b434b29737bbb732b960911b8152905190819003600e0190205490565b604080516d31b7b69739b434b29737bbb732b960911b8152905190819003600e01902055565b6060808390506060839050606081518351016040519080825280601f01601f191660200182016040528015612053576020820181803883390190505b509050806000805b85518110156120ac5785818151811061207057fe5b602001015160f81c60f81b83838060010194508151811061208d57fe5b60200101906001600160f81b031916908160001a90535060010161205b565b5060005b8451811015612101578481815181106120c557fe5b602001015160f81c60f81b8383806001019450815181106120e257fe5b60200101906001600160f81b031916908160001a9053506001016120b0565b5090979650505050505050565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f1061214f57805160ff191683800117855561217c565b8280016001018555821561217c579182015b8281111561217c578251825591602001919060010190612161565b5061218892915061226e565b5090565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f106121c5578054855561217c565b8280016001018555821561217c57600052602060002091601f016020900482015b8281111561217c5782548255916001019190600101906121e6565b50805460018160011615610100020316600290046000825580601f106122275750611879565b601f016020900490600052602060002090810190611879919061226e565b81548183558181111561226957600083815260209020612269918101908301612288565b505050565b61169c91905b808211156121885760008155600101612274565b61169c91905b808211156121885760006122a28282612201565b5060010161228e56fe43616c6c657220617265206e6f7420746865206d657373616765206f776e65722143616e6e6f74206368616e676520746865204f776e657220746f20746865207a65726f2061646472657373546865207472616e73616374696f6e20697320657869737465642c2043616c6c657220617265206e6f7420746865207061727469636970616e742143616c6c65722069736e277420746865206f776e65722100000000000000000043616c6c6572206973206e6f7420726567697374657265642100000000000000a265627a7a72305820160d26600cebc2ca0e3aef4b401eee975a5b560376d31d687c3a8dedbc4399f064736f6c634300050a0032";

    public static final String FUNC_SENDMESSAGE = "sendMessage";

    public static final String FUNC_WITHDRAWPENDINGMESSAGE = "withdrawPendingMessage";

    public static final String FUNC_FINDTRANSACTION = "findTransaction";

    public static final String FUNC_REGISTER = "register";

    public static final String FUNC_EXPORTMESSAGE = "exportMessage";

    public static final String FUNC_FINDMESSAGE = "findMessage";

    public static final String FUNC_OWNER = "owner";

    public static final String FUNC_SETQUERYMSGMAXNUMBER = "setQueryMsgMaxNumber";

    public static final String FUNC_FINDORGPUBKEY = "findOrgPubKey";

    public static final String FUNC_CHANGEOWNER = "changeOwner";

    public static final String FUNC_SENDTRANSACTION = "sendTransaction";

    public static final String FUNC_FINDPENDINGMESSAGESBYOWNER = "findPendingMessagesByOwner";

    public static final String FUNC_EXPORTTRANSACTION = "exportTransaction";

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

    public RemoteCall<String> exportMessage(String msgID) {
        final Function function = new Function(FUNC_EXPORTMESSAGE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(msgID)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
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

    public RemoteCall<String> exportTransaction(String transactionNumber) {
        final Function function = new Function(FUNC_EXPORTTRANSACTION, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(transactionNumber)), 
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
