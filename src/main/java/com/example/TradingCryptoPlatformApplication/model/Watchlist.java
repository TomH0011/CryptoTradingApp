package com.example.TradingCryptoPlatformApplication.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Watchlist {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    // Each user ahs one watchlist
    @OneToOne
    private User user;

    // many watch list has many coin
    @ManyToMany
    private List<Coin> coins = new ArrayList<Coin>();
}
