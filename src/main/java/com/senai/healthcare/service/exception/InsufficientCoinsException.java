package com.senai.healthcare.service.exception;

public class InsufficientCoinsException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InsufficientCoinsException(String mensagem) {
		super(mensagem);
	}
	
}
