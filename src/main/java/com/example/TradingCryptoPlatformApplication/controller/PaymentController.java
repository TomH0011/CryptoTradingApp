package com.example.TradingCryptoPlatformApplication.controller;

import com.example.TradingCryptoPlatformApplication.domain.PaymentMethod;
import com.example.TradingCryptoPlatformApplication.model.PaymentOrder;
import com.example.TradingCryptoPlatformApplication.response.PaymentResponse;
import com.example.TradingCryptoPlatformApplication.service.PaymentService;
import com.example.TradingCryptoPlatformApplication.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.TradingCryptoPlatformApplication.model.User;

@RestController
@RequestMapping("/api")
public class PaymentController {

    @Autowired
    private UserService userService;

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/api/payment/{paymentMethod}/amount/{amount}")
    public ResponseEntity<PaymentResponse> paymentHandler(
            @PathVariable PaymentMethod paymentMethod,
            @PathVariable Long amount,
            @RequestHeader("Authorization") String jwt
            ) throws Exception{
        User user = userService.findUserProfileByJwt(jwt);

        PaymentResponse paymentResponse;

        PaymentOrder order = paymentService.createOrder(user, amount, paymentMethod);

        paymentResponse=paymentService.createStripePayPaymentLink(user, amount, order.getId());

        return new ResponseEntity<>(paymentResponse, HttpStatus.CREATED);
    }
}
