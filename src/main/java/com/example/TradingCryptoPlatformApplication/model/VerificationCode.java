package com.example.TradingCryptoPlatformApplication.model;


import com.example.TradingCryptoPlatformApplication.domain.VerifcationType;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class VerificationCode {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String otp;

    @OneToOne
    private com.example.TradingCryptoPlatformApplication.model.User user;

    private String email;

    private String mobile;

    private VerifcationType verificationType;
}
