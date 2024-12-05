package com.example.TradingCryptoPlatformApplication.repository;

import com.example.TradingCryptoPlatformApplication.model.PaymentOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentServiceRepository extends JpaRepository<PaymentOrder, Long> {
}
