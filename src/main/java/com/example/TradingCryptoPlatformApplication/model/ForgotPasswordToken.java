package com.example.TradingCryptoPlatformApplication.model;


import com.example.TradingCryptoPlatformApplication.domain.VerifcationType;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class ForgotPasswordToken {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;

    @OneToOne
    private com.example.TradingCryptoPlatformApplication.model.User user;

    private String otp;

    private VerifcationType verificationType;

    private String sendTo;
}
