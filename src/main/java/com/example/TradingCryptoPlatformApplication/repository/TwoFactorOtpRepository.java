package com.example.TradingCryptoPlatformApplication.repository;

import com.example.TradingCryptoPlatformApplication.model.TwoFactorOTP;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TwoFactorOtpRepository extends JpaRepository<TwoFactorOTP, String> {
    TwoFactorOTP findByUserId(Long userId);
}
