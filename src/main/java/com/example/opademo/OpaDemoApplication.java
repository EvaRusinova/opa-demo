package com.example.opademo;

import com.example.opademo.config.OpaClient;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class OpaDemoApplication implements ApplicationRunner {


    public static void main(String[] args) {
        SpringApplication.run(OpaDemoApplication.class, args);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public OpaClient opaClient() {
        return new OpaClient();
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        opaClient().loadPolicyFromFile("policies/policies.rego");
    }
}

