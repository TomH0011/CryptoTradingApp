package com.example.TradingCryptoPlatformApplication.service;

import com.example.TradingCryptoPlatformApplication.domain.VerifcationType;
import com.example.TradingCryptoPlatformApplication.model.ForgotPasswordToken;
import com.example.TradingCryptoPlatformApplication.model.User;

public interface ForgotPasswordService {

    ForgotPasswordToken createToken(User user,
                                    String id,
                                    String otp,
                                    VerifcationType verifcationType,
                                    String sendTo);

    ForgotPasswordToken findById(String id);

    ForgotPasswordToken findByUser(Long userId);

    void deleteToken(ForgotPasswordToken token);
}
