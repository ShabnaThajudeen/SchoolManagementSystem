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
public class StudentFeePaymentsApi {
	private static final Logger LOGGER = LoggerFactory.getLogger(StudentFeePaymentsApi.class);
	
	@Autowired
	private WebClient webClient;
	
	@SuppressWarnings("rawtypes")
	@GetMapping("/studentfeepayments/{id}")
	@CircuitBreaker(name = "feesmsclient", fallbackMethod = "studentFeePaymentsApiFallBack")
	public Mono getStudentFeesDetail(@PathVariable Integer id) {
		LOGGER.info("About to call feesms to get fee payment details for student id {}", id);
		
		return webClient
				.get()
				.uri("/fees/" + id)
				.retrieve()
				.bodyToMono(Object.class);
	}
	
	@SuppressWarnings({ "unused", "rawtypes" })
	private Mono studentFeePaymentsApiFallBack(CallNotPermittedException ex) {
		
		LOGGER.error("Fallback is invoked");
		
		return Mono.just(new String("Try after some time!!!"));
	}

}
