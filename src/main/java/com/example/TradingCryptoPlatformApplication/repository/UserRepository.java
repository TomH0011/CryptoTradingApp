package com.example.TradingCryptoPlatformApplication.repository;

import com.example.TradingCryptoPlatformApplication.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

// Updates info in the database
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
}
