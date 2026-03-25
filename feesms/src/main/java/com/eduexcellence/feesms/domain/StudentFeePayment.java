package com.eduexcellence.feesms.domain;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "fee_payment")
public class StudentFeePayment {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer feePaymentId;
	
	@Column(name = "student_id")
	private Integer studentId;
	
	@Column(name = "amount_paid")
	private Float amountPaid;
	
	@Column(name = "date_paid")
	private LocalDate paidDate;

	public StudentFeePayment() {
		super();		
	}

	public StudentFeePayment(Integer feeId, Integer studentId, Float amountPaid, LocalDate paidDate) {
		super();
		this.feePaymentId = feeId;
		this.studentId = studentId;
		this.amountPaid = amountPaid;
		this.paidDate = paidDate;
	}

	public Integer getFeeId() {
		return feePaymentId;
	}

	public void setFeeId(Integer feeId) {
		this.feePaymentId = feeId;
	}

	public Integer getStudentId() {
		return studentId;
	}

	public void setStudentId(Integer studentId) {
		this.studentId = studentId;
	}

	public Float getAmountPaid() {
		return amountPaid;
	}

	public void setAmountPaid(Float amountPaid) {
		this.amountPaid = amountPaid;
	}

	public LocalDate getPaidDate() {
		return paidDate;
	}

	public void setPaidDate(LocalDate paidDate) {
		this.paidDate = paidDate;
	}
}
