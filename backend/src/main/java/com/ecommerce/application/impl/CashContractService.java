package com.ecommerce.application.impl;

import com.ecommerce.application.ICashContractService;
import com.ecommerce.domain.CommonUtil;
import com.ecommerce.domain.CryptoUtil;
import com.ecommerce.domain.exception.ApplicationException;
import com.ecommerce.domain.wrapper.CashContract;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.datatypes.Function;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.core.methods.response.EthGetTransactionReceipt;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.DefaultGasProvider;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class CashContractService implements ICashContractService {
    private static final Logger log = LoggerFactory.getLogger(CashContractService.class);

    @Value("${eth.erc20.contract}")
    private String ERC20_TOKEN_CONTRACT;

    @Value("${eth.admin.address}")
    private String ADMIN_ADDRESS;

    @Value("${eth.admin.wallet.filename}")
    private String WALLET_RESOURCE;

    @Value("${eth.encrypted.password}")
    private String PASSWORD;

    @Value("${spring.web3j.client-address}")
    private String NETWORK_URL;

    private CashContract cashContract;
    private ContractGasProvider contractGasProvider = new DefaultGasProvider();
    private Credentials credentials;

    @Autowired
    private Web3j web3j;

    /**
     * TODO Sub PJT Ⅱ 과제 3 토큰 잔액 조회
     * 
     * @param eoa
     * @return
     */
    @Override
    public int getBalance(String eoa) throws Exception {

        ClassPathResource resource = new ClassPathResource(WALLET_RESOURCE);
        // Path adminWalletFile = Paths.get(resource.getURI());
        // List<String> content = Files.readAllLines(adminWalletFile);
        byte[] bdata = FileCopyUtils.copyToByteArray(resource.getInputStream());
        String data = new String(bdata, StandardCharsets.UTF_8);

        web3j = Web3j.build(new HttpService(NETWORK_URL)); // defaults to http://localhost:8545/
        credentials = WalletUtils.loadJsonCredentials(PASSWORD, data);

        cashContract = CashContract.load(ERC20_TOKEN_CONTRACT, web3j, credentials, contractGasProvider);
        return cashContract.balanceOf(eoa).send().intValue();
    }

    @Override
    public BigInteger buyCash(String eoa, String pk, double chargeAmount) throws Exception {

        web3j = Web3j.build(new HttpService(NETWORK_URL)); // defaults to http://localhost:8545/

        credentials = Credentials.create(pk);
        cashContract = CashContract.load(ERC20_TOKEN_CONTRACT, web3j, credentials, contractGasProvider);
        BigDecimal cashAmount = new BigDecimal(chargeAmount * Math.pow(10, 18));
        BigInteger cash = cashAmount.toBigInteger();
        TransactionReceipt transactionReceipt = cashContract.buy(cash).send();

        return cashContract.balanceOf(eoa).send();
    }

    @Override
    public String deploy() throws Exception {

        ClassPathResource resource = new ClassPathResource(WALLET_RESOURCE);
        // Path adminWalletFile = Paths.get(resource.getURI());
        // List<String> content = Files.readAllLines(adminWalletFile);
        byte[] bdata = FileCopyUtils.copyToByteArray(resource.getInputStream());
        String data = new String(bdata, StandardCharsets.UTF_8);

        Web3j web3 = Web3j.build(new HttpService(NETWORK_URL)); // defaults to http://localhost:8545/
        Credentials credentials = WalletUtils.loadJsonCredentials(PASSWORD, data);
        cashContract = CashContract.deploy(web3, credentials, contractGasProvider).send();

        return cashContract.getContractAddress();
    }
}
