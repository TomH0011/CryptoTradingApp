package com.example.TradingCryptoPlatformApplication.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    // Quantity of the coin
    private double quantity;

    @ManyToOne
    private Coin coin;

    // in context of the coin again
    private double buyPrice;
    private double sellPrice;

    @JsonIgnore
    @OneToOne
    private Order order;

}
