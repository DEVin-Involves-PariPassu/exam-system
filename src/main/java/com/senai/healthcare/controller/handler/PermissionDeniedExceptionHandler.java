package com.senai.healthcare.controller.handler;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.senai.healthcare.dto.ErrorResponse;
import com.senai.healthcare.service.exception.PermissionDeniedException;

@RestControllerAdvice
public class PermissionDeniedExceptionHandler {

	@ExceptionHandler({ PermissionDeniedException.class })
	public ResponseEntity<ErrorResponse> permission(PermissionDeniedException e) {
		
		ErrorResponse error = new ErrorResponse();
		error.setCode(HttpStatus.UNAUTHORIZED.value());
		error.setTimestamp(LocalDateTime.now());
		error.getMessages().add(e.getMessage());
		
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
	}
}

