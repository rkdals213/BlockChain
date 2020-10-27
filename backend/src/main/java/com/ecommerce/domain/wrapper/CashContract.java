package com.ecommerce.domain.wrapper;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
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
public class CashContract extends Contract {
    public static final String BINARY = "608060405234801561001057600080fd5b5064e8d4a510006003819055600080546001600160a01b03191633178082556001600160a01b0316815260016020526040902055610d02806100536000396000f3fe6080604052600436106100f35760003560e01c806340c10f191161008a578063a9059cbb11610059578063a9059cbb1461034a578063b09f126614610383578063d28d885214610398578063dd62ed3e146103ad576100f3565b806340c10f19146102bf57806370a08231146102fa57806395d89b411461032d578063a6f2ae3a14610342576100f3565b806323b872dd116100c657806323b872dd14610227578063313ce5671461026a57806332424aa3146102955780633eaaf86b146102aa576100f3565b806306fdde03146100f85780630754617214610182578063095ea7b3146101b357806318160ddd14610200575b600080fd5b34801561010457600080fd5b5061010d6103e8565b6040805160208082528351818301528351919283929083019185019080838360005b8381101561014757818101518382015260200161012f565b50505050905090810190601f1680156101745780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b34801561018e57600080fd5b50610197610406565b604080516001600160a01b039092168252519081900360200190f35b3480156101bf57600080fd5b506101ec600480360360408110156101d657600080fd5b506001600160a01b038135169060200135610415565b604080519115158252519081900360200190f35b34801561020c57600080fd5b50610215610519565b60408051918252519081900360200190f35b34801561023357600080fd5b506101ec6004803603606081101561024a57600080fd5b506001600160a01b0381358116916020810135909116906040013561051f565b34801561027657600080fd5b5061027f61074c565b6040805160ff9092168252519081900360200190f35b3480156102a157600080fd5b5061027f610751565b3480156102b657600080fd5b50610215610756565b3480156102cb57600080fd5b506102f8600480360360408110156102e257600080fd5b506001600160a01b03813516906020013561075c565b005b34801561030657600080fd5b506102156004803603602081101561031d57600080fd5b50356001600160a01b03166107f4565b34801561033957600080fd5b5061010d61080f565b6101ec61082a565b34801561035657600080fd5b506101ec6004803603604081101561036d57600080fd5b506001600160a01b0381351690602001356109f5565b34801561038f57600080fd5b5061010d610b0e565b3480156103a457600080fd5b5061010d610b2b565b3480156103b957600080fd5b50610215600480360360408110156103d057600080fd5b506001600160a01b0381358116916020013516610b4b565b604080518082019091526004815263086c2e6d60e31b602082015290565b6000546001600160a01b031681565b600080821180156104355750336000908152600160205260409020548211155b61047e576040805162461bcd60e51b815260206004820152601560248201527424b739bab33334b1b4b2b73a103130b630b731b29760591b604482015290519081900360640190fd5b3360009081526002602090815260408083206001600160a01b03871684529091529020546104b2908363ffffffff610b7616565b3360008181526002602090815260408083206001600160a01b03891680855290835292819020949094558351868152935191937f8c5be1e5ebec7d5bd14f71427d1e84f3dd0314c0f7b2291e5b200ac8c7c3b925929081900390910190a350600192915050565b60035490565b600080821161056d576040805162461bcd60e51b81526020600482015260156024820152742bb937b7339030b6b7bab73a1037b31031b0b9b41760591b604482015290519081900360640190fd5b6001600160a01b0384166000908152600160205260409020548211156105d2576040805162461bcd60e51b815260206004820152601560248201527424b739bab33334b1b4b2b73a103130b630b731b29760591b604482015290519081900360640190fd5b6001600160a01b0384166000908152600260209081526040808320338452909152902054821115610643576040805162461bcd60e51b8152602060048201526016602482015275496e73756666696369656e7420616c6c6f77616e636560501b604482015290519081900360640190fd5b6001600160a01b0384166000908152600260209081526040808320338452909152902054610677908363ffffffff610bd716565b6001600160a01b0385166000818152600260209081526040808320338452825280832094909455918152600190915220546106b8908363ffffffff610bd716565b6001600160a01b0380861660009081526001602052604080822093909355908516815220546106ed908363ffffffff610b7616565b6001600160a01b0384166000818152600160209081526040918290209390935580518581529051919233927fddf252ad1be2c89b69c2b068fc378daa952ba7f163c4a11628f55a4df523b3ef9281900390910190a35060019392505050565b600090565b600081565b60035481565b6000546001600160a01b0316331461077357600080fd5b789f4f2726179a224501d762422c946590d91000000000000000811061079857600080fd5b6001600160a01b0382166000908152600160205260409020546107c1908263ffffffff610b7616565b6001600160a01b0383166000908152600160205260409020556003546107ed908263ffffffff610b7616565b6003555050565b6001600160a01b031660009081526001602052604090205490565b6040805180820190915260018152604360f81b602082015290565b600080546001600160a01b031633141561084357600080fd5b67016345785d8a000034101561088a5760405162461bcd60e51b8152600401808060200182810382526022815260200180610cac6022913960400191505060405180910390fd5b600080546001600160a01b0316815260016020526040902054670de0b6b3a764000034620186a00204908111156108ff576040805162461bcd60e51b8152602060048201526014602482015273496e73756666696369656e742062616c616e636560601b604482015290519081900360640190fd5b600080546040516001600160a01b03909116913480156108fc02929091818181858888f19350505050158015610939573d6000803e3d6000fd5b50600080546001600160a01b0316815260016020526040902054610963908263ffffffff610bd716565b600080546001600160a01b0316815260016020526040808220929092553381522054610995908263ffffffff610b7616565b3360008181526001602090815260408083209490945590548351858152935192936001600160a01b03909116927fddf252ad1be2c89b69c2b068fc378daa952ba7f163c4a11628f55a4df523b3ef929181900390910190a3600191505090565b60008082118015610a155750336000908152600160205260409020548211155b610a5e576040805162461bcd60e51b815260206004820152601560248201527424b739bab33334b1b4b2b73a103130b630b731b29760591b604482015290519081900360640190fd5b33600090815260016020526040902054610a7e908363ffffffff610bd716565b33600090815260016020526040808220929092556001600160a01b03851681522054610ab0908363ffffffff610b7616565b6001600160a01b0384166000818152600160209081526040918290209390935580518581529051919233927fddf252ad1be2c89b69c2b068fc378daa952ba7f163c4a11628f55a4df523b3ef9281900390910190a350600192915050565b604051806040016040528060018152602001604360f81b81525081565b60405180604001604052806004815260200163086c2e6d60e31b81525081565b6001600160a01b03918216600090815260026020908152604080832093909416825291909152205490565b600082820183811015610bd0576040805162461bcd60e51b815260206004820152601b60248201527f536166654d6174683a206164646974696f6e206f766572666c6f770000000000604482015290519081900360640190fd5b9392505050565b6000610bd083836040518060400160405280601e81526020017f536166654d6174683a207375627472616374696f6e206f766572666c6f77000081525060008184841115610ca35760405162461bcd60e51b81526004018080602001828103825283818151815260200191508051906020019080838360005b83811015610c68578181015183820152602001610c50565b50505050905090810190601f168015610c955780820380516001836020036101000a031916815260200191505b509250505060405180910390fd5b50505090039056fe67726561746572207468616e206f7220657175616c20746f20302e31206574686572a265627a7a723158208c95c86da96a379f29b59679c0fc2844b1cec58acb1560b2ea5278892ff0494864736f6c634300050c0032";

    public static final String FUNC__DECIMALS = "_decimals";

    public static final String FUNC__NAME = "_name";

    public static final String FUNC__SYMBOL = "_symbol";

    public static final String FUNC__TOTALSUPPLY = "_totalSupply";

    public static final String FUNC_ALLOWANCE = "allowance";

    public static final String FUNC_APPROVE = "approve";

    public static final String FUNC_BALANCEOF = "balanceOf";

    public static final String FUNC_BUY = "buy";

    public static final String FUNC_DECIMALS = "decimals";

    public static final String FUNC_MINT = "mint";

    public static final String FUNC_MINTER = "minter";

    public static final String FUNC_NAME = "name";

    public static final String FUNC_SYMBOL = "symbol";

    public static final String FUNC_TOTALSUPPLY = "totalSupply";

    public static final String FUNC_TRANSFER = "transfer";

    public static final String FUNC_TRANSFERFROM = "transferFrom";

    public static final Event APPROVAL_EVENT = new Event("Approval",
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {
            }, new TypeReference<Address>(true) {
            }, new TypeReference<Uint256>() {
            }));;

    public static final Event TRANSFER_EVENT = new Event("Transfer",
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {
            }, new TypeReference<Address>(true) {
            }, new TypeReference<Uint256>() {
            }));;

    @Deprecated
    protected CashContract(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice,
            BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected CashContract(String contractAddress, Web3j web3j, Credentials credentials,
            ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected CashContract(String contractAddress, Web3j web3j, TransactionManager transactionManager,
            BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected CashContract(String contractAddress, Web3j web3j, TransactionManager transactionManager,
            ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public List<ApprovalEventResponse> getApprovalEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(APPROVAL_EVENT, transactionReceipt);
        ArrayList<ApprovalEventResponse> responses = new ArrayList<ApprovalEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            ApprovalEventResponse typedResponse = new ApprovalEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.owner = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.spender = (String) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.value = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<ApprovalEventResponse> approvalEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, ApprovalEventResponse>() {
            @Override
            public ApprovalEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(APPROVAL_EVENT, log);
                ApprovalEventResponse typedResponse = new ApprovalEventResponse();
                typedResponse.log = log;
                typedResponse.owner = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.spender = (String) eventValues.getIndexedValues().get(1).getValue();
                typedResponse.value = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<ApprovalEventResponse> approvalEventFlowable(DefaultBlockParameter startBlock,
            DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(APPROVAL_EVENT));
        return approvalEventFlowable(filter);
    }

    public List<TransferEventResponse> getTransferEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(TRANSFER_EVENT, transactionReceipt);
        ArrayList<TransferEventResponse> responses = new ArrayList<TransferEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            TransferEventResponse typedResponse = new TransferEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.from = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.to = (String) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.value = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<TransferEventResponse> transferEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, TransferEventResponse>() {
            @Override
            public TransferEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(TRANSFER_EVENT, log);
                TransferEventResponse typedResponse = new TransferEventResponse();
                typedResponse.log = log;
                typedResponse.from = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.to = (String) eventValues.getIndexedValues().get(1).getValue();
                typedResponse.value = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<TransferEventResponse> transferEventFlowable(DefaultBlockParameter startBlock,
            DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(TRANSFER_EVENT));
        return transferEventFlowable(filter);
    }

    public RemoteFunctionCall<BigInteger> _decimals() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC__DECIMALS,
                Arrays.<Type>asList(), Arrays.<TypeReference<?>>asList(new TypeReference<Uint8>() {
                }));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<String> _name() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC__NAME,
                Arrays.<Type>asList(), Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {
                }));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<String> _symbol() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC__SYMBOL,
                Arrays.<Type>asList(), Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {
                }));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<BigInteger> _totalSupply() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC__TOTALSUPPLY,
                Arrays.<Type>asList(), Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {
                }));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<BigInteger> allowance(String _owner, String _spender) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_ALLOWANCE,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _owner),
                        new org.web3j.abi.datatypes.Address(160, _spender)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {
                }));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<TransactionReceipt> approve(String _spender, BigInteger _amount) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_APPROVE,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _spender),
                        new org.web3j.abi.datatypes.generated.Uint256(_amount)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<BigInteger> balanceOf(String _account) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_BALANCEOF,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _account)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {
                }));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<TransactionReceipt> buy(BigInteger weiValue) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_BUY,
                Arrays.<Type>asList(), Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function, weiValue);
    }

    public RemoteFunctionCall<BigInteger> decimals() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_DECIMALS,
                Arrays.<Type>asList(), Arrays.<TypeReference<?>>asList(new TypeReference<Uint8>() {
                }));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<TransactionReceipt> mint(String _receiver, BigInteger _amount) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_MINT,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _receiver),
                        new org.web3j.abi.datatypes.generated.Uint256(_amount)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<String> minter() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_MINTER,
                Arrays.<Type>asList(), Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {
                }));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<String> name() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_NAME,
                Arrays.<Type>asList(), Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {
                }));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<String> symbol() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_SYMBOL,
                Arrays.<Type>asList(), Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {
                }));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<BigInteger> totalSupply() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_TOTALSUPPLY,
                Arrays.<Type>asList(), Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {
                }));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<TransactionReceipt> transfer(String _recipient, BigInteger _amount) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_TRANSFER,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _recipient),
                        new org.web3j.abi.datatypes.generated.Uint256(_amount)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> transferFrom(String _sender, String _recipient, BigInteger _amount) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_TRANSFERFROM,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _sender),
                        new org.web3j.abi.datatypes.Address(160, _recipient),
                        new org.web3j.abi.datatypes.generated.Uint256(_amount)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    @Deprecated
    public static CashContract load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice,
            BigInteger gasLimit) {
        return new CashContract(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static CashContract load(String contractAddress, Web3j web3j, TransactionManager transactionManager,
            BigInteger gasPrice, BigInteger gasLimit) {
        return new CashContract(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static CashContract load(String contractAddress, Web3j web3j, Credentials credentials,
            ContractGasProvider contractGasProvider) {
        return new CashContract(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static CashContract load(String contractAddress, Web3j web3j, TransactionManager transactionManager,
            ContractGasProvider contractGasProvider) {
        return new CashContract(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<CashContract> deploy(Web3j web3j, Credentials credentials,
            ContractGasProvider contractGasProvider) {
        return deployRemoteCall(CashContract.class, web3j, credentials, contractGasProvider, BINARY, "");
    }

    public static RemoteCall<CashContract> deploy(Web3j web3j, TransactionManager transactionManager,
            ContractGasProvider contractGasProvider) {
        return deployRemoteCall(CashContract.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<CashContract> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice,
            BigInteger gasLimit) {
        return deployRemoteCall(CashContract.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<CashContract> deploy(Web3j web3j, TransactionManager transactionManager,
            BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(CashContract.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }

    public static class ApprovalEventResponse extends BaseEventResponse {
        public String owner;

        public String spender;

        public BigInteger value;
    }

    public static class TransferEventResponse extends BaseEventResponse {
        public String from;

        public String to;

        public BigInteger value;
    }
}
