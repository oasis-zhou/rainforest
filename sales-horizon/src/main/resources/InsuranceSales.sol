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
    mapping(string => string) _policies;
    mapping(string => address) _policyToIssuer;

    mapping(string => string) _noticeOfLosses;
    mapping(string => address) _noticeToApplier;
    mapping(string => string) _claims;
    mapping(string => string[]) _policyToClaims;

    mapping(address => string[]) _salesAgreements;

    event ReleaseProduct(address indexed owner, string indexed productCode);
    event IssuePolicy(address indexed owner, address indexed issuer, string indexed policyNumber);
    event ApplyNoticeOfLoss(address indexed owner, address indexed applier, string indexed noticeNumber,string policyNumber);
    event ApproveSalesAgreement(address indexed owner, address indexed approver, string indexed productCode);
    event SyncClaim(address indexed owner, string indexed policyNumber,string indexed claimNumber);

    function releaseProduct(string memory productCode, string memory productSpec) public onlyOwner {
        _products[productCode] = productSpec;

        emit ReleaseProduct(_owner,productCode);
    }

    function findProduct(string memory productCode) public view onlyOwner returns (string memory productSpec) {
        productSpec = _products[productCode];
    }

    function findProductFromChannel(string memory productCode) public view returns (string memory productSpec) {
        bool salesAuth = checkSalesAuth(msg.sender,productCode);
        require(salesAuth,"Caller don't have the sales agreement for this product!");

        productSpec = _products[productCode];
    }

    function approveAgreement(address channel, string memory productCode) public onlyOwner {
        string[] storage products = _salesAgreements[channel];
        products.push(productCode);

        emit ApproveSalesAgreement(channel,_owner,productCode);
    }

    function withdrawAgreement(address channel, string memory productCode) public onlyOwner {
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

        _policies[policyNumber] = policy;
        _policyToIssuer[policyNumber] = msg.sender;

        emit IssuePolicy(_owner,msg.sender,policyNumber);
    }

    function findPolicy(string memory policyNumber) public view returns (string memory policy) {
        bool isOwner = false;
        address policyOwner = _policyToIssuer[policyNumber];
        if(msg.sender == policyOwner || msg.sender == _owner) {
            isOwner = true;
        }
        require(isOwner,"Caller are not the policy owner!");

        policy = _policies[policyNumber];
    }

    function applyNoticeOfLoss(string memory noticeNumber, string memory policyNumber, string memory noticeOfLoss) public {
        string storage policy = _policies[policyNumber];
        bytes memory policyByte = bytes(policy);
        if(policyByte.length > 0) {
            _noticeOfLosses[noticeNumber] = noticeOfLoss;
            _noticeToApplier[noticeNumber] = msg.sender;
        }

        emit ApplyNoticeOfLoss(_owner,msg.sender,noticeNumber,policyNumber);
    }

    function findNoticeOfLoss(string memory noticeNumber) public view returns (string memory noticeOfLoss) {
         bool isOwner = false;
        address noticeOcwner = _noticeToApplier[noticeNumber];
        if(msg.sender == noticeOcwner || msg.sender == _owner) {
            isOwner = true;
        }
        require(isOwner,"Caller are not the notice of loss owner!");

        noticeOfLoss = _noticeOfLosses[noticeNumber];
    }

    function syncClaim(string memory claimNumber, string memory policyNumber, string memory claim) public onlyOwner {
        string storage claimInfo = _claims[claimNumber];
        bytes memory claimInfoByte = bytes(claimInfo);
        if(claimInfoByte.length > 0) {
            _claims[claimNumber] = claim;
        } else {
            _claims[claimNumber] = claim;
            string[] storage policyClaims = _policyToClaims[policyNumber];
            policyClaims.push(claimNumber);
        }

        emit SyncClaim(_owner,policyNumber,claimNumber);
    }

    function findClaim(string memory claimNumber, string memory policyNumber) public view returns (string memory claim) {
        bool isMatch;
        string[] storage policyClaims = _policyToClaims[policyNumber];
        for(uint i = 0; i < policyClaims.length; i++) {
            if (keccak256(bytes(policyClaims[i])) == keccak256(bytes(claimNumber))) {
                isMatch = true;
            }
        }
        require(isMatch, "The policy and the claim are not match!");
        bool isOwner = false;
        address policyOwner = _policyToIssuer[policyNumber];
        if(msg.sender == policyOwner || msg.sender == _owner) {
            isOwner = true;
        }
        require(isOwner,"Caller are not the claim owner!");

        claim = _claims[claimNumber];
    }

    function findClaimsByPolicy(string memory policyNumber) public view returns (string memory claims) {
        bool isOwner = false;
        address policyOwner = _policyToIssuer[policyNumber];
        if(msg.sender == policyOwner || msg.sender == _owner) {
            isOwner = true;
        }
        require(isOwner,"Caller are not the policy owner!");

        claims = "";
        string[] storage policyClaims = _policyToClaims[policyNumber];
        for(uint i = 0; i < policyClaims.length; i++) {
            if(i == 0) {
                claims = policyClaims[i];
            } else {
                claims = claims.append(",");
                claims = claims.append(policyClaims[i]);
            }
        }

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