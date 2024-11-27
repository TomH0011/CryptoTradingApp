package com.example.TradingCryptoPlatformApplication.controller;

import com.example.TradingCryptoPlatformApplication.request.ForgotPasswordTokenRequest;
import com.example.TradingCryptoPlatformApplication.domain.VerifcationType;
import com.example.TradingCryptoPlatformApplication.model.ForgotPasswordToken;
import com.example.TradingCryptoPlatformApplication.model.User;
import com.example.TradingCryptoPlatformApplication.model.VerificationCode;
import com.example.TradingCryptoPlatformApplication.request.ResetPasswordRequest;
import com.example.TradingCryptoPlatformApplication.response.ApiResponse;
import com.example.TradingCryptoPlatformApplication.response.AuthResponse;
import com.example.TradingCryptoPlatformApplication.service.EmailService;
import com.example.TradingCryptoPlatformApplication.service.ForgotPasswordService;
import com.example.TradingCryptoPlatformApplication.service.UserService;
import com.example.TradingCryptoPlatformApplication.service.VerificationCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.TradingCryptoPlatformApplication.utils.OtpUtils;

import java.util.UUID;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private VerificationCodeService verificationCodeService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private ForgotPasswordService forgotPasswordService;


    @PostMapping("/api/users/profile")
    public ResponseEntity<User> getUserProfile(@RequestHeader("Authorization") String jwt) {
        User user=userService.findUserProfileByJwt(jwt);

        return new ResponseEntity<User>(user, HttpStatus.OK);
    }


    @PostMapping("/api/users/verification/{verificationType}/send-otp")
    public ResponseEntity<String> sendVerificationOtp(
            @RequestHeader("Authorization") String jwt,
            @PathVariable VerifcationType verificationType) throws Exception {

        User user=userService.findUserProfileByJwt(jwt);

        VerificationCode verificationCode = verificationCodeService
                .getVerificationCodeByUser(user.getId());

        if (verificationCode == null) {
            verificationCode = verificationCodeService
                    .sendVerificationCode(user, verificationType);
        }

        if (verificationType.equals(VerifcationType.EMAIL)) {
            emailService.sendVerifationEmail(user.getEmail(),
                    verificationCode.getOtp());
        }

        return new ResponseEntity<>(
                "Verification OTP Sent Successfully", HttpStatus.OK);
    }


    @GetMapping("/api/users/enable-two-factor/verify-otp/{otp}")
    public ResponseEntity<User> enableTwoFactorAuthentication(
            @PathVariable String otp,
            @RequestHeader("Authorization") String jwt) throws Exception {

        User user=userService.findUserProfileByJwt(jwt);

        VerificationCode verificationCode=verificationCodeService
                .getVerificationCodeByUser(user.getId());

        String sendTo=verificationCode.getVerificationType()
                .equals(VerifcationType.EMAIL)?
                verificationCode.getEmail():verificationCode.getMobile();

        boolean isVerified=verificationCode.getOtp().equals(otp);

        if (isVerified) {
            User updatedUser=userService.enableTwoFactorAuthentication(
                    verificationCode.getVerificationType(), sendTo, user);
            verificationCodeService.deleteVerificationCodeById(verificationCode);

            return new ResponseEntity<>(updatedUser, HttpStatus.OK);
        }

        throw new Exception("Wrong OTP");
    }


    @PostMapping("/auth/users/reset-password/send-otp")
    public ResponseEntity<AuthResponse> sendForgotPasswordOtp(
            @RequestBody ForgotPasswordTokenRequest req) throws Exception {

        User user=userService.findUserByEmail(req.getSendTo());

        String otp = OtpUtils.generateOtp();
        UUID uuid = UUID.randomUUID();
        String id = uuid.toString();

        ForgotPasswordToken token = forgotPasswordService.findByUser(user.getId());

        if (token == null) {
            token = forgotPasswordService.createToken(user,
                    id,
                    otp,
                    req.getVerificationType(),
                    req.getSendTo());
        }

        if (req.getVerificationType().equals(VerifcationType.EMAIL)){
            emailService.sendVerifationEmail(
                    user.getEmail(),
                    token.getOtp());
        }
        AuthResponse response=new AuthResponse();
        response.setSession(token.getId());
        response.setMessage("Forgot Password OTP Sent Successfully");


        return new ResponseEntity<>(response, HttpStatus.OK);
    }



    @GetMapping("/auth/users/reset-password/verify-otp")
    public ResponseEntity<ApiResponse> resetPasswordOtp(
            @RequestParam String id,
            @RequestBody ResetPasswordRequest req) throws Exception {

        ForgotPasswordToken forgotPasswordToken=forgotPasswordService.findById(id);

        boolean isVerified=forgotPasswordToken.getOtp().equals(req.getOtp());

        if (isVerified) {
            userService.updatePassword(forgotPasswordToken.getUser(), req.getPassword());
            ApiResponse res = new ApiResponse();
            res.setMessage("Password Updated Successfully");
            return new ResponseEntity<>(res, HttpStatus.ACCEPTED);
        }
        throw new Exception("Wrong OTP");
    }
}
