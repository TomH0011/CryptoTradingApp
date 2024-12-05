package com.example.TradingCryptoPlatformApplication.service;

import com.example.TradingCryptoPlatformApplication.domain.PaymentMethod;
import com.example.TradingCryptoPlatformApplication.domain.PaymentOrderStatus;
import com.example.TradingCryptoPlatformApplication.model.PaymentOrder;
import com.example.TradingCryptoPlatformApplication.model.User;
import com.example.TradingCryptoPlatformApplication.response.PaymentResponse;
import com.stripe.exception.StripeException;

public interface PaymentService {

    PaymentOrder createOrder(User user,
                                   Long amount,
                                   PaymentMethod paymentMethod);

    PaymentOrder getPaymentOrderById(Long id) throws Exception;

    Boolean proceedPaymentOrder(PaymentOrder paymentOrder, String paymentId);

//    PaymentResponse createRazorPayPaymentLink(User user, Long amount);

    PaymentResponse createStripePayPaymentLink(User user, Long amount, Long orderId) throws StripeException;

}
