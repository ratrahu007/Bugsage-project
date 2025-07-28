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
public class RegisterRequest {
	@NotBlank(message="Full name is required")
	private String fullName;
	
	@NotBlank(message="Username is required")
	private String username;
	
	@Email(message="Invalid email Format")
	@NotBlank(message="Email is required")
	private String email;
	
	
	@NotBlank(message="Mobile number is required")
	private String mobile;
	
	@NotBlank(message="Password is required")
	@Size(min=6,message="Password must be at least 6 charachters")
	private String password;
}
