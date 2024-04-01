package com.example.opademo.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class OpaService {

    private final WebClient.Builder webClientBuilder;
    private final ObjectMapper mapper;

    public Mono<String> getOPADecision(int id) {
        // Construct request body for OPA
        String requestBody = "{\"input\": " + id + "}";

        // Send request to OPA server
        String opaServerUrl = "http://localhost:8181/v1/data/my_policy_package/decision";
        return webClientBuilder.build()
                .post()
                .uri(opaServerUrl)
                .body(BodyInserters.fromValue(requestBody))
                .retrieve()
                .onStatus(HttpStatusCode::isError, response -> Mono.error(new RuntimeException("Error response from OPA server")))
                .bodyToMono(String.class)
                .flatMap(this::extractDecisionFromResponse);
    }

    private Mono<String> extractDecisionFromResponse(String responseBody) {
        try {
            // Parse the JSON response
            JsonNode responseJson = mapper.readTree(responseBody);

            // Check if the response contains the "result" field
            if (responseJson.has("result")) {
                // Extract the decision value
                return Mono.just(responseJson.get("result").asText());
            } else {
                // Handle case where "result" field is missing
                return Mono.error(new RuntimeException("Decision field not found in response"));
            }
        } catch (Exception e) {
            // Handle any parsing or other exceptions
            return Mono.error(new RuntimeException("Error extracting decision from response: " + e.getMessage()));
        }
    }
}