package com.example.opademo.config;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.nio.charset.StandardCharsets;


@Component
public class OpaClient {
    private static final Logger logger = LoggerFactory.getLogger(OpaClient.class);
    private static final String POLICY_URL = "http://localhost:8181/v1/policies/authentication_policy/resource_access_policy";
    @Autowired
    private RestTemplate restTemplate;

    public void loadPolicyFromFile(String filePath) {
        try {
            ClassPathResource resource = new ClassPathResource(filePath);
            String policyContent = StreamUtils.copyToString(resource.getInputStream(), StandardCharsets.UTF_8);
            logger.info("Loaded policy content from file {}: {}", filePath, policyContent);
            loadPolicy(policyContent);
        } catch (IOException e) {
            logger.error("Error loading policy from file: {}", filePath, e);
        }
    }

    private void loadPolicy(String policyContent) {
        logger.info("Policy content: {}", policyContent);
        try {
            restTemplate.put(POLICY_URL, policyContent, String.class);
            logger.info("Policy loaded successfully");
        } catch (Exception e) {
            logger.error("Error loading policy into OPA server: {}", e.getMessage(), e);
        }
    }
}