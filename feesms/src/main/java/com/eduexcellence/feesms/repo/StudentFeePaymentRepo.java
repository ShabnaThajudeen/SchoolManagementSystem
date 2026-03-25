package com.eduexcellence.feesms.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eduexcellence.feesms.domain.StudentFeePayment;

@Repository
public interface StudentFeePaymentRepo extends JpaRepository<StudentFeePayment, Integer>{
	List<StudentFeePayment> findByStudentId(Integer id);
	Optional<StudentFeePayment> findById(Integer feeId);
}
