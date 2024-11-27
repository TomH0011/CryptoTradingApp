package com.example.TradingCryptoPlatformApplication.model;

import lombok.Data;

@Data
public class TwoFactorAuth {

    private boolean isEnabled = false;

    private com.example.TradingCryptoPlatformApplication.domain.VerifcationType sentTo;

}
