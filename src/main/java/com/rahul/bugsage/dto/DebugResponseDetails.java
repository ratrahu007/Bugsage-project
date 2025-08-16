package com.rahul.bugsage.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DebugResponseDetails {
    @JsonProperty("line_number")
    private int lineNumber;
    private String summary;
}
