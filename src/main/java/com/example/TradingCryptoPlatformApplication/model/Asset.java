package com.example.TradingCryptoPlatformApplication.model;


import jakarta.persistence.*;
import lombok.Data;


@Data
@Entity
public class Asset {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private double quantity;

    private double buyPrice;

    // Many to one as one coin has many assets
    @ManyToOne
    private Coin coin;

    // Many to one as one user has many assets
    @ManyToOne
    private User user;
}
