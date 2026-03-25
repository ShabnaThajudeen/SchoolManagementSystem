package com.eduexcellence.studentms.api;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.eduexcellence.studentms.domain.Student;
import com.eduexcellence.studentms.repo.StudentRepo;

@RestController
public class StudentResource {
	private static final Logger LOGGER = LoggerFactory.getLogger(StudentResource.class);
	
	@Autowired
	private StudentRepo studRepo;
	
	@GetMapping("/students")
	public List<Student> getAllStudents(){
		LOGGER.info("Getting all student details.");
		
		return studRepo.findAll();
	}
	
	@GetMapping("/students/{id}")
	public ResponseEntity<Student> getStudent(@PathVariable("id") Integer id){
		LOGGER.info("Getting student details with id {}", id);
		
		Optional<Student> studFound = studRepo.findById(id);
		if(studFound.isPresent()) {
			LOGGER.info("Student details with id {}:", id);
			LOGGER.info(studFound.get().toString());
			return ResponseEntity.ok(studFound.get());
		}
		
		LOGGER.error("Student with id {} not found", id);
		return ResponseEntity.notFound().build();
	}
	
	@PostMapping("/students/add")
	public ResponseEntity<Student> addStudent(@RequestBody Student student){
		LOGGER.info("Adding student details.");
		
		student.setStudentId(null);
		LOGGER.info("Setting student id as null.");
		
		Student newStudent = studRepo.save(student);
				
		LOGGER.info("Saved student details");
		return ResponseEntity.created(URI.create("http://localhost:8081/students/" + newStudent.getStudentId().toString())).body(newStudent);
	}
	
	@DeleteMapping("/students/{id}")
	public ResponseEntity<Void> deleteStudent(@PathVariable("id") Integer id){
		LOGGER.info("Deleting student details.");
		
		studRepo.deleteById(id);				
		LOGGER.info("Deleted student details.");
		
		return ResponseEntity.noContent().build();
	}
	
	@PutMapping("/students/{id}")
	public ResponseEntity<Student> updateStudent(@PathVariable("id") Integer id, @RequestBody Student student){
		LOGGER.info("Checking if student exist.");
		
		Optional<Student> studentFound = studRepo.findById(id);
		if(studentFound.isEmpty()) {
			LOGGER.error("Student with id {} not found", id);
			return ResponseEntity.notFound().build();
		}
		
		Student toUpdate = studentFound.get();
		toUpdate.setStudentName(student.getStudentName());
		toUpdate.setStudentGrade(student.getStudentGrade());
		
		Student updatedStud = studRepo.save(toUpdate);
		LOGGER.info("Updated student details.");
		
		return ResponseEntity.ok(updatedStud);
	}	
}
