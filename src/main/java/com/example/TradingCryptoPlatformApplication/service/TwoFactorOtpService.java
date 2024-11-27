package com.example.TradingCryptoPlatformApplication.service;

import com.example.TradingCryptoPlatformApplication.model.TwoFactorOTP;
import com.example.TradingCryptoPlatformApplication.model.User;

public interface TwoFactorOtpService {

    TwoFactorOTP createTwoFactorOtp(User user, String otp, String jwt);

    TwoFactorOTP findByUser(Long userId);

    TwoFactorOTP findById(String id);

    // Does otp in database match the users otp?
    boolean verifyTwoFactorOtp(TwoFactorOTP twoFactorOtp, String otp);

    void deleteTwoFactorOtp(TwoFactorOTP twoFactorOtp);
}
