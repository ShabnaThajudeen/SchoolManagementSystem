package com.eduexcellence.feesms.api;

import java.net.URI;
import java.time.LocalDate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eduexcellence.feesms.domain.StudentFeePayment;
import com.eduexcellence.feesms.repo.StudentFeePaymentRepo;

@RestController
public class PayFeeApi {
	private static final Logger LOGGER = LoggerFactory.getLogger(PayFeeApi.class);
	
	@Autowired
	private StudentFeePaymentRepo feePaymentRepo;
	
	@PostMapping("/fees/{studentid}/{amount}")
	public ResponseEntity<StudentFeePayment> makePayment(@PathVariable("studentid") Integer studentId, @PathVariable("amount") Float amount){
		
		StudentFeePayment feePayment = new StudentFeePayment();
		feePayment.setStudentId(studentId);
		feePayment.setAmountPaid(amount);
		feePayment.setPaidDate(LocalDate.now());
		
		StudentFeePayment feePaid = feePaymentRepo.save(feePayment);
		System.out.println(feePaid);
		
		LOGGER.info("Payment Complete.");
		
		return ResponseEntity.created(URI.create("http://localhost:8082/fees/" + feePaid.getFeeId().toString())).body(feePaid);
	}
	
	
	
	

}
