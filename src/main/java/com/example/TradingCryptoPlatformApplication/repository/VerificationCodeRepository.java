package com.example.TradingCryptoPlatformApplication.repository;

import com.example.TradingCryptoPlatformApplication.model.VerificationCode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VerificationCodeRepository  extends JpaRepository<VerificationCode, Long> {

    public VerificationCode findByUserId(Long userId);
}
