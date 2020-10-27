package com.ecommerce.application;

import java.io.IOException;
import java.math.BigInteger;

public interface ICashContractService {
    int getBalance(String eoa) throws Exception;
    BigInteger buyCash(String eoa, String pk, double chargeAmount) throws Exception;
    String deploy() throws Exception;
}
