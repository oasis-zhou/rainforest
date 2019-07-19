pragma solidity ^0.5.0;


contract Ownable {
    address public _owner;

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


contract InsuranceSales is Ownable {
    using String for string;

    mapping(string => string) _products;
    mapping(string => address) _productToOwner;

    mapping(string => string) _policies;
    mapping(string => address) _policyToOwner;
    mapping(address => string[]) _pendingPolicies;

    mapping(string => string) _endorsements;
    mapping(string => address) _endorsementToOwner;
    mapping(address => string[]) _pendingEndorsements;

    mapping(address => string[]) _salesAgreements;

    event ReleaseProduct(address indexed owner, string indexed productCode);
    event IssuePolicy(address indexed owner, address indexed issuer, string indexed policyNumber);
    event WithdrawPendingPolicy(address indexed owner, string indexed policyNumber);
    event IssueEndorsement(address indexed owner, address indexed issuer, string indexed endorsementNumber);
    event WithdrawPendingEndorsement(address indexed owner, string indexed endorsementNumber);
    event ApproveSalesAgreement(address indexed owner, address indexed approver, string indexed productCode);
    

    function releaseProduct(address owner,string memory productCode, string memory productSpec) public onlyOwner {
        _products[productCode] = productSpec;
        _productToOwner[productCode] = owner;

        emit ReleaseProduct(owner,productCode);
    }

    function findProduct(string memory productCode) public view onlyOwner returns (string memory productSpec) {
        require(msg.sender == _productToOwner[productCode],"Caller is not the product owner!");

        productSpec = _products[productCode];
    }

    function findProductFromChannel(string memory productCode) public view returns (string memory productSpec) {
        bool salesAuth = checkSalesAuth(msg.sender,productCode);
        require(salesAuth,"Caller don't have the sales agreement for this product!");

        productSpec = _products[productCode];
    }

    function approveAgreement(address channel, string memory productCode) public {
        require(msg.sender == _productToOwner[productCode],"Caller is not the product owner!");

        _salesAgreements[channel].push(productCode);

        emit ApproveSalesAgreement(channel,_owner,productCode);
    }

    function withdrawAgreement(address channel, string memory productCode) public {
        require(msg.sender == _productToOwner[productCode],"Caller is not the product owner!");

        string[] storage products = _salesAgreements[channel];
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

    function issuePolicy(string memory policyNumber, string memory productCode, string memory policy) public {
        bool salesAuth = checkSalesAuth(msg.sender,productCode);
        require(salesAuth,"Caller don't have the sales agreement for this product!");

        address owner = _productToOwner[productCode];
        _policies[policyNumber] = policy;
        _policyToOwner[policyNumber] = owner;
        _pendingPolicies[owner].push(policyNumber);

        emit IssuePolicy(owner,msg.sender,policyNumber);
    }

    function findPolicy(string memory policyNumber) public view returns (string memory policy) {
        require(msg.sender == _policyToOwner[policyNumber],"Caller are not the policy owner!");

        policy = _policies[policyNumber];
    }

    function findPendingPolicies() public view returns (string memory policies) {
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

    function withdrawPendingPolicy(string memory policyNumber) public {
        require(msg.sender == _policyToOwner[policyNumber],"Caller is not the policy owner!");

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

    function issueEndorsement(string memory endorsementNumber, string memory productCode, string memory endorsement) public {
        bool salesAuth = checkSalesAuth(msg.sender,productCode);
        require(salesAuth,"Caller don't have the sales agreement for this product!");

        address owner = _productToOwner[productCode];
        _endorsements[endorsementNumber] = endorsement;
        _endorsementToOwner[endorsementNumber] = owner;
        _pendingEndorsements[owner].push(endorsementNumber);

        emit IssueEndorsement(owner,msg.sender,endorsementNumber);
    }

    function findEndorsement(string memory endorsementNumber) public view returns (string memory endosement) {
        bool isOwner = false;
        address endosementOwner = _endorsementToOwner[endorsementNumber];
        if(msg.sender == endosementOwner) {
            isOwner = true;
        }
        require(isOwner,"Caller are not the endorsement owner!");

        endosement = _endorsements[endorsementNumber];
    }

    function findPendingEndorsements() public view returns (string memory endorsments) {
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

    function withdrawPendingEndorsement(string memory endorsementNumber) public {
        require(msg.sender == _endorsementToOwner[endorsementNumber],"Caller is not the endorsement owner!");

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