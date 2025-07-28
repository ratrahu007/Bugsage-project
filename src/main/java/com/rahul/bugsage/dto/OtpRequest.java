package com.rahul.bugsage.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OtpRequest {
	@NotBlank
	private String email;
	
	@NotBlank
	private String otp;
}
