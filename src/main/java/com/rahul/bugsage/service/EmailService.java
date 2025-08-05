package com.rahul.bugsage.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
@Service
public class EmailService {
	@Autowired
	private JavaMailSender mailSender;
	
	public void sendOtpEmail(String to, String otp) {
	    try {
	        SimpleMailMessage message = new SimpleMailMessage();
	        message.setTo(to);
	        message.setSubject("BugSage OTP Verification");
	        message.setText("Your OTP is " + otp + "\n\nValid for 5 minutes");

	        mailSender.send(message);
	        System.out.println("OTP email sent to " + to);
	    } catch (Exception e) {
	        System.err.println("Error sending OTP email: " + e.getMessage());
	        e.printStackTrace(); // log stack trace
	    }
	}

}
