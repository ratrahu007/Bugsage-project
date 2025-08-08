package com.rahul.bugsage.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthResponse {
	private String token;
	private String username;
	private String role;
	private boolean emailVerified;
}
