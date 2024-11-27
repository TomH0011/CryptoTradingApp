package com.example.TradingCryptoPlatformApplication.service;

import com.example.TradingCryptoPlatformApplication.config.JwtProvider;
import com.example.TradingCryptoPlatformApplication.domain.VerifcationType;
import com.example.TradingCryptoPlatformApplication.model.TwoFactorAuth;
import com.example.TradingCryptoPlatformApplication.model.User;
import com.example.TradingCryptoPlatformApplication.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class UserServiceImpl implements com.example.TradingCryptoPlatformApplication.service.UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User findUserProfileByJwt(String jwt) {
        String email = JwtProvider.getEmailFromToken(jwt);
        User user = userRepository.findByEmail(email);

        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return user;
    }

    @Override
    public User findUserByEmail(String email) {
        User user = userRepository.findByEmail(email);

        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return user;
    }

    @Override
    public User findUserById(Long userId) {
        Optional<User> user = userRepository.findById(userId);

        if (user.isEmpty()){
            throw new UsernameNotFoundException("User not found");
        }
        return user.get();
    }

    @Override
    public User enableTwoFactorAuthentication(VerifcationType verificationType,
                                              String sendTo,
                                              User user) {

        TwoFactorAuth twoFactorAuth = new TwoFactorAuth();
        twoFactorAuth.setEnabled(true);
        twoFactorAuth.setSentTo(verificationType);

        return userRepository.save(user);
    }


    @Override
    public User updatePassword(User user, String newPassword) {
        user.setPassword(newPassword);
        return userRepository.save(user);
    }
}
