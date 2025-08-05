package com.rahul.bugsage.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

import jakarta.annotation.PostConstruct;

@Service
public class TwilioService {

    @Value("${twilio.account.sid}")
    private String accountsid;

    @Value("${twilio.auth.token}")
    private String authToken;

    @Value("${twilio.phone.number}")
    private String fromNumber;

    @PostConstruct
    public void init() {
        Twilio.init(accountsid, authToken); // 👈 Order is SID, then Token
    }

    public void sendOtpMobile(String toNumber, String otp) {
        String messageBody = "Your BugSage OTP is: " + otp;

        Message.creator(
                new PhoneNumber(toNumber),
                new PhoneNumber(fromNumber),
                messageBody
        ).create();
    }
}
