package com.example.TradingCryptoPlatformApplication.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Data
public class Wallet {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    private User user;

    // Otherwise we get balance is null which causes issues when transferring funds
    private BigDecimal balance=BigDecimal.ZERO;
}
