package com.example.TradingCryptoPlatformApplication.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.example.TradingCryptoPlatformApplication.domain.USER_ROLE;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String fullName;
    private String email;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @Embedded
    private com.example.TradingCryptoPlatformApplication.model.TwoFactorAuth twoFactorAuth=
            new com.example.TradingCryptoPlatformApplication.model.TwoFactorAuth();

    // Give different roles to different users i.e admin customer
    private USER_ROLE role = USER_ROLE.ROLE_CUSTOMER;

}
