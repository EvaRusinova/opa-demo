package com.example.opademo.config;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;
import java.nio.charset.StandardCharsets;


@Component
@RequiredArgsConstructor
@Slf4j
public class OpaClient {

    private static final String POLICY_URL = "http://localhost:8181/v1/policies/authentication_policy/resource_access_policy";

    private final WebClient.Builder webClientBuilder;

    public void loadPolicyFromFile(String filePath) {
        try {
            ClassPathResource resource = new ClassPathResource(filePath);
            String policyContent = StreamUtils.copyToString(resource.getInputStream(), StandardCharsets.UTF_8);
            loadPolicy(policyContent);
        } catch (IOException e) {
            log.error("Error loading policy from file: {}", filePath, e);
        }
    }

    private void loadPolicy(String policyContent) {
        log.info("Policy content: {}", policyContent);
        try {
            webClientBuilder.build()
                    .put()
                    .uri(POLICY_URL)
                    .body(BodyInserters.fromValue(policyContent))
                    .retrieve()
                    .bodyToMono(String.class)
                    .subscribe(response -> log.info("Policy loaded successfully"));
        } catch (Exception e) {
            log.error("Error loading policy into OPA server: {}", e.getMessage(), e);
        }
    }
}