package com.ecommerce.application;

import com.ecommerce.domain.Address;
import java.math.BigInteger;

public interface IEthereumService {
    String requestEth(String address) throws Exception;
    BigInteger getBalance(String address);

    Address getAddress(String address);
}
