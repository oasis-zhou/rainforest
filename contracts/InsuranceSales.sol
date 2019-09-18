pragma solidity ^0.5.0;

import './UpgradeabilityProxy.sol';

contract InsuranceSalesBase {

    uint internal maxQueryNumber = 100;
    // Storage position of the owner of the contract
    bytes32 internal constant OWNER_SLOT = keccak256("contract.owner");

    mapping(string => string) internal _orgPubKey;
    mapping(string => address) internal _orgAddress;
    mapping(address => bool) internal _organization;

    mapping(string => string) internal _products;
    mapping(string => address) internal _productToOwner;

    mapping(string => string) internal _policies;
    mapping(string => address[]) internal _policyToOwner;
    mapping(address => string[]) internal _pendingPolicies;

    mapping(string => string) internal _endorsements;
    mapping(string => address[]) internal _endorsementToOwner;
    mapping(address => string[]) internal _pendingEndorsements;

    mapping(address => string[]) internal _salesAgreements;

    event OwnerChanged(address previousOwner, address newOwner);
    event RegisterOrg(address indexed owner, address indexed orgAddress);

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
    function setMaxQueryNumber(uint number) public onlyOwner {
        maxQueryNumber = number;
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

        emit RegisterOrg(_owner(), orgAddress);
    }
}

contract InsuranceSales is InsuranceSalesBase {
    using String for string;

    event ReleaseProduct(address indexed owner, string indexed productCode);
    event IssuePolicy(address indexed owner, address indexed issuer, string indexed policyNumber);
    event WithdrawPendingPolicy(address indexed owner, string indexed policyNumber);
    event IssueEndorsement(address indexed owner, address indexed issuer, string indexed endorsementNumber);
    event WithdrawPendingEndorsement(address indexed owner, string indexed endorsementNumber);
    event ApproveSalesAgreement(address indexed owner, address indexed approver, string indexed productCode);

    function findOrgAddress(string memory orgCode) public view returns (address orgAddress) {
        orgAddress = _orgAddress[orgCode];
    }

    function findOrgPubKey(string memory orgCode) public view onlyRegistration returns (string memory pubKey) {
        pubKey = _orgPubKey[orgCode];
    }

    function releaseProduct(string memory productCode, string memory productSpec) public onlyRegistration {
        _products[productCode] = productSpec;
        _productToOwner[productCode] = msg.sender;

        emit ReleaseProduct(msg.sender,productCode);
    }

    function findProduct(string memory productCode) public view onlyRegistration returns (string memory productSpec) {
        require(msg.sender == _productToOwner[productCode],"Caller is not the product owner!");

        productSpec = _products[productCode];
    }

    function findProductFromChannel(string memory productCode) public view onlyRegistration returns (string memory productSpec) {
        bool salesAuth = checkSalesAuth(msg.sender,productCode);
        if (!salesAuth)
            revert("Caller don't have the sales agreement for this product!");

        productSpec = _products[productCode];
    }

    function approveAgreement(string memory orgCode, string memory productCode) public onlyRegistration {
        require(msg.sender == _productToOwner[productCode],"Caller is not the product owner!");

        _salesAgreements[_orgAddress[orgCode]].push(productCode);

        emit ApproveSalesAgreement(_orgAddress[orgCode],msg.sender,productCode);
    }

    function withdrawAgreement(string memory orgCode, string memory productCode) public onlyRegistration {
        require(msg.sender == _productToOwner[productCode],"Caller is not the product owner!");

        string[] storage products = _salesAgreements[_orgAddress[orgCode]];
        for (uint i = 0; i < products.length; i++) {
            if (keccak256(bytes(products[i])) == keccak256(bytes(productCode))) {
                for (uint j = i; j < products.length-1; j++){
                    products[j] = products[j+1];
                }
                delete products[products.length - 1];
                products.length--;
            }
        }
    }

    function issuePolicy(string memory policyNumber, string memory productCode, string memory policy) public onlyRegistration {
        bool salesAuth = checkSalesAuth(msg.sender,productCode);
        require(salesAuth,"Caller don't have the sales agreement for this product!");

        address owner = _productToOwner[productCode];
        _policies[policyNumber] = policy;
        _policyToOwner[policyNumber].push(owner);
        _policyToOwner[policyNumber].push(msg.sender);
        _pendingPolicies[owner].push(policyNumber);

        emit IssuePolicy(owner,msg.sender,policyNumber);
    }

    function findPolicy(string memory policyNumber) public view onlyRegistration returns (string memory policy)  {
        address[] memory owners = _policyToOwner[policyNumber];
        bool isOwner = false;
        for (uint i = 0; i < owners.length; i++) {
            if (msg.sender == owners[i])
                isOwner = true;
        }
        if (!isOwner)
            revert("Caller are not the policy owner!");

        policy = _policies[policyNumber];
    }

    function findPendingPolicies() public view onlyRegistration returns (string memory policies) {
        string[] memory policyNumbers = _pendingPolicies[msg.sender];
        policies = "";
        for (uint i = 0; i < policyNumbers.length; i++) {
            if(bytes(policyNumbers[i]).length > 0) {
                if( i == 0) {
                    policies = policyNumbers[i];
                } else {
                    policies = policies.append(",");
                    policies = policies.append(policyNumbers[i]);
                }
            }
        }
    }

    function withdrawPendingPolicy(string memory policyNumber) public onlyRegistration {
        string[] storage policies = _pendingPolicies[msg.sender];
        for (uint i = 0; i < policies.length; i++) {
            if (keccak256(bytes(policies[i])) == keccak256(bytes(policyNumber))) {
                // 删除待处理保单，同时释放空间，可能存在和并发写入的冲突问题
                for (uint j = i; j < policies.length-1; j++){
                    policies[j] = policies[j+1];
                }
                delete policies[policies.length - 1];
                policies.length--;
                // delete policies[i];
            }
        }

        emit WithdrawPendingPolicy(msg.sender,policyNumber);
    }

    function issueEndorsement(string memory endorsementNumber, string memory productCode, string memory endorsement) public onlyRegistration {
        bool salesAuth = checkSalesAuth(msg.sender,productCode);
        if (!salesAuth)
            revert("Caller don't have the sales agreement for this product!");

        address owner = _productToOwner[productCode];
        _endorsements[endorsementNumber] = endorsement;
        _endorsementToOwner[endorsementNumber].push(owner);
        _endorsementToOwner[endorsementNumber].push(msg.sender);
        _pendingEndorsements[owner].push(endorsementNumber);

        emit IssueEndorsement(owner,msg.sender,endorsementNumber);
    }

    function findEndorsement(string memory endorsementNumber) public view onlyRegistration returns (string memory endosement) {
        address[] memory owners = _endorsementToOwner[endorsementNumber];
        bool isOwner = false;
        for (uint i = 0; i < owners.length; i++) {
            if (msg.sender == owners[i])
                isOwner = true;
        }
        if (!isOwner)
            revert("Caller are not the endorsement owner!");

        endosement = _endorsements[endorsementNumber];
    }

    function findPendingEndorsements() public view onlyRegistration returns (string memory endorsments) {
        string[] memory endoNumbers = _pendingEndorsements[msg.sender];
        endorsments = "";
        for (uint i = 0; i < endoNumbers.length; i++) {
            if(bytes(endoNumbers[i]).length > 0) {
                if( i == 0) {
                    endorsments = endoNumbers[i];
                } else {
                    endorsments = endorsments.append(",");
                    endorsments = endorsments.append(endoNumbers[i]);
                }
            }
        }
    }

    function withdrawPendingEndorsement(string memory endorsementNumber) public onlyRegistration {
        string[] storage endorsments = _pendingEndorsements[msg.sender];
        for (uint i = 0; i < endorsments.length; i++) {
            if (keccak256(bytes(endorsments[i])) == keccak256(bytes(endorsementNumber))) {
                // 删除待处理批改，同时释放空间，可能存在和并发写入的冲突问题
                for (uint j = i; j < endorsments.length-1; j++){
                    endorsments[j] = endorsments[j+1];
                }
                delete endorsments[endorsments.length - 1];
                endorsments.length--;
                // delete endorsments[i];
            }
        }

        emit WithdrawPendingEndorsement(msg.sender,endorsementNumber);
    }

    function checkSalesAuth(address channel, string memory productCode) internal view returns (bool) {
        bool salesAuth = false;
        string[] storage agreements = _salesAgreements[channel];
        for (uint i = 0; i < agreements.length; i++) {
            if (keccak256(bytes(agreements[i])) == keccak256(bytes(productCode))) {
               salesAuth = true;
            }
        }

        return salesAuth;
    }
}

contract InsuranceSalesProxy is UpgradeabilityProxy,InsuranceSalesBase {

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