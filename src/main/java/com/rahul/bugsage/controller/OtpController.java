package com.rahul.bugsage.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.rahul.bugsage.dto.OtpRequest;
import com.rahul.bugsage.dto.OtpVerifyRequest;
import com.rahul.bugsage.service.OtpService;

@RestController
@RequestMapping("/api/otp")
public class OtpController {

    @Autowired
    private OtpService otpService;

    @PostMapping("/send")
    public ResponseEntity<String> sendOtp(@RequestBody OtpRequest otpRequest) {
        return ResponseEntity.ok(otpService.sendOtp(otpRequest));
    }

    @PostMapping("/verify")
    public ResponseEntity<String> verifyOtp(@RequestBody OtpVerifyRequest otpVerifyRequest) {
        return ResponseEntity.ok(otpService.verifyOtp(otpVerifyRequest));
    }

    @GetMapping("/test")
    public ResponseEntity<String> testOtpApi() {
        return ResponseEntity.ok("OTP API is accessible 🚀");
    }
}
