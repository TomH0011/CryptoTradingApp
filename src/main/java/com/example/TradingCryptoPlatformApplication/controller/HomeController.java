package com.example.TradingCryptoPlatformApplication.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "Welcome to the trading application";
    }

    @GetMapping("/api")
    public String secure() {
        return "Welcome to the trading application Secure";
    }
}
