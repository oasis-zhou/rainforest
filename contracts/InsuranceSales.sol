pragma solidity ^0.5.0;

import './UpgradeabilityProxy.sol';

contract InsuranceBase {

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
    mapping(string => address) internal _policyToInsurer;
    mapping(address => string[]) internal _pendingPolicies;

    mapping(string => string) internal _endorsements;
    mapping(string => address[]) internal _endorsementToOwner;
    mapping(address => string[]) internal _pendingEndorsements;

    mapping(string => string) internal _noticesOfLoss;
    mapping(string => address[]) internal _noticeToOwner;
    mapping(address => string[]) internal _pendingNotices;

    mapping(string => string) internal _claims;
    mapping(string => address[]) internal _claimToOwner;
    mapping(address => string[]) internal _pendingClaims;

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

contract Insurance is InsuranceBase {
    using String for string;

    event ReleaseProduct(address indexed owner, string indexed productCode);
    event IssuePolicy(address indexed owner, address indexed issuer, string indexed policyNumber);
    event UpdatePolicy(address indexed owner, address indexed operator, string indexed policyNumber);
    event WithdrawPendingPolicy(address indexed owner, string indexed policyNumber);
    event IssueEndorsement(address indexed owner, address indexed issuer, string indexed endorsementNumber);
    event WithdrawPendingEndorsement(address indexed owner, string indexed endorsementNumber);
    event ApplyNoticeOfLoss(address indexed owner,address indexed applyer, string indexed noticeNumber);
    event UpdateNoticeOfLoss(address indexed owner,address indexed operator, string indexed noticeNumber);
    event WithdrawPendingNoticeOfLoss(address indexed owner, string indexed noticeNumber);
    event CreateClaim(address indexed owner, address indexed creater, string indexed claimNumber);
    event UpdateClaim(address indexed owner, address indexed operator, string indexed claimNumber);
    event WithdrawPendingClaim(address indexed owner, string indexed claimNumber);
    event CloseClaim(address indexed owner, address indexed operator, string indexed claimNumber);
    event ApproveSalesAgreement(address indexed owner, address indexed approver, string indexed productCode);

    function _checkSalesAuth(address channel, string memory productCode) internal view returns (bool) {
        bool salesAuth = false;
        string[] storage agreements = _salesAgreements[channel];
        for (uint i = 0; i < agreements.length; i++) {
            if (keccak256(bytes(agreements[i])) == keccak256(bytes(productCode))) {
               salesAuth = true;
            }
        }

        return salesAuth;
    }

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

    function findProductByOwner(string memory productCode) public view onlyRegistration returns (string memory productSpec) {
        require(msg.sender == _productToOwner[productCode],"Caller is not the product owner!");

        productSpec = _products[productCode];
    }

    function findProductByChannel(string memory productCode) public view onlyRegistration returns (string memory productSpec) {
        bool salesAuth = _checkSalesAuth(msg.sender,productCode);
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
                products[i] = products[products.length - 1];
                products.pop();
            }
        }
    }

    function issuePolicyByChannel(string memory policyNumber, string memory productCode, string memory policy) public onlyRegistration {
        bool salesAuth = _checkSalesAuth(msg.sender,productCode);
        require(salesAuth,"Caller don't have the sales agreement for this product!");

        address insurer = _productToOwner[productCode];
        _policies[policyNumber] = policy;
        _policyToInsurer[policyNumber] = insurer;
        _policyToOwner[policyNumber].push(insurer);
        _policyToOwner[policyNumber].push(msg.sender);
        _pendingPolicies[insurer].push(policyNumber);

        emit IssuePolicy(insurer,msg.sender,policyNumber);
    }

    function updatePolicy(string memory policyNumber, string memory policy) public onlyRegistration {
        address[] memory owners = _policyToOwner[policyNumber];
        bool isOwner = false;
        for (uint i = 0; i < owners.length; i++) {
            if (msg.sender == owners[i])
                isOwner = true;
        }
        if (!isOwner)
            revert("Caller is not the policy owner!");

        _policies[policyNumber] = policy;

        emit UpdatePolicy(_policyToInsurer[policyNumber], msg.sender, policyNumber);
    }

    function findPolicy(string memory policyNumber) public view onlyRegistration returns (string memory policy)  {
        address[] memory owners = _policyToOwner[policyNumber];
        bool isOwner = false;
        for (uint i = 0; i < owners.length; i++) {
            if (msg.sender == owners[i])
                isOwner = true;
        }
        if (!isOwner)
            revert("Caller is not the policy owner!");

        policy = _policies[policyNumber];
    }

    function findPendingPolicies() public view onlyRegistration returns (string memory policies) {
        string[] memory policyNumbers = _pendingPolicies[msg.sender];
        policies = "";
        uint length = policyNumbers.length;
        if (length > maxQueryNumber) {
            length = maxQueryNumber;
        }
        for (uint i = 0; i < length; i++) {
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
                policies.pop();
                // delete policies[policies.length - 1];
                // policies.length--;
            }
        }

        emit WithdrawPendingPolicy(msg.sender,policyNumber);
    }

    function issueEndorsement(string memory endorsementNumber, string memory policyNumber, string memory endorsement) public onlyRegistration {
        address[] memory owners = _policyToOwner[policyNumber];
        bool isOwner = false;
        for (uint i = 0; i < owners.length; i++) {
            if (msg.sender == owners[i])
                isOwner = true;
        }
        if (!isOwner)
            revert("Caller is not the policy owner!");

        address insurer = _policyToInsurer[policyNumber];
        _endorsements[endorsementNumber] = endorsement;
        _endorsementToOwner[endorsementNumber].push(insurer);
        _endorsementToOwner[endorsementNumber].push(msg.sender);
        _pendingEndorsements[insurer].push(endorsementNumber);

        emit IssueEndorsement(insurer,msg.sender,endorsementNumber);
    }

    function findEndorsement(string memory endorsementNumber) public view onlyRegistration returns (string memory endosement) {
        address[] memory owners = _endorsementToOwner[endorsementNumber];
        bool isOwner = false;
        for (uint i = 0; i < owners.length; i++) {
            if (msg.sender == owners[i])
                isOwner = true;
        }
        if (!isOwner)
            revert("Caller is not the endorsement owner!");

        endosement = _endorsements[endorsementNumber];
    }

    function findPendingEndorsements() public view onlyRegistration returns (string memory endorsments) {
        string[] memory endoNumbers = _pendingEndorsements[msg.sender];
        endorsments = "";
        uint length = endoNumbers.length;
        if (length > maxQueryNumber) {
            length = maxQueryNumber;
        }
        for (uint i = 0; i < length; i++) {
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
        string[] storage endorsements = _pendingEndorsements[msg.sender];
        for (uint i = 0; i < endorsements.length; i++) {
            if (keccak256(bytes(endorsements[i])) == keccak256(bytes(endorsementNumber))) {
                // 删除待处理批改，同时释放空间，可能存在和并发写入的冲突问题
                for (uint j = i; j < endorsements.length-1; j++){
                    endorsements[j] = endorsements[j+1];
                }
                endorsements.pop();
                // delete endorsments[endorsments.length - 1];
                // endorsments.length--;
            }
        }

        emit WithdrawPendingEndorsement(msg.sender,endorsementNumber);
    }

    function applyNoticeOfLoss(string memory noticeNumber, string memory policyNumber, string memory noticeOfLoss) public onlyRegistration {
        address[] memory owners = _policyToOwner[policyNumber];
        bool isOwner = false;
        for (uint i = 0; i < owners.length; i++) {
            if (msg.sender == owners[i])
                isOwner = true;
        }
        if (!isOwner)
            revert("Caller is not the policy owner!");

        address insurer = _policyToInsurer[policyNumber];
        _noticesOfLoss[noticeNumber] = noticeOfLoss;
        _noticeToOwner[noticeNumber].push(insurer);
        _noticeToOwner[noticeNumber].push(msg.sender);
        _pendingNotices[insurer].push(noticeNumber);

        emit ApplyNoticeOfLoss(insurer, msg.sender, noticeNumber);
    }

    function updateNoticeOfLoss(string memory noticeNumber, string memory policyNumber, string memory noticeOfLoss) public onlyRegistration {
        address[] memory owners = _noticeToOwner[noticeNumber];
        bool isOwner = false;
        for (uint i = 0; i < owners.length; i++) {
            if (msg.sender == owners[i])
                isOwner = true;
        }
        if (!isOwner)
            revert("Caller is not the notice of loss owner!");

        _noticesOfLoss[noticeNumber] = noticeOfLoss;

        emit UpdateNoticeOfLoss(_policyToInsurer[policyNumber], msg.sender, noticeNumber);
    }

    function findNoticeOfLoss(string memory noticeNumber) public view onlyRegistration returns (string memory noticeOfLoss) {
        address[] memory owners = _noticeToOwner[noticeNumber];
        bool isOwner = false;
        for (uint i = 0; i < owners.length; i++) {
            if (msg.sender == owners[i])
                isOwner = true;
        }
        if (!isOwner)
            revert("Caller is not the notice of loss owner!");

        noticeOfLoss = _noticesOfLoss[noticeNumber];
    }

    function findPendingNoticeOfLoss() public view onlyRegistration returns (string memory notices) {
        string[] memory noticeNumbers = _pendingNotices[msg.sender];
        notices = "";
        uint length = noticeNumbers.length;
        if (length > maxQueryNumber) {
            length = maxQueryNumber;
        }
        for (uint i = 0; i < length; i++) {
            if(bytes(noticeNumbers[i]).length > 0) {
                if( i == 0) {
                    notices = noticeNumbers[i];
                } else {
                    notices = notices.append(",");
                    notices = notices.append(noticeNumbers[i]);
                }
            }
        }
    }

    function withdrawPendingNoticeOfLoss(string memory noticeNumber) public onlyRegistration {
        string[] storage notices = _pendingNotices[msg.sender];
        for (uint i = 0; i < notices.length; i++) {
            if (keccak256(bytes(notices[i])) == keccak256(bytes(noticeNumber))) {
                for (uint j = i; j < notices.length-1; j++){
                    notices[j] = notices[j+1];
                }
                notices.pop();
            }
        }

        emit WithdrawPendingNoticeOfLoss(msg.sender, noticeNumber);
    }

    function createClaim(string memory claimNumber, string memory policyNumber, string memory claim) public onlyRegistration {
        address[] memory owners = _policyToOwner[policyNumber];
        bool isOwner = false;
        for (uint i = 0; i < owners.length; i++) {
            if (msg.sender == owners[i])
                isOwner = true;
        }
        if (!isOwner)
            revert("Caller are not the policy owner!");

        address insurer = _policyToInsurer[policyNumber];
        _claims[claimNumber] = claim;
        _claimToOwner[claimNumber].push(insurer);
        _claimToOwner[claimNumber].push(msg.sender);
        _pendingClaims[insurer].push(claimNumber);

        emit ApplyNoticeOfLoss(insurer, msg.sender, claimNumber);
    }

    function updateClaim(string memory claimNumber, string memory policyNumber, string memory claim) public onlyRegistration {
        address[] memory owners = _claimToOwner[claimNumber];
        bool isOwner = false;
        for (uint i = 0; i < owners.length; i++) {
            if (msg.sender == owners[i])
                isOwner = true;
        }
        if (!isOwner)
            revert("Caller is not the claim owner!");

        _claims[claimNumber] = claim;

        emit UpdateClaim(_policyToInsurer[policyNumber], msg.sender, claimNumber);
    }

    function closeClaim(string memory claimNumber, string memory policyNumber, string memory claim) public onlyRegistration {
        address[] memory owners = _claimToOwner[claimNumber];
        bool isOwner = false;
        for (uint i = 0; i < owners.length; i++) {
            if (msg.sender == owners[i])
                isOwner = true;
        }
        if (!isOwner)
            revert("Caller is not the claim owner!");

        _claims[claimNumber] = claim;

        emit CloseClaim(_policyToInsurer[policyNumber], msg.sender, claimNumber);
    }

    function findClaim(string memory claimNumber) public view onlyRegistration returns (string memory claim) {
        address[] memory owners = _claimToOwner[claimNumber];
        bool isOwner = false;
        for (uint i = 0; i < owners.length; i++) {
            if (msg.sender == owners[i])
                isOwner = true;
        }
        if (!isOwner)
            revert("Caller is not the claim owner!");

        claim = _claims[claimNumber];
    }

    function findPendingClaim() public view onlyRegistration returns (string memory claims) {
        string[] memory claimNumbers = _pendingClaims[msg.sender];
        claims = "";
        uint length = claimNumbers.length;
        if (length > maxQueryNumber) {
            length = maxQueryNumber;
        }
        for (uint i = 0; i < length; i++) {
            if(bytes(claimNumbers[i]).length > 0) {
                if( i == 0) {
                    claims = claimNumbers[i];
                } else {
                    claims = claims.append(",");
                    claims = claims.append(claimNumbers[i]);
                }
            }
        }
    }

    function withdrawPendingClaim(string memory claimNumber) public onlyRegistration {
        string[] storage claims = _pendingClaims[msg.sender];
        for (uint i = 0; i < claims.length; i++) {
            if (keccak256(bytes(claims[i])) == keccak256(bytes(claimNumber))) {
                for (uint j = i; j < claims.length-1; j++){
                    claims[j] = claims[j+1];
                }
                claims.pop();
            }
        }

        emit WithdrawPendingClaim(msg.sender, claimNumber);
    }
}

contract InsuranceProxy is UpgradeabilityProxy,InsuranceBase {

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