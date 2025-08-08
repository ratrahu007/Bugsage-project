package com.rahul.bugsage.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rahul.bugsage.dto.AuthRequest;
import com.rahul.bugsage.dto.AuthResponse;
import com.rahul.bugsage.dto.RegisterRequest;
import com.rahul.bugsage.service.UserServices;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {
	
	private final UserServices userServices;
	
	@PostMapping("/register")
	public ResponseEntity<String> registerUser(@RequestBody RegisterRequest request)
	{
		String response = userServices.registerUser(request);
		
		  return ResponseEntity.ok(response);
	}
	
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
        AuthResponse response = userServices.login(request);
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/hello")
    public ResponseEntity<String> sayHello() {
        return ResponseEntity.ok("Hello, API is working!");
    }

}
