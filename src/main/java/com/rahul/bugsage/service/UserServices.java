package com.rahul.bugsage.service;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.rahul.bugsage.dto.RegisterRequest;
import com.rahul.bugsage.entity.Otpdata;
import com.rahul.bugsage.entity.User;
import com.rahul.bugsage.repository.OtpDataRepository;
import com.rahul.bugsage.repository.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServices {

	private final UserRepository userRepository;

	private final OtpDataRepository otpRepository;

	private final PasswordEncoder passwordEncoder;

	@Transactional
	public String registerUser(RegisterRequest request) {
		if (userRepository.existsByUsername(request.getUsername()) || userRepository.existsByEmail(request.getEmail())
				|| userRepository.existsByMobile(request.getMobile())) {
			throw new RuntimeException("User already exists with same username/email/Mobile");
		}
		
		Optional<Otpdata> verifiedOtp= otpRepository.findFirstByContactAndVerified(request.getEmail(), true);
		
		
		if(verifiedOtp.isEmpty())
		{
		verifiedOtp=otpRepository.findFirstByContactAndVerified(request.getMobile(), true);
		}
		if(verifiedOtp.isEmpty())
		{	
			throw new RuntimeException("OTP verification required for provided Email or Mobile");
		}
		
		User user= User.builder()
				.fullName(request.getFullName())
				.username(request.getUsername())
				.email(request.getEmail())
				.mobile(request.getMobile())
				.password(passwordEncoder.encode(request.getPassword()))
				.role("USER")
				.otpData(verifiedOtp.get())
				.build();
		userRepository.save(user);
		
		return "User registered Succuesfully";
	}
}
