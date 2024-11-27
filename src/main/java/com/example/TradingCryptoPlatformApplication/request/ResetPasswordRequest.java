package com.example.TradingCryptoPlatformApplication.request;

import lombok.Data;

@Data
public class ResetPasswordRequest {
    private String Otp;
    private String Password;
}
