package com.example.TradingCryptoPlatformApplication.service;

import com.example.TradingCryptoPlatformApplication.model.Coin;

import java.util.List;

public interface CoinService {
    List<Coin> getCoinList(int page) throws Exception;

    String GetMarketChart(String coinId, int days);

    String getCoinDetails(String coinId);

    Coin findById(String coinId);

    String searchCoin(String keyword);

    String getTop50CoinByMarketCapRank();

    String getTradingCoins();

}
