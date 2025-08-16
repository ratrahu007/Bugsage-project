package com.rahul.bugsage.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.rahul.bugsage.dto.DebugRequest;
import com.rahul.bugsage.dto.DebugResponse;
import com.rahul.bugsage.service.GeminiService;

import jakarta.validation.Valid;

/**
 * This is the main REST controller for the application. It exposes an endpoint
 * to receive the bug details and returns the analysis result.
 */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/bug")
public class BugController {

    private final GeminiService geminiService;

    @Autowired
    public BugController(GeminiService geminiService) {
        this.geminiService = geminiService;
    }

    @PostMapping("/analyze")
    public DebugResponse analyzeBug(@Valid @RequestBody DebugRequest debugRequest) {
        // Corrected line: Call the service and return its response directly.
        return geminiService.analyzeBug(debugRequest.getErrorLog());
    }
}
