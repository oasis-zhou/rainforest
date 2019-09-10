pragma solidity ^0.5.0;

/**
 * @title Proxy
 * @dev Implements delegation of calls to other contracts, with proper
 * forwarding of return values and bubbling of failures.
 * It defines a fallback function that delegates all calls to the address
 * returned by the abstract _implementation() internal function.
 */
contract Proxy {
  /**
   * @dev Fallback function.
   * Implemented entirely in `_fallback`.
   */
  function () payable external {
    _fallback();
  }

  /**
   * @return The Address of the implementation.
   */
  function _implementation() internal view returns (address);

  /**
   * @dev Delegates execution to an implementation contract.
   * This is a low level function that doesn't return to its internal call site.
   * It will return to the external caller whatever the implementation returns.
   * @param implementation Address to delegate.
   */
  function _delegate(address implementation) internal {
    assembly {
      // Copy msg.data. We take full control of memory in this inline assembly
      // block because it will not return to Solidity code. We overwrite the
      // Solidity scratch pad at memory position 0.
      calldatacopy(0, 0, calldatasize)

      // Call the implementation.
      // out and outsize are 0 because we don't know the size yet.
      let result := delegatecall(gas, implementation, 0, calldatasize, 0, 0)

      // Copy the returned data.
      returndatacopy(0, 0, returndatasize)

      switch result
      // delegatecall returns 0 on error.
      case 0 { revert(0, returndatasize) }
      default { return(0, returndatasize) }
    }
  }

  /**
   * @dev Function that is run as the first thing in the fallback function.
   * Can be redefined in derived contracts to add functionality.
   * Redefinitions must call super._willFallback().
   */
  function _willFallback() internal {
  }

  /**
   * @dev fallback implementation.
   * Extracted to enable manual triggering.
   */
  function _fallback() internal {
    _willFallback();
    _delegate(_implementation());
  }
}

/**
 * @title UpgradeabilityProxy
 * @dev This contract implements a proxy that allows to change the
 * implementation address to which it will delegate.
 * Such a change is called an implementation upgrade.
 */
contract UpgradeabilityProxy is Proxy {
  /**
   * @dev Emitted when the implementation is upgraded.
   * @param implementation Address of the new implementation.
   */
  event Upgraded(address indexed implementation);

  /**
   * @dev Storage slot with the address of the current implementation.
   * This is the keccak-256 hash of "com.shie.proxy.implementation" subtracted by 1, and is
   * validated in the constructor.
   */
  bytes32 internal constant IMPLEMENTATION_SLOT = keccak256("com.shie.proxy.implementation");

  /**
   * @dev Returns the current implementation.
   * @return Address of the current implementation
   */
  function _implementation() internal view returns (address impl) {
    bytes32 slot = IMPLEMENTATION_SLOT;
    assembly {
      impl := sload(slot)
    }
  }

  /**
   * @dev Upgrades the proxy to a new implementation.
   * @param newImplementation Address of the new implementation.
   */
  function _upgradeTo(address newImplementation) internal {
    _setImplementation(newImplementation);
    emit Upgraded(newImplementation);
  }

  /**
   * @dev Sets the implementation address of the proxy.
   * @param newImplementation Address of the new implementation.
   */
  function _setImplementation(address newImplementation) internal {
    bytes32 slot = IMPLEMENTATION_SLOT;

    assembly {
      sstore(slot, newImplementation)
    }
  }
}

/**
 * @title OwnedUpgradeabilityProxy
 * @dev This contract combines an upgradeability proxy with an authorization
 * mechanism for Owneristrative tasks.
 * All external functions in this contract must be guarded by the
 * `ifOwner` modifier. See ethereum/solidity#3864 for a Solidity
 * feature proposal that would enable this to be done automatically.
 */
contract OwnedUpgradeabilityProxy is UpgradeabilityProxy {
  /**
   * @dev Emitted when the Owneristration has been transferred.
   * @param previousOwner Address of the previous owner.
   * @param newOwner Address of the new Owner.
   */
  event OwnerChanged(address previousOwner, address newOwner);

  /**
   * @dev Storage slot with the Owner of the contract.
   * This is the keccak-256 hash of "com.shie.owner" subtracted by 1, and is
   * validated in the constructor.
   */

  bytes32 internal constant OWNER_SLOT = keccak256("com.shie.owner");

  /**
   * @dev Modifier to check whether the `msg.sender` is the owner.
   * If it is, it will run the function. Otherwise, it will delegate the call
   * to the implementation.
   */
  modifier ifOwner() {
    if (msg.sender == _owner()) {
      _;
    } else {
      _fallback();
    }
  }

  constructor() public {
    _setOwner(msg.sender);
  }

  /**
   * @return The address of the proxy owner.
   */
  function owner() external ifOwner returns (address) {
    return _owner();
  }

  /**
   * @return The address of the implementation.
   */
  function implementation() external ifOwner returns (address) {
    return _implementation();
  }

  /**
   * @dev Changes the owner of the proxy.
   * Only the current owner can call this function.
   * @param newOwner Address to transfer proxy owneristration to.
   */
  function changeOwner(address newOwner) external ifOwner {
    require(newOwner != address(0), "Cannot change the Owner of a proxy to the zero address");
    emit OwnerChanged(_owner(), newOwner);
    _setOwner(newOwner);
  }

  /**
   * @dev Upgrade the backing implementation of the proxy.
   * Only the Owner can call this function.
   * @param newImplementation Address of the new implementation.
   */
  function upgradeTo(address newImplementation) external ifOwner {
    _upgradeTo(newImplementation);
  }

  /**
   * @dev Upgrade the backing implementation of the proxy and call a function
   * on the new implementation.
   * This is useful to initialize the proxied contract.
   * @param newImplementation Address of the new implementation.
   * @param data Data to send as msg.data in the low level call.
   * It should include the signature and the parameters of the function to be called, as described in
   * https://solidity.readthedocs.io/en/v0.4.24/abi-spec.html#function-selector-and-argument-encoding.
   */
  function upgradeToAndCall(address newImplementation, bytes calldata data) payable external ifOwner {
    _upgradeTo(newImplementation);
    (bool success,) = newImplementation.delegatecall(data);
    require(success);
  }

  /**
   * @return The owner slot.
   */
  function _owner() internal view returns (address adm) {
    bytes32 slot = OWNER_SLOT;
    assembly {
      adm := sload(slot)
    }
  }

  /**
   * @dev Sets the address of the proxy owner.
   * @param newOwner Address of the new proxy owner.
   */
  function _setOwner(address newOwner) internal {
    bytes32 slot = OWNER_SLOT;
    assembly {
      sstore(slot, newOwner)
    }
  }

  /**
   * @dev Only fall back when the sender is not the owner.
   */
  function _willFallback() internal {
    require(msg.sender != _owner(), "Cannot call fallback function from the proxy owner");
    super._willFallback();
  }
}
