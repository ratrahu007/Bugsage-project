package com.rahul.bugsage.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rahul.bugsage.dto.DebugResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This service class is responsible for communicating with the Gemini API.
 * It constructs the prompt and handles the HTTP request to get the bug explanation.
 */
@Service
public class GeminiService {

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    // Use the @Value annotation to inject the API key from application.properties.
    @Value("${gemini.api.key}")
    private String API_KEY;

    private final String API_URL = "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.5-flash-preview-05-20:generateContent?key=";

    public DebugResponse analyzeBug(String errorLog) {
        try {
            // Construct the prompt for the Gemini API.
            // It now asks for a detailed, structured JSON response with multiple fields.
            String prompt = String.format(
                "You are an expert Java developer and a debugging assistant. " +
                "Analyze the following Java error message. Based on the stack trace and exception type, " +
                "provide a clear, concise explanation of the probable cause of the error. " +
                "Also provide a solution to fix the bug and a suggestion for best practices. " +
                "The response should be a structured JSON object with the following format:\n\n" +
                "{\n" +
                "  \"solution\": \"A clear explanation of the solution.\",\n" +
                "  \"suggestions\": \"Best practices or next steps.\",\n" +
                "  \"visual_details\": {\n" +
                "    \"line_number\": <number_of_buggy_line_from_stacktrace>,\n" +
                "    \"summary\": \"A short summary of the bug on that line.\"\n" +
                "  }\n" +
                "}\n\n" +
                "Error Message:\n```\n%s\n```",
                errorLog
            );

            // The payload for the API request.
            Map<String, Object> payload = new HashMap<>();
            payload.put("contents", List.of(Map.of("role", "user", "parts", List.of(Map.of("text", prompt)))));

            // The generationConfig is crucial for a structured JSON response.
            Map<String, Object> generationConfig = new HashMap<>();
            generationConfig.put("responseMimeType", "application/json");
            Map<String, Object> responseSchema = new HashMap<>();
            responseSchema.put("type", "OBJECT");
            responseSchema.put("properties", Map.of(
                "solution", Map.of("type", "STRING"),
                "suggestions", Map.of("type", "STRING"),
                "visual_details", Map.of("type", "OBJECT", "properties", Map.of(
                    "line_number", Map.of("type", "NUMBER"),
                    "summary", Map.of("type", "STRING")
                ))
            ));
            generationConfig.put("responseSchema", responseSchema);
            payload.put("generationConfig", generationConfig);


            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<Map<String, Object>> request = new HttpEntity<>(payload, headers);

            // Perform the API call.
            String response = restTemplate.postForObject(API_URL + API_KEY, request, String.class);

            // Parse the JSON response from the API.
            JsonNode root = objectMapper.readTree(response);
            JsonNode textNode = root.path("candidates").get(0).path("content").path("parts").get(0).path("text");
            
            // Map the JSON directly to your DebugResponse DTO.
            return objectMapper.readValue(textNode.asText(), DebugResponse.class);

        } catch (JsonProcessingException e) {
            System.err.println("Error parsing Gemini API response: " + e.getMessage());
            return new DebugResponse("Failed to parse API response.", "N/A", null);
        } catch (Exception e) {
            System.err.println("An unexpected error occurred: " + e.getMessage());
            return new DebugResponse("An unexpected error occurred.", "N/A", null);
        }
    }
}
