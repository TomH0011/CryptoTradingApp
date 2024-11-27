package com.example.TradingCryptoPlatformApplication.request;

import com.example.TradingCryptoPlatformApplication.domain.VerifcationType;
import lombok.Data;

@Data
public class ForgotPasswordTokenRequest {

    private String sendTo;
    private VerifcationType verificationType;
    private String Otp;
}
