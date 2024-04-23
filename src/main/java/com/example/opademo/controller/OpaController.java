package com.example.opademo.controller;


import com.example.opademo.service.OpaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class OpaController {

    private final OpaService opaService;

    @GetMapping("/user/{id}")
    public Mono<ResponseEntity<String>> getUserDecision(@PathVariable(name = "id") int userId) {
        // Retrieve the decision asynchronously
        return opaService.getOPADecision(userId)
                .map(decision -> ResponseEntity.ok("Decision: " + decision))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
}