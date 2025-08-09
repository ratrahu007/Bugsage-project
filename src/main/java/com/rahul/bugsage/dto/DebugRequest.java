package com.rahul.bugsage.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DebugRequest {

    @NotBlank(message = "errorLog cannot be blank")
    private String errorLog;
}
