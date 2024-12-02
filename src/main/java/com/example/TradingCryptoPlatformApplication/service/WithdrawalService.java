package com.example.TradingCryptoPlatformApplication.service;

import com.example.TradingCryptoPlatformApplication.model.User;
import com.example.TradingCryptoPlatformApplication.model.Withdrawal;

import java.util.List;

public interface WithdrawalService {

    Withdrawal requestWithdrawal(Long amount, User user);

    Withdrawal proceedWithdrawal(Long withdrawalId, boolean accept) throws Exception;

    List<Withdrawal> getUsersWithdrawalHistory(User user);

    List<Withdrawal> getAllWithdrawalRequest();
}
