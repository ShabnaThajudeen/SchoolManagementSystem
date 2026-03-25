package com.eduexcellence.studentms.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/students")
public class PayFeeApi {
	private static final Logger LOGGER = LoggerFactory.getLogger(PayFeeApi.class);
	
	@Autowired
	private WebClient webClient;
	
	@SuppressWarnings("rawtypes")
	@GetMapping("/payfee/{id}/{amount}")
	@CircuitBreaker(name = "feesmsclient", fallbackMethod = "payFeeApiFallBack")
	public Mono payFee(@PathVariable("id") Integer id, @PathVariable("amount") Float amount) {
		LOGGER.info("About to call feesms to make fee payment.");
		
		return webClient
				.post()
				.uri("/fees/" + id + "/" + amount)
				.retrieve()
				.bodyToMono(Object.class);
	}
	
	@SuppressWarnings({ "unused", "rawtypes" })
	private Mono payFeeApiFallBack(CallNotPermittedException ex) {
		
		LOGGER.error("Fallback is invoked");
		
		return Mono.just(new String("Try after some time!!!"));
	}

}
