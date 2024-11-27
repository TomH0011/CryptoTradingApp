package com.example.TradingCryptoPlatformApplication.service;

import com.example.TradingCryptoPlatformApplication.domain.VerifcationType;
import com.example.TradingCryptoPlatformApplication.model.User;
import com.example.TradingCryptoPlatformApplication.model.VerificationCode;
import com.example.TradingCryptoPlatformApplication.repository.VerificationCodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.TradingCryptoPlatformApplication.utils.OtpUtils;

import java.util.Optional;

@Service
public class VerificationCodeServiceImpl implements com.example.TradingCryptoPlatformApplication.service.VerificationCodeService {

    @Autowired
    private VerificationCodeRepository verificationCodeRepository;

    @Override
    public VerificationCode sendVerificationCode(User user, VerifcationType verificationType) {
        VerificationCode verificationCode1 = new VerificationCode();
        verificationCode1.setOtp(OtpUtils.generateOtp());
        // I wrapped verification type in a string in a verification type because java (the guy didn't beware)
        verificationCode1.setVerificationType(verificationType);
        verificationCode1.setUser(user);


        return verificationCodeRepository.save(verificationCode1);
    }

    @Override
    public VerificationCode getVerificationCodeById(Long id) throws Exception {
        Optional<VerificationCode> verificationCode =
                verificationCodeRepository.findById(id);
        if (verificationCode.isPresent()) {
            return verificationCode.get();
        }
        throw new Exception("Verification code not found...");
    }

    @Override
    public VerificationCode getVerificationCodeByUser(Long userId) {
        return verificationCodeRepository.findByUserId(userId);

    }


    @Override
    public void deleteVerificationCodeById(VerificationCode verificationCode) {
        verificationCodeRepository.delete(verificationCode);

    }
}
