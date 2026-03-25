package com.eduexcellence.studentms.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "student")
public class Student {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer studentId;
	
	@Column(name = "name")
	private String studentName;
	@Column(name = "class")
	private Integer studentGrade;
	
	public Student() {
		super();		
	}

	public Student(Integer studentId, String studentName, Integer studentGrade) {
		super();
		this.studentId = studentId;
		this.studentName = studentName;
		this.studentGrade = studentGrade;
	}

	public Integer getStudentId() {
		return studentId;
	}

	public void setStudentId(Integer studentId) {
		this.studentId = studentId;
	}

	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public int getStudentGrade() {
		return studentGrade;
	}

	public void setStudentGrade(Integer studentGrade) {
		this.studentGrade = studentGrade;
	}
}
