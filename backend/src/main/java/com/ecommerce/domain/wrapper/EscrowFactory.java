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
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.abi.datatypes.generated.Uint32;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.BaseEventResponse;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tuples.generated.Tuple6;
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
public class EscrowFactory extends Contract {
    public static final String BINARY = "608060405234801561001057600080fd5b506040516118083803806118088339818101604052606081101561003357600080fd5b5080516020820151604090920151600080546001600160a01b031990811633908117909255600180548216909217909155600280546001600160a01b039485169083161790556003805494841694821694909417909355600480549290911691909216179055611760806100a86000396000f3fe608060405234801561001057600080fd5b50600436106100f55760003560e01c8063b8f0994211610097578063ef435ae011610066578063ef435ae01461028a578063f2fde38b146102b0578063f851a440146102d6578063ffb5d239146102de576100f5565b8063b8f0994214610255578063c6438d021461025d578063c6dd0acb14610265578063c8f84cd61461026d576100f5565b806366676547116100d357806366676547146101ca5780636c466c41146101ff57806374910a87146102275780638da5cb5b1461024d576100f5565b8063012f52ee146100fa5780633a25a924146101335780634370126514610195575b600080fd5b6101176004803603602081101561011057600080fd5b5035610347565b604080516001600160a01b039092168252519081900360200190f35b61015e6004803603604081101561014957600080fd5b5063ffffffff8135811691602001351661036e565b6040805163ffffffff95861681529390941660208401526001600160a01b0390911682840152606082015290519081900360800190f35b6101b6600480360360208110156101ab57600080fd5b503561ffff16610451565b604080519115158252519081900360200190f35b6101ed600480360360208110156101e057600080fd5b503563ffffffff166104e2565b60408051918252519081900360200190f35b6102256004803603602081101561021557600080fd5b50356001600160a01b031661075d565b005b6102256004803603602081101561023d57600080fd5b50356001600160a01b0316610796565b6101176107cf565b6101176107de565b6101176107ed565b6101176107fc565b6101176004803603602081101561028357600080fd5b503561080b565b610225600480360360208110156102a057600080fd5b50356001600160a01b0316610826565b610225600480360360208110156102c657600080fd5b50356001600160a01b031661085f565b6101176108e4565b610301600480360360208110156102f457600080fd5b503563ffffffff166108f3565b6040805163ffffffff97881681529590961660208601526001600160a01b039093168486015260608401919091526080830152151560a082015290519081900360c00190f35b6006818154811061035457fe5b6000918252602090912001546001600160a01b0316905081565b60008060008061037c610940565b50506040805160c08101825263ffffffff9687168082529587166020808301918252338385019081524260608501908152600060808601818152600160a088018181529c83526005909552969020945185549451925163ffffffff19909516818d161767ffffffff0000000019166401000000009c84169c909c029b909b1768010000000000000000600160e01b031916600160401b6001600160a01b03861602178555519184018290559351600284015596516003909201805460ff19169215159290921790915595969095949350915050565b61ffff811660009081526005602052604081208054600160401b90046001600160a01b031633146104c9576040805162461bcd60e51b815260206004820152601c60248201527f4f6e6c792073656c6c65722063616e2064656c657465206974656d2e00000000604482015290519081900360640190fd5b426002820155600301805460ff19169055506001919050565b60006104ec610940565b5063ffffffff828116600090815260056020908152604091829020825160c08101845281548086168252640100000000810490951692810192909252600160401b9093046001600160a01b0316918101829052600183015460608201526002830154608082015260039092015460ff16151560a08301523314156105ad576040805162461bcd60e51b8152602060048201526013602482015272436865636b206275796572206164647265737360681b604482015290519081900360640190fd5b60a081015115156001146105fd576040805162461bcd60e51b81526020600482015260126024820152714974656d206e6f7420617661696c61626c6560701b604482015290519081900360640190fd5b600254600354600454604080850151602086015191516000956001600160a01b0390811695811694169289929133919061063690610975565b6001600160a01b039788168152958716602087015293861660408087019190915263ffffffff9384166060870152918616608086015290941660a08401521660c082015290519081900360e001906000f080158015610699573d6000803e3d6000fd5b5060068054600181019091557ff652222313e28459528d920b65115c16c04f3efc82aaedc97be59f3f377c0d3f810180546001600160a01b038085166001600160a01b031992831681179093556000848152600760209081526040918290208054909416851790935587810151815163ffffffff8c16815293840186905283820194909452519495509293339392909216917faa2d605f5f6537bca132ac73803dc6247ef9bb84e742fe25e5e83d3dc5de28fa9181900360600190a3949350505050565b6000546001600160a01b0316331461077457600080fd5b600380546001600160a01b0319166001600160a01b0392909216919091179055565b6000546001600160a01b031633146107ad57600080fd5b600280546001600160a01b0319166001600160a01b0392909216919091179055565b6000546001600160a01b031681565b6003546001600160a01b031681565b6002546001600160a01b031681565b6004546001600160a01b031681565b6007602052600090815260409020546001600160a01b031681565b6000546001600160a01b0316331461083d57600080fd5b600480546001600160a01b0319166001600160a01b0392909216919091179055565b6000546001600160a01b0316331461087657600080fd5b6001600160a01b03811661088957600080fd5b600080546040516001600160a01b03808516939216917f8be0079c531659141344cd1fd0a4f28419497f9722a3daafe3b4186f6b6457e091a3600080546001600160a01b0319166001600160a01b0392909216919091179055565b6001546001600160a01b031681565b600560205260009081526040902080546001820154600283015460039093015463ffffffff80841694640100000000850490911693600160401b90046001600160a01b0316929160ff1686565b6040805160c081018252600080825260208201819052918101829052606081018290526080810182905260a081019190915290565b610da9806109838339019056fe608060405234801561001057600080fd5b50604051610da9380380610da9833981810160405260e081101561003357600080fd5b8101908080519060200190929190805190602001909291908051906020019092919080519060200190929190805190602001909291908051906020019092919080519060200190929190505050866000806101000a8154816001600160a01b0302191690836001600160a01b0316021790555085600160006101000a8154816001600160a01b0302191690836001600160a01b0316021790555085600260006101000a8154816001600160a01b0302191690836001600160a01b0316021790555084600360006101000a8154816001600160a01b0302191690836001600160a01b0316021790555083600360146101000a81548163ffffffff021916908363ffffffff16021790555082600460006101000a8154816001600160a01b0302191690836001600160a01b0316021790555081600560006101000a8154816001600160a01b0302191690836001600160a01b0316021790555080600560146101000a81548163ffffffff021916908363ffffffff160217905550426006819055506000809054906101000a90046001600160a01b03166001600160a01b03166370a08231306040518263ffffffff1660e01b815260040180826001600160a01b03166001600160a01b0316815260200191505060206040518083038186803b15801561021c57600080fd5b505afa158015610230573d6000803e3d6000fd5b505050506040513d602081101561024657600080fd5b5051600755600254604080517f517807e400000000000000000000000000000000000000000000000000000000815230600482015290516001600160a01b039092169163517807e49160248082019260009290919082900301818387803b1580156102b057600080fd5b505af11580156102c4573d6000803e3d6000fd5b5050505050505050505050610acb806102de6000396000f3fe608060405234801561001057600080fd5b506004361061010b5760003560e01c806380e41ac1116100a2578063b8a7083a11610071578063b8a7083a146101e2578063b8f09942146101ea578063c19d93fb146101f2578063ca6158cb1461021e578063ea8a1af0146102265761010b565b806380e41ac1146101a957806380f474f6146101b1578063a035b1fe146101b9578063b46300ec146101da5761010b565b806357c4b262116100de57806357c4b262146101895780636177a9f8146101915780637022b58e146101995780637150d8ae146101a15761010b565b806308551a531461011057806337143233146101345780634c4cea5a1461015357806355be04ad1461016d575b600080fd5b61011861022e565b604080516001600160a01b039092168252519081900360200190f35b6101516004803603602081101561014a57600080fd5b503561023d565b005b61015b610311565b60408051918252519081900360200190f35b610175610317565b604080519115158252519081900360200190f35b61015b61048a565b610118610490565b61017561049f565b6101186106e9565b6101186106f8565b61015b610707565b6101c161070d565b6040805163ffffffff9092168252519081900360200190f35b610151610720565b6101186107d4565b6101186107e3565b6101fa6107f2565b6040518082600481111561020a57fe5b60ff16815260200191505060405180910390f35b6101c1610802565b610151610815565b6004546001600160a01b031681565b6005546001600160a01b0316331461025457600080fd5b6009541561026157600080fd5b6003600554600160c01b900460ff16600481111561027b57fe5b1461028557600080fd5b600581111561029357600080fd5b6009819055600354600554604080516366eee6b560e01b81526001600160a01b03928316600482015230602482015260448101859052905191909216916366eee6b591606480830192600092919082900301818387803b1580156102f657600080fd5b505af115801561030a573d6000803e3d6000fd5b5050505050565b60085481565b600080600554600160c01b900460ff16600481111561033257fe5b1461033c57600080fd5b60008054604080516370a0823160e01b815230600482015290516001600160a01b03909216916370a0823191602480820192602092909190829003018186803b15801561038857600080fd5b505afa15801561039c573d6000803e3d6000fd5b505050506040513d60208110156103b257600080fd5b505160055490915063ffffffff600160a01b90910481166014011681101561040b5760405162461bcd60e51b815260040180806020018281038252602c815260200180610a6b602c913960400191505060405180910390fd5b6005805460ff60c01b1916600160c01b17905560025460408051636452fca160e01b815230600482015290516001600160a01b0390921691636452fca19160248082019260009290919082900301818387803b15801561046a57600080fd5b505af115801561047e573d6000803e3d6000fd5b50505050600191505090565b60065481565b6002546001600160a01b031681565b6005546000906001600160a01b031633146104b957600080fd5b6002600554600160c01b900460ff1660048111156104d357fe5b146104dd57600080fd5b60008054604080516370a0823160e01b815230600482015290516001600160a01b03909216916370a0823191602480820192602092909190829003018186803b15801561052957600080fd5b505afa15801561053d573d6000803e3d6000fd5b505050506040513d602081101561055357600080fd5b505160008054600480546005546040805163a9059cbb60e01b81526001600160a01b0393841694810194909452600160a01b90910463ffffffff166024840152519495509091169263a9059cbb92604480840193602093929083900390910190829087803b1580156105c457600080fd5b505af11580156105d8573d6000803e3d6000fd5b505050506040513d60208110156105ee57600080fd5b5050600080546005546040805163a9059cbb60e01b81526001600160a01b038084166004830152600160a01b90930463ffffffff16860360248201529051919092169263a9059cbb92604480820193602093909283900390910190829087803b15801561065a57600080fd5b505af115801561066e573d6000803e3d6000fd5b505050506040513d602081101561068457600080fd5b50506005805460ff60c01b1916600360c01b1790554260085560025460408051630956cfb760e11b815230600482015290516001600160a01b03909216916312ad9f6e9160248082019260009290919082900301818387803b15801561046a57600080fd5b6005546001600160a01b031681565b6000546001600160a01b031681565b60095481565b600554600160a01b900463ffffffff1681565b6004546001600160a01b0316331461073757600080fd5b6001600554600160c01b900460ff16600481111561075157fe5b1461075b57600080fd5b6005805460ff60c01b1916600160c11b17905560025460408051638ff06ab760e01b815230600482015290516001600160a01b0390921691638ff06ab79160248082019260009290919082900301818387803b1580156107ba57600080fd5b505af11580156107ce573d6000803e3d6000fd5b50505050565b6003546001600160a01b031681565b6001546001600160a01b031681565b600554600160c01b900460ff1681565b600354600160a01b900463ffffffff1681565b6004546001600160a01b031633148061083857506005546001600160a01b031633145b61084157600080fd5b6000600554600160c01b900460ff16600481111561085b57fe5b148061087e57506001600554600160c01b900460ff16600481111561087c57fe5b145b61088757600080fd5b60008054604080516370a0823160e01b815230600482015290516001600160a01b03909216916370a0823191602480820192602092909190829003018186803b1580156108d357600080fd5b505afa1580156108e7573d6000803e3d6000fd5b505050506040513d60208110156108fd57600080fd5b505160055490915063ffffffff600160a01b9091048116601401168110610a0b57600080546005546040805163a9059cbb60e01b81526001600160a01b039283166004820152602481018690529051919092169263a9059cbb92604480820193602093909283900390910190829087803b15801561097a57600080fd5b505af115801561098e573d6000803e3d6000fd5b505050506040513d60208110156109a457600080fd5b505060025460408051637d44a00d60e11b815230600482015290516001600160a01b039092169163fa89401a9160248082019260009290919082900301818387803b1580156109f257600080fd5b505af1158015610a06573d6000803e3d6000fd5b505050505b6005805460ff60c01b1916600160c21b179055600254604080516320ad1b7d60e21b815230600482015290516001600160a01b03909216916382b46df49160248082019260009290919082900301818387803b1580156102f657600080fdfe47726561746572207468616e206f7220657161756c7320746f2032302043617368206e656365737361727921a265627a7a723158203ae1148eb95f20513b937e0b8a96bf7e42aa2867a8f5e22c30c8a348d653657c64736f6c634300050c0032a265627a7a72315820eb4a1e12da11dcff69ecc2acdce523ddb3de5c086f6306521394c030e4c6b7ba64736f6c634300050c0032";

    public static final String FUNC_ADMIN = "admin";

    public static final String FUNC_CASHCONTRACTADDRESS = "cashContractAddress";

    public static final String FUNC_DEREGISTERITEM = "deregisterItem";

    public static final String FUNC_ESCROWS = "escrows";

    public static final String FUNC_ITEMS = "items";

    public static final String FUNC_OWNER = "owner";

    public static final String FUNC_PURCHASEIDTOESCROW = "purchaseIdToEscrow";

    public static final String FUNC_PURCHASEITEM = "purchaseItem";

    public static final String FUNC_PURCHASERECORDCONTRACTADDRESS = "purchaseRecordContractAddress";

    public static final String FUNC_RATINGRECORDCONTRACTADDRESS = "ratingRecordContractAddress";

    public static final String FUNC_REGISTERITEM = "registerItem";

    public static final String FUNC_SETPAYMENTMETHOD = "setPaymentMethod";

    public static final String FUNC_SETPURCHASERECORDCONTRACT = "setPurchaseRecordContract";

    public static final String FUNC_SETRATINGRECORDCONTRACT = "setRatingRecordContract";

    public static final String FUNC_TRANSFEROWNERSHIP = "transferOwnership";

    public static final Event NEWESCROW_EVENT = new Event("NewEscrow", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Address>() {}));
    ;

    public static final Event OWNERSHIPTRANSFERRED_EVENT = new Event("OwnershipTransferred", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}));
    ;

    @Deprecated
    protected EscrowFactory(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected EscrowFactory(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected EscrowFactory(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected EscrowFactory(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public List<NewEscrowEventResponse> getNewEscrowEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(NEWESCROW_EVENT, transactionReceipt);
        ArrayList<NewEscrowEventResponse> responses = new ArrayList<NewEscrowEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            NewEscrowEventResponse typedResponse = new NewEscrowEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.seller = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.buyer = (String) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.itemId = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.purchaseId = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse._address = (String) eventValues.getNonIndexedValues().get(2).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<NewEscrowEventResponse> newEscrowEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, NewEscrowEventResponse>() {
            @Override
            public NewEscrowEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(NEWESCROW_EVENT, log);
                NewEscrowEventResponse typedResponse = new NewEscrowEventResponse();
                typedResponse.log = log;
                typedResponse.seller = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.buyer = (String) eventValues.getIndexedValues().get(1).getValue();
                typedResponse.itemId = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.purchaseId = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
                typedResponse._address = (String) eventValues.getNonIndexedValues().get(2).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<NewEscrowEventResponse> newEscrowEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(NEWESCROW_EVENT));
        return newEscrowEventFlowable(filter);
    }

    public List<OwnershipTransferredEventResponse> getOwnershipTransferredEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(OWNERSHIPTRANSFERRED_EVENT, transactionReceipt);
        ArrayList<OwnershipTransferredEventResponse> responses = new ArrayList<OwnershipTransferredEventResponse>(valueList.size());
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
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(OWNERSHIPTRANSFERRED_EVENT, log);
                OwnershipTransferredEventResponse typedResponse = new OwnershipTransferredEventResponse();
                typedResponse.log = log;
                typedResponse.previousOwner = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.newOwner = (String) eventValues.getIndexedValues().get(1).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<OwnershipTransferredEventResponse> ownershipTransferredEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(OWNERSHIPTRANSFERRED_EVENT));
        return ownershipTransferredEventFlowable(filter);
    }

    public RemoteFunctionCall<String> admin() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_ADMIN, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<String> cashContractAddress() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_CASHCONTRACTADDRESS, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<TransactionReceipt> deregisterItem(BigInteger itemId) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_DEREGISTERITEM, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint16(itemId)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<String> escrows(BigInteger param0) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_ESCROWS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<Tuple6<BigInteger, BigInteger, String, BigInteger, BigInteger, Boolean>> items(BigInteger param0) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_ITEMS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint32(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint32>() {}, new TypeReference<Uint32>() {}, new TypeReference<Address>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Bool>() {}));
        return new RemoteFunctionCall<Tuple6<BigInteger, BigInteger, String, BigInteger, BigInteger, Boolean>>(function,
                new Callable<Tuple6<BigInteger, BigInteger, String, BigInteger, BigInteger, Boolean>>() {
                    @Override
                    public Tuple6<BigInteger, BigInteger, String, BigInteger, BigInteger, Boolean> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple6<BigInteger, BigInteger, String, BigInteger, BigInteger, Boolean>(
                                (BigInteger) results.get(0).getValue(), 
                                (BigInteger) results.get(1).getValue(), 
                                (String) results.get(2).getValue(), 
                                (BigInteger) results.get(3).getValue(), 
                                (BigInteger) results.get(4).getValue(), 
                                (Boolean) results.get(5).getValue());
                    }
                });
    }

    public RemoteFunctionCall<String> owner() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_OWNER, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<String> purchaseIdToEscrow(BigInteger param0) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_PURCHASEIDTOESCROW, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<TransactionReceipt> purchaseItem(BigInteger itemId) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_PURCHASEITEM, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint32(itemId)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<String> purchaseRecordContractAddress() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_PURCHASERECORDCONTRACTADDRESS, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<String> ratingRecordContractAddress() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_RATINGRECORDCONTRACTADDRESS, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<TransactionReceipt> registerItem(BigInteger itemId, BigInteger price) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_REGISTERITEM, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint32(itemId), 
                new org.web3j.abi.datatypes.generated.Uint32(price)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> setPaymentMethod(String _address) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_SETPAYMENTMETHOD, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _address)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> setPurchaseRecordContract(String _address) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_SETPURCHASERECORDCONTRACT, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _address)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> setRatingRecordContract(String _address) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_SETRATINGRECORDCONTRACT, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _address)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> transferOwnership(String newOwner) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_TRANSFEROWNERSHIP, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, newOwner)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    @Deprecated
    public static EscrowFactory load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new EscrowFactory(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static EscrowFactory load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new EscrowFactory(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static EscrowFactory load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new EscrowFactory(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static EscrowFactory load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new EscrowFactory(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<EscrowFactory> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider, String _cashContractAddress, String _purchaseRecordAddress, String _ratingRecordContractAddress) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _cashContractAddress), 
                new org.web3j.abi.datatypes.Address(160, _purchaseRecordAddress), 
                new org.web3j.abi.datatypes.Address(160, _ratingRecordContractAddress)));
        return deployRemoteCall(EscrowFactory.class, web3j, credentials, contractGasProvider, BINARY, encodedConstructor);
    }

    public static RemoteCall<EscrowFactory> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider, String _cashContractAddress, String _purchaseRecordAddress, String _ratingRecordContractAddress) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _cashContractAddress), 
                new org.web3j.abi.datatypes.Address(160, _purchaseRecordAddress), 
                new org.web3j.abi.datatypes.Address(160, _ratingRecordContractAddress)));
        return deployRemoteCall(EscrowFactory.class, web3j, transactionManager, contractGasProvider, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<EscrowFactory> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, String _cashContractAddress, String _purchaseRecordAddress, String _ratingRecordContractAddress) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _cashContractAddress), 
                new org.web3j.abi.datatypes.Address(160, _purchaseRecordAddress), 
                new org.web3j.abi.datatypes.Address(160, _ratingRecordContractAddress)));
        return deployRemoteCall(EscrowFactory.class, web3j, credentials, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<EscrowFactory> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, String _cashContractAddress, String _purchaseRecordAddress, String _ratingRecordContractAddress) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _cashContractAddress), 
                new org.web3j.abi.datatypes.Address(160, _purchaseRecordAddress), 
                new org.web3j.abi.datatypes.Address(160, _ratingRecordContractAddress)));
        return deployRemoteCall(EscrowFactory.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    public static class NewEscrowEventResponse extends BaseEventResponse {
        public String seller;

        public String buyer;

        public BigInteger itemId;

        public BigInteger purchaseId;

        public String _address;
    }

    public static class OwnershipTransferredEventResponse extends BaseEventResponse {
        public String previousOwner;

        public String newOwner;
    }
}
