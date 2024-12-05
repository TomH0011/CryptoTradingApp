package com.example.TradingCryptoPlatformApplication.controller;

import com.example.TradingCryptoPlatformApplication.model.Coin;
import com.example.TradingCryptoPlatformApplication.model.User;
import com.example.TradingCryptoPlatformApplication.model.Watchlist;
import com.example.TradingCryptoPlatformApplication.service.CoinService;
import com.example.TradingCryptoPlatformApplication.service.UserService;
import com.example.TradingCryptoPlatformApplication.service.WatchlistService;
import jdk.jshell.spi.ExecutionControl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/watchlist")
public class WatchlistController {

    @Autowired
    private WatchlistService watchlistService;

    @Autowired
    private UserService userService;

    @Autowired
    private CoinService coinService;

    @GetMapping("/user")
    public ResponseEntity<Watchlist> getUserWatchlist(
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);
        Watchlist watchlist = watchlistService.findUserWatchlist(user.getId());
        return ResponseEntity.ok(watchlist);
    }

//    @PostMapping("/create")
//    public ResponseEntity<Watchlist> createWatchList(
//            @RequestHeader("Authorization") String jwt
//    ) throws Exception {
//        User user = userService.findUserProfileByJwt(jwt);
//
//        Watchlist createdWatchlist = watchlistService.createWatchlist(user);
//
//        return ResponseEntity.status(HttpStatus.CREATED).body(createdWatchlist);
//
//    }

    @GetMapping("/{watchlistId}")
    public ResponseEntity<Watchlist> getWatchlistById(
            @PathVariable Long watchlistId
    ) throws Exception{
        Watchlist watchlist = watchlistService.findById(watchlistId);
        return ResponseEntity.ok(watchlist);
    }

    @PatchMapping("/add/coin/{coinId}")
    public ResponseEntity<Coin> addItemToWatchlist(
            @RequestHeader("Authorization")String jwt,
            @PathVariable String coinId
    ) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);

        Coin coin = coinService.findById(coinId);
        Coin addedCoin = watchlistService.addItemToWatchList(coin, user);
        return ResponseEntity.ok(addedCoin);
    }
}
