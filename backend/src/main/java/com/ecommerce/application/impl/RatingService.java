package com.ecommerce.application.impl;

import java.math.BigInteger;
import java.util.List;

import com.ecommerce.application.IRatingService;
import com.ecommerce.domain.Purchase;
import com.ecommerce.domain.Rating;
import com.ecommerce.domain.repository.IPurchaseRepository;
import com.ecommerce.domain.repository.IRatingRepository;
import com.ecommerce.domain.wrapper.Escrow;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.DefaultGasProvider;

@Service
public class RatingService implements IRatingService {
	private Credentials credentials;
	private Escrow escrow;
	@Value("${spring.web3j.client-address}")
	private String NETWORK_URL;
	@Autowired
	private Web3j web3j;
	@Value("${eth.item.contract}")
    private String ITEM_CONTRACT;
	private ContractGasProvider contractGasProvider = new DefaultGasProvider();

	private IPurchaseRepository purchaseRepository;
	
	public static final Logger logger = LoggerFactory.getLogger(RatingService.class);

	
	
	private IRatingRepository ratingRepository;

	@Autowired
	public RatingService(IRatingRepository ratingRepository, IPurchaseRepository purchaseRepository) {
		this.ratingRepository = ratingRepository;
        this.purchaseRepository = purchaseRepository;
	}

	@Override
	public List<Rating> list() {
		return this.ratingRepository.list();
	}

	@Override
	public double get(long userId) {
		List<Rating> rating = this.ratingRepository.get(userId);

		long score1 = 0;
		for (Rating r : rating) {
			score1 += r.getScore();
		}

		return (double) score1 / rating.size();
	}

	@Override
	public long register(final Rating rating) throws Exception {
		web3j = Web3j.build(new HttpService(NETWORK_URL));

        credentials = Credentials.create(rating.getPrivateKey());
		
		Purchase purchase = purchaseRepository.get(rating.getPurchasesId());
		
		escrow = Escrow.load(purchase.getContractAddress(), web3j, credentials, contractGasProvider);
		TransactionReceipt tr = escrow.evaluate(BigInteger.valueOf(rating.getScore())).send();
		
		System.out.println("평가 스마트컨트랙트 성공");
		return this.ratingRepository.create(rating);
	}

	@Override
	public int delete(final long id) {
		return this.ratingRepository.delete(id);
	}
}
