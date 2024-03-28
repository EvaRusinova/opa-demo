package com.example.opademo.controller;


import com.example.opademo.config.OpaClient;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;


@RestController
public class OpaController {
    @Autowired
    private OpaClient opaClient;

    @Autowired
    private RestTemplate restTemplate;


    @GetMapping("/user/{id}")
    public ResponseEntity<String> getUserDecision(@PathVariable(name = "id") int userId) {
        // Send parameter to OPA for decision-making
        String decision = getOPADecision(userId);

        // Return decision as the response
        return ResponseEntity.ok("Decision: " + decision);
    }


    private String getOPADecision(int id) {
        // Construct request body for OPA
        String requestBody = "{\"input\": " + id + "}";

        // Send request to OPA server
        String opaServerUrl = "http://localhost:8181/v1/data/my_policy_package/decision";
        ResponseEntity<String> opaResponse = restTemplate.postForEntity(opaServerUrl, requestBody, String.class);

        // Log the OPA response
        System.out.println("OPA Response: " + opaResponse.getBody());

        // Extract decision from OPA response
        if (opaResponse.getStatusCode().is2xxSuccessful()) {
            return extractDecisionFromResponse(opaResponse.getBody());
        } else {
            // Handle error response from OPA server
            return "Decision not found in response from OPA server";
        }
    }


    private String extractDecisionFromResponse(String responseBody) {
        try {
            // Parse the JSON response
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode responseJson = objectMapper.readTree(responseBody);

            // Check if the response contains the "result" field
            if (responseJson.has("result")) {
                // Extract the decision value
                return responseJson.get("result").asText();
            } else {
                // Handle case where "result" field is missing
                return "Decision field not found in response";
            }
        } catch (Exception e) {
            // Handle any parsing or other exceptions
            return "Error extracting decision from response: " + e.getMessage();
        }
    }
}
