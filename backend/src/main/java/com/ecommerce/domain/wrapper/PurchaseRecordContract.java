package com.ecommerce.domain.wrapper;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.abi.datatypes.generated.Uint8;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.BaseEventResponse;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tuples.generated.Tuple3;
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
public class PurchaseRecordContract extends Contract {
    public static final String BINARY = "608060405234801561001057600080fd5b5060008054336001600160a01b0319918216811783556001805490921617905561057890819061004090396000f3fe608060405234801561001057600080fd5b50600436106100a95760003560e01c80638ff06ab7116100715780638ff06ab71461016c578063cdb8acf014610192578063d96bfe0e14610202578063f2fde38b1461023e578063f851a44014610264578063fa89401a1461026c576100a9565b806312ad9f6e146100ae578063517807e4146100d65780636452fca1146100fc57806382b46df4146101225780638da5cb5b14610148575b600080fd5b6100d4600480360360208110156100c457600080fd5b50356001600160a01b0316610292565b005b6100d4600480360360208110156100ec57600080fd5b50356001600160a01b03166102bf565b6100d46004803603602081101561011257600080fd5b50356001600160a01b03166102cb565b6100d46004803603602081101561013857600080fd5b50356001600160a01b03166102e9565b610150610307565b604080516001600160a01b039092168252519081900360200190f35b6100d46004803603602081101561018257600080fd5b50356001600160a01b0316610316565b6101be600480360360408110156101a857600080fd5b506001600160a01b038135169060200135610334565b604051808460068111156101ce57fe5b60ff168152602001838152602001826001600160a01b03166001600160a01b03168152602001935050505060405180910390f35b6102286004803603602081101561021857600080fd5b50356001600160a01b0316610374565b6040805160ff9092168252519081900360200190f35b6100d46004803603602081101561025457600080fd5b50356001600160a01b0316610392565b610150610417565b6100d46004803603602081101561028257600080fd5b50356001600160a01b0316610426565b61029b33610444565b6102a457600080fd5b6102b081600333610464565b6102bc81600630610464565b50565b6102bc81600033610464565b6102d433610444565b6102dd57600080fd5b6102bc81600133610464565b6102f233610444565b6102fb57600080fd5b6102b081600433610464565b6000546001600160a01b031681565b61031f33610444565b61032857600080fd5b6102bc81600233610464565b6001600160a01b03918216600090815260026020818152604080842085855280835290842080546001820154969095529152015460ff9091169391921690565b6001600160a01b031660009081526003602052604090205460ff1690565b6000546001600160a01b031633146103a957600080fd5b6001600160a01b0381166103bc57600080fd5b600080546040516001600160a01b03808516939216917f8be0079c531659141344cd1fd0a4f28419497f9722a3daafe3b4186f6b6457e091a3600080546001600160a01b0319166001600160a01b0392909216919091179055565b6001546001600160a01b031681565b61042f33610444565b61043857600080fd5b6102b081600533610464565b6001600160a01b031660009081526003602052604090205460ff16151590565b6001600160a01b038316600090815260036020526040908190205481516060810190925260ff16908084600681111561049957fe5b8152426020808301919091526001600160a01b03808616604093840152871660009081526002825282812060ff861682529091522081518154829060ff191660018360068111156104e657fe5b0217905550602082810151600183810191909155604093840151600290930180546001600160a01b0319166001600160a01b03948516179055969091166000908152600390915220805460ff19169190940160ff1617909255505056fea265627a7a7231582084dbc13d3f11b1a1f026c9aca9771d02ae3158018d5a2b695300dfdba0437ac664736f6c634300050c0032";

    public static final String FUNC_ADDPURCHASE = "addPurchase";

    public static final String FUNC_ADMIN = "admin";

    public static final String FUNC_CANCELPURCHASE = "cancelPurchase";

    public static final String FUNC_CONFIRMDEPOSIT = "confirmDeposit";

    public static final String FUNC_CONFIRMPURCHASE = "confirmPurchase";

    public static final String FUNC_GETRECORD = "getRecord";

    public static final String FUNC_GETRECORDNUMBER = "getRecordNumber";

    public static final String FUNC_OWNER = "owner";

    public static final String FUNC_REFUND = "refund";

    public static final String FUNC_SENDITEM = "sendItem";

    public static final String FUNC_TRANSFEROWNERSHIP = "transferOwnership";

    public static final Event OWNERSHIPTRANSFERRED_EVENT = new Event("OwnershipTransferred",
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {
            }, new TypeReference<Address>(true) {
            }));;

    @Deprecated
    protected PurchaseRecordContract(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice,
            BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected PurchaseRecordContract(String contractAddress, Web3j web3j, Credentials credentials,
            ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected PurchaseRecordContract(String contractAddress, Web3j web3j, TransactionManager transactionManager,
            BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected PurchaseRecordContract(String contractAddress, Web3j web3j, TransactionManager transactionManager,
            ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public List<OwnershipTransferredEventResponse> getOwnershipTransferredEvents(
            TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(OWNERSHIPTRANSFERRED_EVENT,
                transactionReceipt);
        ArrayList<OwnershipTransferredEventResponse> responses = new ArrayList<OwnershipTransferredEventResponse>(
                valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            OwnershipTransferredEventResponse typedResponse = new OwnershipTransferredEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.previousOwner = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.newOwner = (String) eventValues.getIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<OwnershipTransferredEventResponse> ownershipTransferredEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, OwnershipTransferredEventResponse>() {
            @Override
            public OwnershipTransferredEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(OWNERSHIPTRANSFERRED_EVENT,
                        log);
                OwnershipTransferredEventResponse typedResponse = new OwnershipTransferredEventResponse();
                typedResponse.log = log;
                typedResponse.previousOwner = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.newOwner = (String) eventValues.getIndexedValues().get(1).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<OwnershipTransferredEventResponse> ownershipTransferredEventFlowable(
            DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(OWNERSHIPTRANSFERRED_EVENT));
        return ownershipTransferredEventFlowable(filter);
    }

    public RemoteFunctionCall<TransactionReceipt> addPurchase(String _address) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_ADDPURCHASE,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _address)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<String> admin() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_ADMIN,
                Arrays.<Type>asList(), Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {
                }));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<TransactionReceipt> cancelPurchase(String _address) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_CANCELPURCHASE,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _address)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> confirmDeposit(String _address) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_CONFIRMDEPOSIT,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _address)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> confirmPurchase(String _address) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_CONFIRMPURCHASE,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _address)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<Tuple3<BigInteger, BigInteger, String>> getRecord(String _address, BigInteger recordNo) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETRECORD,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _address),
                        new org.web3j.abi.datatypes.generated.Uint256(recordNo)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint8>() {
                }, new TypeReference<Uint256>() {
                }, new TypeReference<Address>() {
                }));
        return new RemoteFunctionCall<Tuple3<BigInteger, BigInteger, String>>(function,
                new Callable<Tuple3<BigInteger, BigInteger, String>>() {
                    @Override
                    public Tuple3<BigInteger, BigInteger, String> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple3<BigInteger, BigInteger, String>((BigInteger) results.get(0).getValue(),
                                (BigInteger) results.get(1).getValue(), (String) results.get(2).getValue());
                    }
                });
    }

    public RemoteFunctionCall<BigInteger> getRecordNumber(String _address) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETRECORDNUMBER,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _address)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint8>() {
                }));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<String> owner() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_OWNER,
                Arrays.<Type>asList(), Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {
                }));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<TransactionReceipt> refund(String _address) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_REFUND,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _address)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> sendItem(String _address) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_SENDITEM,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _address)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> transferOwnership(String newOwner) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_TRANSFEROWNERSHIP,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, newOwner)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    @Deprecated
    public static PurchaseRecordContract load(String contractAddress, Web3j web3j, Credentials credentials,
            BigInteger gasPrice, BigInteger gasLimit) {
        return new PurchaseRecordContract(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static PurchaseRecordContract load(String contractAddress, Web3j web3j,
            TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new PurchaseRecordContract(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static PurchaseRecordContract load(String contractAddress, Web3j web3j, Credentials credentials,
            ContractGasProvider contractGasProvider) {
        return new PurchaseRecordContract(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static PurchaseRecordContract load(String contractAddress, Web3j web3j,
            TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new PurchaseRecordContract(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<PurchaseRecordContract> deploy(Web3j web3j, Credentials credentials,
            ContractGasProvider contractGasProvider) {
        return deployRemoteCall(PurchaseRecordContract.class, web3j, credentials, contractGasProvider, BINARY, "");
    }

    public static RemoteCall<PurchaseRecordContract> deploy(Web3j web3j, TransactionManager transactionManager,
            ContractGasProvider contractGasProvider) {
        return deployRemoteCall(PurchaseRecordContract.class, web3j, transactionManager, contractGasProvider, BINARY,
                "");
    }

    @Deprecated
    public static RemoteCall<PurchaseRecordContract> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice,
            BigInteger gasLimit) {
        return deployRemoteCall(PurchaseRecordContract.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<PurchaseRecordContract> deploy(Web3j web3j, TransactionManager transactionManager,
            BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(PurchaseRecordContract.class, web3j, transactionManager, gasPrice, gasLimit, BINARY,
                "");
    }

    public static class OwnershipTransferredEventResponse extends BaseEventResponse {
        public String previousOwner;

        public String newOwner;
    }
}
