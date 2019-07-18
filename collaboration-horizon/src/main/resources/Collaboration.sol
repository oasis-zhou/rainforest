pragma solidity ^0.5.0;


contract Ownable {
    address private _owner;

    event OwnershipTransferred(address indexed previousOwner, address indexed newOwner);

    /**
     * @dev Initializes the contract setting the deployer as the initial owner.
     */
    constructor () internal {
        _owner = msg.sender;
        emit OwnershipTransferred(address(0), _owner);
    }

    /**
     * @dev Returns the address of the current owner.
     */
    function owner() public view returns (address) {
        return _owner;
    }

    /**
     * @dev Throws if called by any account other than the owner.
     */
    modifier onlyOwner() {
        require(isOwner(), "Ownable: caller is not the owner");
        _;
    }

    /**
     * @dev Returns true if the caller is the current owner.
     */
    function isOwner() public view returns (bool) {
        return msg.sender == _owner;
    }

    /**
     * @dev Transfers ownership of the contract to a new account (`newOwner`).
     * Can only be called by the current owner.
     */
    function transferOwnership(address newOwner) public onlyOwner {
        _transferOwnership(newOwner);
    }

    /**
     * @dev Transfers ownership of the contract to a new account (`newOwner`).
     */
    function _transferOwnership(address newOwner) internal {
        require(newOwner != address(0), "Ownable: new owner is the zero address");
        emit OwnershipTransferred(_owner, newOwner);
        _owner = newOwner;
    }
}

contract Collaboration is Ownable {
    using String for string;

    mapping(string => string) private _messages;
    mapping(string => address) private _messageToOwner;
    mapping(address => string[]) private _ownershipPendingMessages;

    mapping(string => string) private _transactions;
    mapping(string => address[]) private _ownershipTransactions;

    mapping(string => string) private _orgPubKey;
    mapping(string => address) private _orgAddress;

    event SendTransaction(address indexed sender, address indexed participant, string indexed transactionNumber);
    event SendMessage(address indexed sender, address indexed owner, string indexed msgID);
    event WithdrawPendingMessage(address indexed owner, string indexed msgID);

    function register(string memory orgCode, string memory pubKey, address orgAddress) public onlyOwner {
        _orgPubKey[orgCode] = pubKey;
        _orgAddress[orgCode] = orgAddress;
    }

    function sendTransaction(string memory transactionNumber, string memory transaction, string memory participant) public {
        string storage tmpTransaction = _transactions[transactionNumber];
        bytes memory transactionByte = bytes(tmpTransaction);
        if(transactionByte.length > 0 ) {
            address[] storage participants = _ownershipTransactions[transactionNumber];
            bool isParticipant = false;
            for(uint i = 0; i < participants.length; i++){
                if(msg.sender == participants[i]) {
                    isParticipant = true;
                }
            }
            require(isParticipant,"The transaction is existed, Caller are not the participant!");
        }
       
        _transactions[transactionNumber] = transaction;
        address[] storage participants = _ownershipTransactions[transactionNumber];
        participants.push(msg.sender);
        participants.push(_orgAddress[participant]);

        emit SendTransaction(msg.sender, _orgAddress[participant], transactionNumber);
    }

    function findTransaction(string memory transactionNumber) public view returns (string memory transaction) {
        address[] storage participants = _ownershipTransactions[transactionNumber];
        bool isParticipant = false;
        for(uint i = 0; i < participants.length; i++){
            if(msg.sender == participants[i]) {
                isParticipant = true;
            }
        }
        require(isParticipant,"Caller are not the participant!");

        transaction = _transactions[transactionNumber];
    }

    function sendMessage(string memory msgID, string memory message, string memory owner) public {
        _messages[msgID] = message;
        _messageToOwner[msgID] = _orgAddress[owner];
        string[] storage msgs = _ownershipPendingMessages[_orgAddress[owner]];
        msgs.push(msgID);

        emit SendMessage(msg.sender, _orgAddress[owner], msgID);
    }

    function findMessage(string memory msgID) public view returns (string memory message) {
        require (msg.sender == _messageToOwner[msgID], "Caller are not the message owner!");
        message = _messages[msgID];
    }

    function findPendingMessagesByOwner() public view returns (string memory msgIDs) {
        string[] memory msgs = _ownershipPendingMessages[msg.sender];
        msgIDs = "";
        for (uint i = 0; i < msgs.length; i++) {
            if( i == 0) {
                msgIDs = msgs[i];
            } else {
                msgIDs = msgIDs.append(",");
                msgIDs = msgIDs.append(msgs[i]);
            }
        }
    }

    function withdrawPendingMessage(string memory msgID) public {

        require (msg.sender == _messageToOwner[msgID], "Caller are not the message owner!");

        string[] storage msgs = _ownershipPendingMessages[msg.sender];
        for (uint i = 0; i < msgs.length; i++) {
            if (keccak256(bytes(msgs[i])) == keccak256(bytes(msgID))) {
                for (uint j = i; j < msgs.length-1; j++){
                    msgs[j] = msgs[j+1];
                }
                delete msgs[msgs.length - 1];
                msgs.length--;
            }
        }

        emit WithdrawPendingMessage(msg.sender, msgID);
    }
}


library String {

    function append(string memory self, string memory str) internal pure returns (string memory) {
        bytes memory selfByte = bytes(self);
        bytes memory strByte = bytes(str);
        
        string memory newStr = new string(selfByte.length + strByte.length);
        bytes memory newStrByte = bytes(newStr);
        uint n = 0;
        for (uint i = 0; i < selfByte.length; i++) {
            newStrByte[n++] = selfByte[i];
        }
        for (uint i = 0; i < strByte.length; i++) {
            newStrByte[n++] = strByte[i];
        }
        
        return string(newStrByte);
    }

}
    