package com.example.TradingCryptoPlatformApplication.service;

import com.example.TradingCryptoPlatformApplication.domain.OrderType;
import com.example.TradingCryptoPlatformApplication.model.Coin;
import com.example.TradingCryptoPlatformApplication.model.Order;
import com.example.TradingCryptoPlatformApplication.model.OrderItem;
import com.example.TradingCryptoPlatformApplication.model.User;
import org.aspectj.weaver.ast.Or;

import java.util.List;

public interface OrderService {

    Order createOrder(User user, OrderItem orderItem, OrderType orderType);

    Order getOrderById(Long orderId) throws Exception;

    List<Order> getAllOrderOfUser(Long userId, OrderType orderType, String assetSymbol);

    Order processOrder(Coin coin, double quantity, OrderType orderType, User user) throws Exception;


}
