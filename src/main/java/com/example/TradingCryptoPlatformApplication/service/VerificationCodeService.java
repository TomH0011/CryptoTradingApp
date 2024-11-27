package com.example.TradingCryptoPlatformApplication.service;

import com.example.TradingCryptoPlatformApplication.domain.VerifcationType;
import com.example.TradingCryptoPlatformApplication.model.User;
import com.example.TradingCryptoPlatformApplication.model.VerificationCode;

public interface VerificationCodeService {

    VerificationCode sendVerificationCode(User user, VerifcationType verificationType);

    VerificationCode getVerificationCodeById(Long id) throws Exception;

    VerificationCode getVerificationCodeByUser(Long userId);

    void deleteVerificationCodeById(VerificationCode verificationCode);
}
