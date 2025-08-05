package com.rahul.bugsage.service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rahul.bugsage.dto.OtpRequest;
import com.rahul.bugsage.dto.OtpVerifyRequest;
import com.rahul.bugsage.entity.Otpdata;
import com.rahul.bugsage.repository.OtpDataRepository;

@Service
public class OtpService {

    @Autowired
    private EmailService emailService;

    @Autowired
    private TwilioService twilioService;

    @Autowired
    private OtpDataRepository otpDataRepository;

    private String generateOtp() {
        return String.format("%06d", new Random().nextInt(999999));
    }

    public String sendOtp(OtpRequest request) {
        String contact = request.getContact();
        String type = request.getType(); // "email" or "mobile"
        String otp = generateOtp();

        Otpdata otpData = new Otpdata();
        otpData.setContact(contact);
        otpData.setOtp(otp);
        otpData.setExpiryTime(LocalDateTime.now().plusMinutes(5));
        otpData.setVerified(false);
        otpData.setUsed(false);
        otpData.setType(type.toLowerCase());

        otpDataRepository.save(otpData);

        if (type.equalsIgnoreCase("email")) {
            emailService.sendOtpEmail(contact, otp);
        } else if (type.equalsIgnoreCase("mobile")) {
            twilioService.sendOtpMobile(contact, otp);
        } else {
            return "Invalid type. Use 'email' or 'mobile'";
        }

        return "OTP sent successfully via " + type;
    }

    public String verifyOtp(OtpVerifyRequest request) {
        Optional<Otpdata> optionalOtp = otpDataRepository.findTopByContactAndOtpAndTypeOrderByIdDesc(
                request.getContact(), request.getOtp(), request.getType().toLowerCase());

        if (optionalOtp.isPresent()) {
            Otpdata otpData = optionalOtp.get();

            if (otpData.isUsed()) return "OTP already used ❌";
            if (otpData.getExpiryTime().isBefore(LocalDateTime.now())) return "OTP expired ⌛";

            otpData.setVerified(true);
            otpData.setUsed(true);
            otpDataRepository.save(otpData);
            return "OTP verified ✅";
        }

        return "Invalid OTP or contact ❌";
    }
}
