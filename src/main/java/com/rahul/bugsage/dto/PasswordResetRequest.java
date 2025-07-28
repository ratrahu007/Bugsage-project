package com.rahul.bugsage.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PasswordResetRequest {
	@Email(message="Email must be Valid")
	private String email;
	
	@NotBlank(message="OTP is required")
	private String otp;
	
	@NotBlank(message="New Password is required")
	@Size(min=6, message="Password must be ar least 6 characters")
	private String password ;
}
