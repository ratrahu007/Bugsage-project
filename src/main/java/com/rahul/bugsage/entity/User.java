package com.rahul.bugsage.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name="users")
public class User {
	@Id
	@GeneratedValue(strategy  = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable=false)
	private String fullName;
	
	@Column(unique = true,nullable=false)
	private String username;
	
	@Column(unique = true,nullable=false)
	private String email;
	
	
	@Column(unique=true)
	private String mobile;
	
	@Column(nullable=false)
	private String password;
	
	
	private String role;
	
	private boolean enabled=true;
	
	private boolean emailVerified = false;
	
	private String otp;
	
	private Long otpExpirytime;
	
	
}
