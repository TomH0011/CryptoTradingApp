package com.example.TradingCryptoPlatformApplication.service;

import com.example.TradingCryptoPlatformApplication.domain.OrderStatus;
import com.example.TradingCryptoPlatformApplication.domain.OrderType;
import com.example.TradingCryptoPlatformApplication.model.*;
import com.example.TradingCryptoPlatformApplication.repository.OrderItemRepository;
import com.example.TradingCryptoPlatformApplication.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService{

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private AssetService assetService;

    @Autowired
    private WalletService walletService;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Override
    public Order createOrder(User user, OrderItem orderItem, OrderType orderType) {
        double price = orderItem.getCoin().getCurrentPrice()*orderItem.getQuantity();

        Order order = new Order();

        order.setUser(user);
        order.setOrderItem(orderItem);
        order.setOrderType(orderType);
        order.setPrice(BigDecimal.valueOf(price));
        order.setTimeStamp(LocalDateTime.now());
        order.setStatus(OrderStatus.PENDING);

        return orderRepository.save(order);
    }

    @Override
    public Order getOrderById(Long orderId) throws Exception {
        return orderRepository.findById(orderId)
                .orElseThrow(()->new Exception("Order Not Found"));
    }

    @Override
    public List<Order> getAllOrderOfUser(Long userId, OrderType orderType, String assetSymbol) {
        return orderRepository.findByUserId(userId);
    }

    private OrderItem createOrderItem(Coin coin, double quantity, double buyPrice, double sellPrice){
        OrderItem orderItem = new OrderItem();
        orderItem.setCoin(coin);
        orderItem.setQuantity(quantity);
        orderItem.setBuyPrice(buyPrice);
        orderItem.setSellPrice(sellPrice);

        return orderItemRepository.save(orderItem);
    }

    // Transactional allows the method to delete from database if
    // payOrderPayment throws an error
    @Transactional
    public  Order buyAsset(Coin coin, double quantity, User user) throws Exception {
        if(quantity <= 0){
            throw new Exception("Quantity should be >0");
        }
        double buyPrice = coin.getCurrentPrice();

        OrderItem orderItem = createOrderItem(
                coin,
                quantity,
                buyPrice,
                0);

        Order order = createOrder(user, orderItem, OrderType.BUY);

        orderItem.setOrder(order);

        walletService.payOrderPayment(order, user);

        order.setStatus(OrderStatus.SUCCESS);
        order.setOrderType(OrderType.BUY);
        Order savedOrder=orderRepository.save(order);

        // Now to create Asset

        Asset oldAsset=assetService.findAssetByUserIdAndCoinId(order.getUser().getId(),
                order.getOrderItem().getCoin().getId());

        if(oldAsset==null){
            assetService.createAsset(user, orderItem.getCoin(), orderItem.getQuantity());
        } else {
            assetService.updateAsset(oldAsset.getId(), quantity);
        }

        return savedOrder;
    }


    @Transactional
    public  Order sellAsset(Coin coin, double quantity, User user) throws Exception {
        if(quantity <= 0){
            throw new Exception("Quantity should be >0");
        }
        double sellPrice = coin.getCurrentPrice();

        Asset assetToSell = assetService.findAssetByUserIdAndCoinId(
                user.getId(),
                coin.getId());

        double buyPrice = assetToSell.getBuyPrice();
        OrderItem orderItem = createOrderItem(
                coin,
                quantity,
                buyPrice,
                sellPrice);


        Order order = createOrder(user, orderItem, OrderType.SELL);
        orderItem.setOrder(order);

        if (assetToSell.getQuantity() >= quantity) {

            walletService.payOrderPayment(order, user);
            order.setStatus(OrderStatus.SUCCESS);
            order.setOrderType(OrderType.SELL);
            Order savedOrder = orderRepository.save(order);

            Asset updatedAsset = assetService.updateAsset(
                    assetToSell.getId(), -quantity
            );

            if (updatedAsset.getQuantity() * coin.getCurrentPrice() <= 1) {
                assetService.deleteAsset(updatedAsset.getId());
            }
            return savedOrder;
        }
        throw new Exception("Insufficient Qauntity To Sell");
    }


    @Override
    @Transactional
    public Order processOrder(
            Coin coin,
            double quantity,
            OrderType orderType,
            User user) throws Exception {

        if (orderType == OrderType.BUY){
            return buyAsset(coin,quantity,user);
        } else if (orderType == OrderType.SELL) {
            return sellAsset(coin, quantity, user);
        }
        throw new Exception("Invalid Order Type...");
    }
}
