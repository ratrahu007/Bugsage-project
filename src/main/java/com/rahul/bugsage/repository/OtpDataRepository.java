package com.rahul.bugsage.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rahul.bugsage.entity.Otpdata;

public interface OtpDataRepository extends JpaRepository<Otpdata,Long>{
	Optional<Otpdata> findByContactAndOtpAndVerifiedFalse(String contact, String otp);
	
    Optional<Otpdata> findTopByContactAndOtpAndTypeOrderByIdDesc(String contact, String otp, String type);
}
