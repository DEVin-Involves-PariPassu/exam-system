package com.senai.healthcare.controller.handler;

import java.time.LocalDateTime;

import com.senai.healthcare.service.exception.ExamAlreadyExistException;
import com.senai.healthcare.service.exception.HealthcareInstitutionAlreadyExistException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.senai.healthcare.dto.ErrorResponse;

@RestControllerAdvice
public class EntityAlreadyExistHandler {
	
	@ExceptionHandler({ HealthcareInstitutionAlreadyExistException.class, ExamAlreadyExistException.class })
	public ResponseEntity<ErrorResponse> healthcareInstitutionAlreadyExistException(Exception e) {
		
		ErrorResponse error = new ErrorResponse();
		error.setCode(HttpStatus.BAD_REQUEST.value());
		error.setTimestamp(LocalDateTime.now());
		error.getMessages().add(e.getMessage());
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
	}
}
