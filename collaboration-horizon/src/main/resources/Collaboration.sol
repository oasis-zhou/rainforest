pragma solidity ^0.5.0;

import './UpgradeabilityProxy.sol';

contract CollaborationBase {

    uint internal maxMessageNumber = 100;
    // Storage position of the owner of the contract
    bytes32 internal constant OWNER_SLOT = keccak256("com.shie.owner");

    mapping(string => string) internal _messages;
    mapping(string => address) internal _messageToOwner;
    mapping(address => string[]) internal _pendingMessages;

    mapping(string => string) internal _transactions;
    mapping(string => address[]) internal _ownershipTransactions;

    mapping(string => string) internal _orgPubKey;
    mapping(string => address) internal _orgAddress;
    mapping(address => bool) internal _organization;

    event OwnerChanged(address previousOwner, address newOwner);

    modifier onlyOwner() {
        require(msg.sender == _owner(),"Caller isn't the owner!");
        _;
    }

    modifier onlyRegistration() {
        require(_organization[msg.sender], "Caller is not registered!");
        _;
    }
    /**
     * @dev constructor function.
     */
    constructor() public {
        _setOwner(msg.sender);
    }

    /**
    * @return The address of the owner.
    */
    function owner() public view onlyOwner returns (address) {
        return _owner();
    }

    /**
   * @dev Changes the owner.
   * Only the current owner can call this function.
   * @param newOwner Address to transfer proxy owneristration to.
   */
  function changeOwner(address newOwner) public onlyOwner {
    require(newOwner != address(0), "Cannot change the Owner to the zero address");
    emit OwnerChanged(_owner(), newOwner);
    _setOwner(newOwner);
  }

    /**
    * @return The owner slot.
    */
    function _owner() internal view returns (address ownerAddress) {
        bytes32 slot = OWNER_SLOT;
        assembly {
            ownerAddress := sload(slot)
        }
    }

    /**
    * @dev Sets the address of the owner.
    * @param newOwner Address of the new owner.
    */
    function _setOwner(address newOwner) internal {
        bytes32 slot = OWNER_SLOT;
        assembly {
            sstore(slot, newOwner)
        }
    }

    /**
     * @dev Set the query pending message max number.
     * @param number max number.
     */
    function setQueryMsgMaxNumber(uint number) public onlyOwner {
        maxMessageNumber = number;
    }

    /**
     * @dev Register the organization, only contract owner can call this function.
     * @param orgCode organization code.
     * @param pubKey organization RSA public key.
     * @param orgAddress organization account address.
     */
    function register(string memory orgCode, string memory pubKey, address orgAddress) public onlyOwner {
        _orgPubKey[orgCode] = pubKey;
        _orgAddress[orgCode] = orgAddress;
        _organization[orgAddress] = true;
    }

    /**
     * @dev Export the transaction data, only contract owner can call this function.
     * @param transactionNumber the business transaction number.
     */
    function exportTransaction(string memory transactionNumber) public view onlyOwner returns (string memory transaction) {
        transaction = _transactions[transactionNumber];
    }

    /**
     * @dev Export the message data, only contract owner can call this function.
     * @param msgID message id.
     */
    function exportMessage(string memory msgID) public view onlyOwner returns (string memory message) {
        message = _messages[msgID];
    }
}

contract Collaboration is CollaborationBase{
    using String for string;

    event SendTransaction(address indexed owner, address indexed sender, string indexed transactionNumber);
    event SendMessage(address indexed owner, address indexed sender, string indexed msgID);
    event WithdrawPendingMessage(address indexed owner, string indexed msgID);

    function findOrgPubKey(string memory orgCode) public view onlyRegistration returns (string memory pubKey) {
        pubKey = _orgPubKey[orgCode];
    }

    function sendTransaction(string memory transactionNumber, string memory transaction, string memory receiver) public onlyRegistration {
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
            if(!isParticipant)
                revert("The transaction is existed, Caller are not the participant!");
        }

        _transactions[transactionNumber] = transaction;
        address[] storage participants = _ownershipTransactions[transactionNumber];
        participants.push(msg.sender);
        participants.push(_orgAddress[receiver]);

        emit SendTransaction(_orgAddress[receiver], msg.sender, transactionNumber);
    }

    function findTransaction(string memory transactionNumber) public view onlyRegistration returns (string memory transaction) {
        address[] storage participants = _ownershipTransactions[transactionNumber];
        bool isParticipant = false;
        for(uint i = 0; i < participants.length; i++){
            if(msg.sender == participants[i]) {
                isParticipant = true;
            }
        }
        if(!isParticipant)
            revert("Caller are not the participant!");

        transaction = _transactions[transactionNumber];
    }

    function sendMessage(string memory msgID, string memory message, string memory owner) public onlyRegistration {
        _messages[msgID] = message;
        _messageToOwner[msgID] = _orgAddress[owner];
        _pendingMessages[_orgAddress[owner]].push(msgID);

        emit SendMessage(_orgAddress[owner], msg.sender, msgID);
    }

    function findMessage(string memory msgID) public view onlyRegistration returns (string memory message) {
        require (msg.sender == _messageToOwner[msgID], "Caller are not the message owner!");
        message = _messages[msgID];
    }

    function findPendingMessagesByOwner() public view onlyRegistration returns (string memory msgIDs) {
        string[] memory msgs = _pendingMessages[msg.sender];
        msgIDs = "";
        uint length = msgs.length;
        if (length > maxMessageNumber) {
            length = maxMessageNumber;
        }
        for (uint i = 0; i < length; i++) {
            if(bytes(msgs[i]).length > 0) {
                if( i == 0) {
                    msgIDs = msgs[i];
                } else {
                    msgIDs = msgIDs.append(",");
                    msgIDs = msgIDs.append(msgs[i]);
                }
            }
        }
    }

    function withdrawPendingMessage(string memory msgID) public onlyRegistration {
        require (msg.sender == _messageToOwner[msgID], "Caller are not the message owner!");

        string[] storage msgs = _pendingMessages[msg.sender];
        for (uint i = 0; i < msgs.length; i++) {
            if (keccak256(bytes(msgs[i])) == keccak256(bytes(msgID))) {
                // 删除待处理消息，同时释放空间，可能存在和并发写入的冲突问题
                for (uint j = i; j < msgs.length-1; j++){
                    msgs[j] = msgs[j+1];
                }
                delete msgs[msgs.length - 1];
                msgs.length--;
                // delete msgs[i];
            }
        }

        emit WithdrawPendingMessage(msg.sender, msgID);
    }
}


contract CollaborationProxy is UpgradeabilityProxy,CollaborationBase {

  /**
   * @return The address of the implementation.
   */
  function implementation() external view onlyOwner returns (address) {
    return _implementation();
  }

  /**
   * @dev Upgrade the backing implementation of the proxy.
   * Only the Owner can call this function.
   * @param newImplementation Address of the new implementation.
   */
  function upgradeTo(address newImplementation) external onlyOwner {
    _upgradeTo(newImplementation);
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