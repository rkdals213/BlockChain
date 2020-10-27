package com.ecommerce.application.impl;

import com.ecommerce.application.IItemService;
import com.ecommerce.domain.Item;
import com.ecommerce.domain.ItemJpa;
import com.ecommerce.domain.User;
import com.ecommerce.domain.exception.ApplicationException;
import com.ecommerce.domain.repository.IItemRepository;
import com.ecommerce.domain.repository.IItemRepository2;
import com.ecommerce.domain.repository.IUserRepository;
import com.ecommerce.domain.wrapper.EscrowFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.DefaultGasProvider;

import javax.transaction.Transactional;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
@Transactional
public class ItemService implements IItemService {
	public static final Logger logger = LoggerFactory.getLogger(ItemService.class);
	@Value("${eth.erc20.contract}")
	private String ERC20_TOKEN_CONTRACT;

	@Value("${eth.item.contract}")
	private String ITEM_CONTRACT;

	@Value("${eth.purchase.record.contract}")
	private String PURCHASE_CONTRACT_ADDRESS;

	@Value("${eth.rating.record.contract}")
	private String RATING_CONTRACT_ADDRESS;

	@Value("${eth.admin.address}")
	private String ADMIN_ADDRESS;

	@Value("${eth.admin.wallet.filename}")
	private String WALLET_RESOURCE;

	@Value("${eth.encrypted.password}")
	private String PASSWORD;

	@Value("${spring.web3j.client-address}")
	private String NETWORK_URL;

	private IItemRepository itemRepository;
	private EscrowFactory escrowFactory;
	private ContractGasProvider contractGasProvider = new DefaultGasProvider();
	private Credentials credentials;

	@Autowired
	private IUserRepository userRepository;

	@Autowired
	IItemRepository2 itemRepository2;

	@Autowired
	private Web3j web3j;

	@Autowired
	public ItemService(IItemRepository itemRepository) {
		this.itemRepository = itemRepository;
	}

	@Override
	public List<Item> list() {
		return this.itemRepository.list();
	}

	@Override
	public List<Item> pageList(int page) {
		return this.itemRepository.pageList(page);
	}

	@Override
	public Item get(final long id) throws Exception {
		// ClassPathResource resource = new ClassPathResource(WALLET_RESOURCE);
		// byte[] bdata = FileCopyUtils.copyToByteArray(resource.getInputStream());
		// String data = new String(bdata, StandardCharsets.UTF_8);

		// Web3j web3 = Web3j.build(new HttpService(NETWORK_URL)); // defaults to
		// http://localhost:8545/
		// Credentials credentials = WalletUtils.loadJsonCredentials(PASSWORD, data);

		// escrowFactory = EscrowFactory.load(ITEM_CONTRACT, web3, credentials,
		// contractGasProvider);

		// Tuple6<BigInteger, BigInteger, String, BigInteger, BigInteger, Boolean> t =
		// escrowFactory
		// .items(BigInteger.valueOf(id)).send();

		return this.itemRepository.get(id);
	}

	@Override
	public List<Item> getByUser(int uid) {
		return this.itemRepository.getByUser(uid);
	}

	@Override
	public List<Item> getByUser(String category, String name, int page) {
		User user = userRepository.getUserId(name);
		return this.itemRepository.getByUserName(category, user.getId(), page);
	}

	@Override
	public List<Item> getByName(String category, String title, int page) {
		return this.itemRepository.getByName(category, title, page);
	}

	@Override
	public List<Item> getByCategory(String category, int page) {
		return this.itemRepository.getByCategory(category, page);
	}

	@Override
	public List<Item> getByMainCategory(String category) {
		return this.itemRepository.getByMainCategory(category);
	}

	/**
	 * TODO Sub PJT Ⅲ 과제 3 상품 등록 시 상품 정보를 저장한다.
	 * 
	 * @param item
	 * @return Item
	 * @throws Exception
	 */
	@Override
	public Item register(final Item item) throws Exception {
		long id = this.itemRepository.create(item);

		Web3j web3 = Web3j.build(new HttpService(NETWORK_URL));

		credentials = Credentials.create(item.getPk());

		escrowFactory = EscrowFactory.load(ITEM_CONTRACT, web3, credentials, contractGasProvider);

		TransactionReceipt tr = escrowFactory.registerItem(BigInteger.valueOf(id), BigInteger.valueOf(item.getPrice()))
				.send();

		return this.itemRepository.get(id);
	}

	/**
	 * TODO Sub PJT Ⅲ 과제 3 상품 판매 취소
	 * 
	 * @param id 상품 id
	 * @return Item
	 */
	@Override
	public int delete(final long id) {
		return this.itemRepository.delete(id);
	}

	@Override
	public int viewCountUpdate(final long id) {
		return this.itemRepository.viewCountUpdate(id);
	}

	/**
	 * 상품 정보 업데이트
	 * 
	 * @param item
	 * @return
	 * @throws Exception
	 */
	public Item update(final Item item) throws Exception {
		Item itemStored = this.itemRepository.get(item.getId());
		if (itemStored == null)
			throw new ApplicationException("해당 상품을 찾을 수 없습니다.");

		if (item.getPrice() == 0)
			item.setPrice(itemStored.getPrice());

		// Web3j web3 = Web3j.build(new HttpService(NETWORK_URL));

		// credentials = Credentials.create(item.getPk());

		// escrowFactory = EscrowFactory.load(ITEM_CONTRACT, web3, credentials,
		// contractGasProvider);

		// TransactionReceipt tr = escrowFactory
		// .registerItem(BigInteger.valueOf(item.getId()),
		// BigInteger.valueOf(item.getPrice())).send();

		return this.itemRepository.get(item.getId());
	}

	@Override
	public int imageUpdate(long id, int image) {
		return this.itemRepository.imageUpdate(id, image);
	}

	@Override
	public int complete(long id, String eoa, String pk) throws Exception {
		Web3j web3 = Web3j.build(new HttpService(NETWORK_URL));
		System.out.println(pk);
		credentials = Credentials.create(pk);

		escrowFactory = EscrowFactory.load(ITEM_CONTRACT, web3, credentials, contractGasProvider);

		TransactionReceipt tr = escrowFactory.deregisterItem(BigInteger.valueOf(id)).send();
		// return this.itemRepository.complete(id);
		return 0;
	}

	@Override
	public List<Item> getByOnlyName(String name, int page) {
		return this.itemRepository.getByOnlyName(name, page);
	}

	@Override
	public int getLengthByUser(String category, String name) {
		User user = userRepository.getUserId(name);
		return this.itemRepository.getLengthByUser(category, user.getId());
	}

	@Override
	public int getLengthByname(String category, String name) {
		return this.itemRepository.getLengthByName(category, name);
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
		escrowFactory = EscrowFactory.deploy(web3, credentials, contractGasProvider, ERC20_TOKEN_CONTRACT,
				PURCHASE_CONTRACT_ADDRESS, RATING_CONTRACT_ADDRESS).send();

		return escrowFactory.getContractAddress();
	}

	@Override
	public int changeProgressTrue(long id) {
		return this.itemRepository.changeProgressTrue(id);
	}

	@Override
	public int changeProgressFalse(long id) {
		// TODO Auto-generated method stub
		return this.itemRepository.changeProgressFalse(id);
	}

	///////////////////////////////////////////// jpa 구간
	///////////////////////////////////////////// //////////////////////////////////////////////

	@Override
	public Page<ItemJpa> getByNameContaining(int page, String name) {
		return this.itemRepository2.getByNameContainingAndProgressAndAvailable(
				PageRequest.of(page, 12, Sort.Direction.DESC, "id"), name, false, true);
	}

	@Override
	public Page<ItemJpa> getBySeller(int page, String seller) {
		User user = userRepository.getUserId(seller);
		System.out.println(user);
		return this.itemRepository2.getBySellerAndProgressAndAvailable(
				PageRequest.of(page, 12, Sort.Direction.DESC, "id"), user.getId(), false, true);
	}

	@Override
	public Page<ItemJpa> getByNameContainingAndCategoryContaining(int page, String name, String category) {
		return this.itemRepository2.getByNameContainingAndCategoryContainingAndProgressAndAvailable(
				PageRequest.of(page, 12, Sort.Direction.DESC, "id"), name, category, false, true);
	}

	@Override
	public Page<ItemJpa> getBySellerAndCategoryContaining(int page, String seller, String category) {
		User user = userRepository.getUserId(seller);
		System.out.println(user);
		return this.itemRepository2.getBySellerAndCategoryContainingAndProgressAndAvailable(
				PageRequest.of(page, 12, Sort.Direction.DESC, "id"), user.getId(), category, false, true);
	}

}
