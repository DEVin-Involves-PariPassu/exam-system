package com.senai.healthcare.controller.handler;

import java.time.LocalDateTime;

import com.senai.healthcare.service.exception.ExamNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.senai.healthcare.dto.ErrorResponse;

@RestControllerAdvice
public class EntityNotFoundHandler {

	@ExceptionHandler({ ExamNotFoundException.class })
	public ResponseEntity<ErrorResponse> entityNotFound(ExamNotFoundException e) {
		
		ErrorResponse error = new ErrorResponse();
		error.setCode(HttpStatus.NO_CONTENT.value());
		error.setTimestamp(LocalDateTime.now());
		error.getMessages().add(e.getMessage());
		
		return ResponseEntity.status(HttpStatus.NO_CONTENT).body(error);
	}
}
