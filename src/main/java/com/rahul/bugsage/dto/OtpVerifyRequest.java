package com.rahul.bugsage.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OtpVerifyRequest {

    @NotBlank(message = "contact number is required")
    private String contact;

    @NotBlank(message = "OTP is required")
    private String otp;
    
    private String type;
    
}
