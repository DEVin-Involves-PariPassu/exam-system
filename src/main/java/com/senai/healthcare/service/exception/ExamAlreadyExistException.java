package com.senai.healthcare.service.exception;

public class ExamAlreadyExistException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ExamAlreadyExistException(String message) {
		super(message);
	}
	
}
