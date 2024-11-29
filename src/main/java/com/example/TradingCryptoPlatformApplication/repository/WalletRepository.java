package com.example.TradingCryptoPlatformApplication.repository;

import com.example.TradingCryptoPlatformApplication.model.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WalletRepository extends JpaRepository<Wallet, Long> {

    Wallet findByUserId(Long userId);
}
