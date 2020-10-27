package com.ecommerce.domain;

import lombok.Data;

@Data
public class Cash {
    private String walletAddress;
    private String privateKey;
    private double chargeAmount;
}
