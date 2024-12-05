package com.example.TradingCryptoPlatformApplication.response;


import lombok.Data;

// To receive responses from Stripe
@Data
public class PaymentResponse {
    private String payment_url;
}
