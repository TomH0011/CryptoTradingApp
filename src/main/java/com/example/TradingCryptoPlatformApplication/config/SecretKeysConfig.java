package com.example.TradingCryptoPlatformApplication.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:Secret_keys.properties")
public class SecretKeysConfig {

}
