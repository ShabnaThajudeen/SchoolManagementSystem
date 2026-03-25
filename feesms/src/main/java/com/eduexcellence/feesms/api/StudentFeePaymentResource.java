package com.eduexcellence.feesms.api;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.eduexcellence.feesms.domain.StudentFeePayment;
import com.eduexcellence.feesms.repo.StudentFeePaymentRepo;

@RestController
public class StudentFeePaymentResource {
    private static final Logger LOGGER = LoggerFactory.getLogger(StudentFeePaymentResource.class);
	
	@Autowired
	private StudentFeePaymentRepo feePaymentRepo;
	
	@GetMapping("/fees/{id}")
	public List<StudentFeePayment> getStudentFeesPaymentDetails(@PathVariable Integer id){
		LOGGER.info("Finding Student Fees Details.");
		
		return feePaymentRepo.findByStudentId(id);
	}
	
	@GetMapping("/fees/payment/{id}")
	public ResponseEntity<StudentFeePayment> getStudentFeePayment(@PathVariable Integer id){
		LOGGER.info("Finding Student Fee Payment Detail.");
		
		Optional<StudentFeePayment> payment = feePaymentRepo.findById(id);
		if(payment.isEmpty()) {
			LOGGER.error("Payment details not found");
			return ResponseEntity.notFound().build();
		}
		LOGGER.info("Fetched Student Fee Payment Detail.");
		return ResponseEntity.ok(payment.get());
	}
}
