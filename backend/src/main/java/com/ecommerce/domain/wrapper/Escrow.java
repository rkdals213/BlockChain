package com.ecommerce.domain.wrapper;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.abi.datatypes.generated.Uint32;
import org.web3j.abi.datatypes.generated.Uint8;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.RemoteFunctionCall;
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
 * <p>Generated with web3j version 4.5.16.
 */
@SuppressWarnings("rawtypes")
public class Escrow extends Contract {
    public static final String BINARY = "608060405234801561001057600080fd5b50604051610da9380380610da9833981810160405260e081101561003357600080fd5b8101908080519060200190929190805190602001909291908051906020019092919080519060200190929190805190602001909291908051906020019092919080519060200190929190505050866000806101000a8154816001600160a01b0302191690836001600160a01b0316021790555085600160006101000a8154816001600160a01b0302191690836001600160a01b0316021790555085600260006101000a8154816001600160a01b0302191690836001600160a01b0316021790555084600360006101000a8154816001600160a01b0302191690836001600160a01b0316021790555083600360146101000a81548163ffffffff021916908363ffffffff16021790555082600460006101000a8154816001600160a01b0302191690836001600160a01b0316021790555081600560006101000a8154816001600160a01b0302191690836001600160a01b0316021790555080600560146101000a81548163ffffffff021916908363ffffffff160217905550426006819055506000809054906101000a90046001600160a01b03166001600160a01b03166370a08231306040518263ffffffff1660e01b815260040180826001600160a01b03166001600160a01b0316815260200191505060206040518083038186803b15801561021c57600080fd5b505afa158015610230573d6000803e3d6000fd5b505050506040513d602081101561024657600080fd5b5051600755600254604080517f517807e400000000000000000000000000000000000000000000000000000000815230600482015290516001600160a01b039092169163517807e49160248082019260009290919082900301818387803b1580156102b057600080fd5b505af11580156102c4573d6000803e3d6000fd5b5050505050505050505050610acb806102de6000396000f3fe608060405234801561001057600080fd5b506004361061010b5760003560e01c806380e41ac1116100a2578063b8a7083a11610071578063b8a7083a146101e2578063b8f09942146101ea578063c19d93fb146101f2578063ca6158cb1461021e578063ea8a1af0146102265761010b565b806380e41ac1146101a957806380f474f6146101b1578063a035b1fe146101b9578063b46300ec146101da5761010b565b806357c4b262116100de57806357c4b262146101895780636177a9f8146101915780637022b58e146101995780637150d8ae146101a15761010b565b806308551a531461011057806337143233146101345780634c4cea5a1461015357806355be04ad1461016d575b600080fd5b61011861022e565b604080516001600160a01b039092168252519081900360200190f35b6101516004803603602081101561014a57600080fd5b503561023d565b005b61015b610311565b60408051918252519081900360200190f35b610175610317565b604080519115158252519081900360200190f35b61015b61048a565b610118610490565b61017561049f565b6101186106e9565b6101186106f8565b61015b610707565b6101c161070d565b6040805163ffffffff9092168252519081900360200190f35b610151610720565b6101186107d4565b6101186107e3565b6101fa6107f2565b6040518082600481111561020a57fe5b60ff16815260200191505060405180910390f35b6101c1610802565b610151610815565b6004546001600160a01b031681565b6005546001600160a01b0316331461025457600080fd5b6009541561026157600080fd5b6003600554600160c01b900460ff16600481111561027b57fe5b1461028557600080fd5b600581111561029357600080fd5b6009819055600354600554604080516366eee6b560e01b81526001600160a01b03928316600482015230602482015260448101859052905191909216916366eee6b591606480830192600092919082900301818387803b1580156102f657600080fd5b505af115801561030a573d6000803e3d6000fd5b5050505050565b60085481565b600080600554600160c01b900460ff16600481111561033257fe5b1461033c57600080fd5b60008054604080516370a0823160e01b815230600482015290516001600160a01b03909216916370a0823191602480820192602092909190829003018186803b15801561038857600080fd5b505afa15801561039c573d6000803e3d6000fd5b505050506040513d60208110156103b257600080fd5b505160055490915063ffffffff600160a01b90910481166014011681101561040b5760405162461bcd60e51b815260040180806020018281038252602c815260200180610a6b602c913960400191505060405180910390fd5b6005805460ff60c01b1916600160c01b17905560025460408051636452fca160e01b815230600482015290516001600160a01b0390921691636452fca19160248082019260009290919082900301818387803b15801561046a57600080fd5b505af115801561047e573d6000803e3d6000fd5b50505050600191505090565b60065481565b6002546001600160a01b031681565b6005546000906001600160a01b031633146104b957600080fd5b6002600554600160c01b900460ff1660048111156104d357fe5b146104dd57600080fd5b60008054604080516370a0823160e01b815230600482015290516001600160a01b03909216916370a0823191602480820192602092909190829003018186803b15801561052957600080fd5b505afa15801561053d573d6000803e3d6000fd5b505050506040513d602081101561055357600080fd5b505160008054600480546005546040805163a9059cbb60e01b81526001600160a01b0393841694810194909452600160a01b90910463ffffffff166024840152519495509091169263a9059cbb92604480840193602093929083900390910190829087803b1580156105c457600080fd5b505af11580156105d8573d6000803e3d6000fd5b505050506040513d60208110156105ee57600080fd5b5050600080546005546040805163a9059cbb60e01b81526001600160a01b038084166004830152600160a01b90930463ffffffff16860360248201529051919092169263a9059cbb92604480820193602093909283900390910190829087803b15801561065a57600080fd5b505af115801561066e573d6000803e3d6000fd5b505050506040513d602081101561068457600080fd5b50506005805460ff60c01b1916600360c01b1790554260085560025460408051630956cfb760e11b815230600482015290516001600160a01b03909216916312ad9f6e9160248082019260009290919082900301818387803b15801561046a57600080fd5b6005546001600160a01b031681565b6000546001600160a01b031681565b60095481565b600554600160a01b900463ffffffff1681565b6004546001600160a01b0316331461073757600080fd5b6001600554600160c01b900460ff16600481111561075157fe5b1461075b57600080fd5b6005805460ff60c01b1916600160c11b17905560025460408051638ff06ab760e01b815230600482015290516001600160a01b0390921691638ff06ab79160248082019260009290919082900301818387803b1580156107ba57600080fd5b505af11580156107ce573d6000803e3d6000fd5b50505050565b6003546001600160a01b031681565b6001546001600160a01b031681565b600554600160c01b900460ff1681565b600354600160a01b900463ffffffff1681565b6004546001600160a01b031633148061083857506005546001600160a01b031633145b61084157600080fd5b6000600554600160c01b900460ff16600481111561085b57fe5b148061087e57506001600554600160c01b900460ff16600481111561087c57fe5b145b61088757600080fd5b60008054604080516370a0823160e01b815230600482015290516001600160a01b03909216916370a0823191602480820192602092909190829003018186803b1580156108d357600080fd5b505afa1580156108e7573d6000803e3d6000fd5b505050506040513d60208110156108fd57600080fd5b505160055490915063ffffffff600160a01b9091048116601401168110610a0b57600080546005546040805163a9059cbb60e01b81526001600160a01b039283166004820152602481018690529051919092169263a9059cbb92604480820193602093909283900390910190829087803b15801561097a57600080fd5b505af115801561098e573d6000803e3d6000fd5b505050506040513d60208110156109a457600080fd5b505060025460408051637d44a00d60e11b815230600482015290516001600160a01b039092169163fa89401a9160248082019260009290919082900301818387803b1580156109f257600080fd5b505af1158015610a06573d6000803e3d6000fd5b505050505b6005805460ff60c01b1916600160c21b179055600254604080516320ad1b7d60e21b815230600482015290516001600160a01b03909216916382b46df49160248082019260009290919082900301818387803b1580156102f657600080fdfe47726561746572207468616e206f7220657161756c7320746f2032302043617368206e656365737361727921a265627a7a723158203ae1148eb95f20513b937e0b8a96bf7e42aa2867a8f5e22c30c8a348d653657c64736f6c634300050c0032";

    public static final String FUNC_BUYER = "buyer";

    public static final String FUNC_CANCEL = "cancel";

    public static final String FUNC_CASHCONTRACT = "cashContract";

    public static final String FUNC_CHECKDEPOSIT = "checkDeposit";

    public static final String FUNC_COMPLETEAT = "completeAt";

    public static final String FUNC_CONFIRM = "confirm";

    public static final String FUNC_EVALUATE = "evaluate";

    public static final String FUNC_ITEMID = "itemId";

    public static final String FUNC_PRICE = "price";

    public static final String FUNC_PURCHASERECORDCONTRACT = "purchaseRecordContract";

    public static final String FUNC_PURCHASERECORDCONTRACTADDRESS = "purchaseRecordContractAddress";

    public static final String FUNC_PURCHASEDAT = "purchasedAt";

    public static final String FUNC_RATING = "rating";

    public static final String FUNC_RATINGRECORDCONTRACT = "ratingRecordContract";

    public static final String FUNC_SELLER = "seller";

    public static final String FUNC_SEND = "send";

    public static final String FUNC_STATE = "state";

    @Deprecated
    protected Escrow(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected Escrow(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected Escrow(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected Escrow(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public RemoteFunctionCall<String> buyer() {
        final Function function = new Function(FUNC_BUYER, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<TransactionReceipt> cancel() {
        final Function function = new Function(
                FUNC_CANCEL, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<String> cashContract() {
        final Function function = new Function(FUNC_CASHCONTRACT, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<TransactionReceipt> checkDeposit() {
        final Function function = new Function(
                FUNC_CHECKDEPOSIT, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<BigInteger> completeAt() {
        final Function function = new Function(FUNC_COMPLETEAT, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<TransactionReceipt> confirm() {
        final Function function = new Function(
                FUNC_CONFIRM, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> evaluate(BigInteger score) {
        final Function function = new Function(
                FUNC_EVALUATE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(score)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<BigInteger> itemId() {
        final Function function = new Function(FUNC_ITEMID, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint32>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<BigInteger> price() {
        final Function function = new Function(FUNC_PRICE, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint32>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<String> purchaseRecordContract() {
        final Function function = new Function(FUNC_PURCHASERECORDCONTRACT, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<String> purchaseRecordContractAddress() {
        final Function function = new Function(FUNC_PURCHASERECORDCONTRACTADDRESS, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<BigInteger> purchasedAt() {
        final Function function = new Function(FUNC_PURCHASEDAT, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<BigInteger> rating() {
        final Function function = new Function(FUNC_RATING, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<String> ratingRecordContract() {
        final Function function = new Function(FUNC_RATINGRECORDCONTRACT, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<String> seller() {
        final Function function = new Function(FUNC_SELLER, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<TransactionReceipt> send() {
        final Function function = new Function(
                FUNC_SEND, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<BigInteger> state() {
        final Function function = new Function(FUNC_STATE, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint8>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    @Deprecated
    public static Escrow load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new Escrow(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static Escrow load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new Escrow(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static Escrow load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new Escrow(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static Escrow load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new Escrow(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<Escrow> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider, String _contractAddress, String _purchaseRecordAddress, String _ratingRecordAddress, BigInteger _itemId, String _seller, String _buyer, BigInteger _price) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _contractAddress), 
                new org.web3j.abi.datatypes.Address(160, _purchaseRecordAddress), 
                new org.web3j.abi.datatypes.Address(160, _ratingRecordAddress), 
                new org.web3j.abi.datatypes.generated.Uint32(_itemId), 
                new org.web3j.abi.datatypes.Address(160, _seller), 
                new org.web3j.abi.datatypes.Address(160, _buyer), 
                new org.web3j.abi.datatypes.generated.Uint32(_price)));
        return deployRemoteCall(Escrow.class, web3j, credentials, contractGasProvider, BINARY, encodedConstructor);
    }

    public static RemoteCall<Escrow> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider, String _contractAddress, String _purchaseRecordAddress, String _ratingRecordAddress, BigInteger _itemId, String _seller, String _buyer, BigInteger _price) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _contractAddress), 
                new org.web3j.abi.datatypes.Address(160, _purchaseRecordAddress), 
                new org.web3j.abi.datatypes.Address(160, _ratingRecordAddress), 
                new org.web3j.abi.datatypes.generated.Uint32(_itemId), 
                new org.web3j.abi.datatypes.Address(160, _seller), 
                new org.web3j.abi.datatypes.Address(160, _buyer), 
                new org.web3j.abi.datatypes.generated.Uint32(_price)));
        return deployRemoteCall(Escrow.class, web3j, transactionManager, contractGasProvider, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<Escrow> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, String _contractAddress, String _purchaseRecordAddress, String _ratingRecordAddress, BigInteger _itemId, String _seller, String _buyer, BigInteger _price) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _contractAddress), 
                new org.web3j.abi.datatypes.Address(160, _purchaseRecordAddress), 
                new org.web3j.abi.datatypes.Address(160, _ratingRecordAddress), 
                new org.web3j.abi.datatypes.generated.Uint32(_itemId), 
                new org.web3j.abi.datatypes.Address(160, _seller), 
                new org.web3j.abi.datatypes.Address(160, _buyer), 
                new org.web3j.abi.datatypes.generated.Uint32(_price)));
        return deployRemoteCall(Escrow.class, web3j, credentials, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<Escrow> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, String _contractAddress, String _purchaseRecordAddress, String _ratingRecordAddress, BigInteger _itemId, String _seller, String _buyer, BigInteger _price) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _contractAddress), 
                new org.web3j.abi.datatypes.Address(160, _purchaseRecordAddress), 
                new org.web3j.abi.datatypes.Address(160, _ratingRecordAddress), 
                new org.web3j.abi.datatypes.generated.Uint32(_itemId), 
                new org.web3j.abi.datatypes.Address(160, _seller), 
                new org.web3j.abi.datatypes.Address(160, _buyer), 
                new org.web3j.abi.datatypes.generated.Uint32(_price)));
        return deployRemoteCall(Escrow.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, encodedConstructor);
    }
}
