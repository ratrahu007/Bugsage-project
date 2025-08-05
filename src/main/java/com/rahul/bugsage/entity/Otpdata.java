package com.rahul.bugsage.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name ="otp_data")
public class Otpdata {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	
	private String contact;
	
	private String otp;
	
	private String type;
	
	private boolean verified;
	
	private LocalDateTime expiryTime;
	
	private boolean used;
	
	@OneToOne(mappedBy ="otpData")
	private User user;
}
