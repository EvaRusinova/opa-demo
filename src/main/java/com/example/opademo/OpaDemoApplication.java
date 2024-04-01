package com.example.opademo;

import com.example.opademo.config.OpaClient;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
public class OpaDemoApplication implements ApplicationRunner {

    public static void main(String[] args) {
        SpringApplication.run(OpaDemoApplication.class, args);
    }

    @Bean
    public WebClient.Builder webClientBuilder() {
        return WebClient.builder();
    }

    @Bean
    public OpaClient opaClient(WebClient.Builder webClientBuilder) {
        return new OpaClient(webClientBuilder);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        opaClient(webClientBuilder()).loadPolicyFromFile("policies/subscription.rego");
    }
}