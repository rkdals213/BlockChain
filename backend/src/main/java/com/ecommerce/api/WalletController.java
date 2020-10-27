package com.ecommerce.api;

import com.ecommerce.application.ICashContractService;
import com.ecommerce.application.IWalletService;
import com.ecommerce.domain.Cash;
import com.ecommerce.domain.Wallet;
import com.ecommerce.domain.exception.ApplicationException;
import com.ecommerce.domain.exception.EmptyListException;
import com.ecommerce.domain.exception.NotFoundException;

import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContextException;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.EthGetBalance;
import org.web3j.protocol.http.HttpService;
import org.web3j.utils.Convert;
import org.web3j.utils.Convert.Unit;

import javax.validation.Valid;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class WalletController {
	public static final Logger logger = LoggerFactory.getLogger(WalletController.class);

	private IWalletService walletService;
	@Autowired
	private ICashContractService cashContractService;

	@Autowired
	public WalletController(IWalletService walletService) {
		Assert.notNull(walletService, "walletService 개체가 반드시 필요!");
		this.walletService = walletService;
	}

	/**
	 * TODO Sub PJT Ⅱ 과제 1 지갑 등록
	 * 
	 * @param wallet
	 */
	@ApiOperation(value = "Register wallet of user")
	@RequestMapping(value = "/wallets", method = RequestMethod.POST)
	public Wallet register(@Valid @RequestBody Wallet wallet) {
		walletService.register(wallet);
		return wallet;
	}

	/**
	 * TODO Sub PJT Ⅱ 과제 1 지갑 조회 by address
	 * 
	 * @param address 지갑 주소
	 */
	@ApiOperation(value = "Fetch wallet by address")
	@RequestMapping(value = "/wallets/{address}", method = RequestMethod.GET)
	public Wallet get(@PathVariable String address) {
		return walletService.get(address);
	}

	/**
	 * TODO Sub PJT Ⅱ 과제 1 지갑 조회 by user's id
	 * 
	 * @param uid 사용자 id
	 * @throws IOException
	 */
	@ApiOperation(value = "Fetch wallet of user")
	@RequestMapping(value = "/wallets/of/{uid}", method = RequestMethod.GET)
	public Wallet getByUser(@PathVariable long uid) throws IOException {
		// Wallet wallet = walletService.get(uid);
		// Web3j web3 = Web3j.build(new HttpService("http://localhost:8545/"));
		// EthGetBalance balanceWei = web3
		// .ethGetBalance(wallet.getAddress(), DefaultBlockParameterName.LATEST)
		// .send();
		// BigDecimal balanceInEther =
		// Convert.fromWei(balanceWei.getBalance().toString(), Unit.ETHER);
		// wallet.setBalance(balanceInEther);

		return walletService.get(uid);
	}

	@RequestMapping(value = "/wallets/temp/{eoa}", method = RequestMethod.GET)
	public int getBalance(@PathVariable String eoa) throws Exception {
		return cashContractService.getBalance(eoa);
	}

	/**
	 * TODO Sub PJT Ⅱ 과제 1 이더 충전 요청
	 * 
	 * @param address 지갑 주소
	 * @throws Exception
	 */
	@ApiOperation(value = "Request ether")
	@RequestMapping(value = "/wallets/{address}", method = RequestMethod.PUT)
	public Wallet requestEth(@PathVariable String address) throws Exception { // 테스트 가능하도록 일정 개수의 코인을 충전해준다.
		System.out.println("이더 충전 들어옴!!");
		Wallet wallet = null;
		try {
			wallet = walletService.requestEth(address);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception();
		}
		return wallet;
	}

	/**
	 * TODO Sub PJT Ⅱ 과제 1 캐쉬 충전 요청
	 * 
	 * @param address 지갑 주소
	 * @throws Exception
	 */
	@ApiOperation(value = "Buy cash")
	@RequestMapping(value = "/wallets/cash", method = RequestMethod.PUT)
	public Wallet buyCash(@RequestBody Cash cash) throws Exception { // 캐쉬 구매
		Wallet wallet = null;
		try {
			wallet = walletService.buyCash(cash.getWalletAddress(), cash.getPrivateKey(), cash.getChargeAmount());
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception();
		}

		return wallet;
	}

	@RequestMapping(value = "/contract", method = RequestMethod.GET)
	public String deploy() {
		String answer = null;
		try {
			answer = cashContractService.deploy();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return answer;
	}
}
