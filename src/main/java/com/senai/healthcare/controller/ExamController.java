package com.senai.healthcare.controller;

import java.net.URI;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.senai.healthcare.dto.ExamDTO;
import com.senai.healthcare.service.ExamService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "Exam")
@RestController
@RequestMapping("/api/exams")
public class ExamController {

	@Autowired
	private ExamService examService;
	
	@ApiOperation(value = "Create new exam")
	@PostMapping
	public ResponseEntity<ExamDTO> createExam(@Valid @RequestBody ExamDTO exam) {
		
		examService.save(exam);
		
		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
			.path("/{id}")
			.buildAndExpand(exam.getId()).toUri();
		
		return ResponseEntity.created(location).body(exam);
		
	}
	
	@ApiOperation("Update an exam")
	@PutMapping("/{id}")
	public ResponseEntity<ExamDTO> updateExam(@NotNull @PathVariable("id") Long id,
			@Valid @RequestBody ExamDTO exam) {
		
		exam.setId(id);
		this.examService.updateExam(exam);

		return ResponseEntity.ok(exam);
	}

	@ApiOperation("Delete an exam")
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteExamById(@NotNull @PathVariable("id") Long id,
			@NotNull @RequestParam("institutionId")Long idInstitution) {
		
		this.examService.deleteById(id, idInstitution);

		return ResponseEntity.ok().build();
	}
	
	@ApiOperation(value = "Retrieve an exam by id")
	@GetMapping("/{id}")
	public ResponseEntity<ExamDTO> getById(@NotNull @PathVariable("id") Long id,
			@NotNull @RequestParam("institutionId")Long idInstitution) {
		
		ExamDTO examDTO = this.examService.getById(id, idInstitution);
		
		return ResponseEntity.ok(examDTO);
		
	}
	
}
