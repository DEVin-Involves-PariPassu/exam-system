package com.senai.healthcare.service.exception;

public class PermissionDeniedException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PermissionDeniedException(String message) {
		super(message);
	}
	
}
