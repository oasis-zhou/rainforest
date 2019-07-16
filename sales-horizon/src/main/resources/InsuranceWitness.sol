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

contract InsuranceWitness is Ownable {

    mapping(string => address) _organization;
    mapping(string => address) _orgToContract;
    mapping(string => string) _productToOrg;

    event RegisterOrg(address indexed owner, address indexed orgAccount, address indexed contractAddress);
    event RegisterProduct(address indexed owner, string indexed orgCode, string indexed productCode);

    function registerOrg(string memory orgCode, address account, address contractAddress) public onlyOwner {
        _organization[orgCode] = account;
        _orgToContract[orgCode] = contractAddress;

        emit RegisterOrg(_owner, account, contractAddress);
    }

    function registerProduct(string memory orgCode, string memory productCode) public onlyOwner {
        _productToOrg[productCode] = orgCode;

        emit RegisterProduct(_owner, orgCode, productCode);
    }

    function findOrgContract(string memory orgCode) public view returns (address) {
        return _orgToContract[orgCode];
    }

    function findProductOrg(string memory productCode) public view returns (string memory) {
        return _productToOrg[productCode];
    }

    function findProductContract(string memory productCode) public view returns (address) {
        string memory orgCode = _productToOrg[productCode];
        return _orgToContract[orgCode];
    }
}