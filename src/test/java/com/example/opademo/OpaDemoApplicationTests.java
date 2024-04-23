package com.example.opademo;

import com.example.opademo.service.OpaService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class OpaDemoApplicationTests {

	@Autowired
	private WebTestClient webTestClient;

	@MockBean
	private OpaService opaService;

	@Test
	public void testGetUserDecision() {
		// Mock the response from the OpaService
		when(opaService.getOPADecision(any(Integer.class))).thenReturn(Mono.just("ALLOW"));

		// Perform GET request and verify the response
		webTestClient.get().uri("/user/123")
				.exchange()
				.expectStatus().isOk()
				.expectBody(String.class).isEqualTo("Decision: ALLOW");
	}

	@Test
	public void testGetUserDecisionNotFound() {
		// Mock the response from the OpaService
		when(opaService.getOPADecision(any(Integer.class))).thenReturn(Mono.empty());

		// Perform GET request and verify the response
		webTestClient.get().uri("/user/456")
				.exchange()
				.expectStatus().isNotFound();
	}

	@Test
	public void testGetUserDecisionServerError() {
		// Mock the response from the OpaService to simulate a server error
		when(opaService.getOPADecision(any(Integer.class))).thenReturn(Mono.error(new RuntimeException("Internal Server Error")));

		// Perform GET request and verify the response
		webTestClient.get().uri("/user/789")
				.exchange()
				.expectStatus().is5xxServerError();
	}
}
