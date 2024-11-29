package com.example.TradingCryptoPlatformApplication.repository;

import com.example.TradingCryptoPlatformApplication.model.Coin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoinRepository extends JpaRepository<Coin, String> {
}
