package com.senai.healthcare.controller.handler;

import java.time.LocalDateTime;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.senai.healthcare.dto.ErrorResponse;

@ControllerAdvice
public class MethodArgumentNotValidExceptionHandler extends ResponseEntityExceptionHandler{
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		ErrorResponse errorResponse = new ErrorResponse();
		errorResponse.setCode(HttpStatus.BAD_REQUEST.value());
		errorResponse.setTimestamp(LocalDateTime.now());
		ex.getBindingResult().getFieldErrors().forEach(error ->
			errorResponse.getMessages().add(error.getDefaultMessage()));
		ex.getBindingResult().getGlobalErrors().forEach(error -> 
			errorResponse.getMessages().add(error.getDefaultMessage()));
		
		return handleExceptionInternal(ex, errorResponse, headers, HttpStatus.BAD_REQUEST, request);
		
	}
	
}
