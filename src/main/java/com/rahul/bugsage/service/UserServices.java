package com.rahul.bugsage.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.rahul.bugsage.dto.AuthRequest;
import com.rahul.bugsage.dto.AuthResponse;
import com.rahul.bugsage.dto.RegisterRequest;
import com.rahul.bugsage.entity.Otpdata;
import com.rahul.bugsage.entity.User;
import com.rahul.bugsage.repository.OtpDataRepository;
import com.rahul.bugsage.repository.UserRepository;
import com.rahul.bugsage.utility.JwtUtil;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServices {

	private final UserRepository userRepository;

	private final OtpDataRepository otpRepository;

	private final PasswordEncoder passwordEncoder;
	
	 @Autowired
	    private AuthenticationManager authenticationManager;

	    @Autowired
	    private CustomUserDetailsService userDetailsService;

	    @Autowired
	    private JwtUtil jwtUtil;

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
	
	
	public AuthResponse login(AuthRequest request) {
	    try {
	        authenticationManager.authenticate(
	            new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
	        );

	        // 🔍 Load full user info from DB
	        User user = userRepository.findByUsername(request.getUsername())
	            .orElseThrow(() -> new RuntimeException("User not found"));

	        // 📦 Prepare custom claims
	        Map<String, Object> claims = new HashMap<>();
	        claims.put("role", user.getRole());
	        claims.put("email", user.getEmail());
	        claims.put("userId", user.getId());

	        // ⏳ Define token validity (e.g., 24 hours)
	        long expiryMillis = 1000 * 60 * 60 * 24;

	        // 🔐 Generate token
	        String token = jwtUtil.generateToken(user.getUsername(), claims, expiryMillis);

	        // 📤 Send AuthResponse
	        return AuthResponse.builder()
	            .token(token)
	            .username(user.getUsername())
	            .role(user.getRole())
	            .emailVerified(user.getOtpData() != null && user.getOtpData().isVerified())
	            .build();

	    } catch (Exception e) {
	        throw new RuntimeException("Invalid username or password");
	    }
	}


}
