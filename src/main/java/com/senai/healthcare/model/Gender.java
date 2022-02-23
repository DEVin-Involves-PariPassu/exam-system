package com.senai.healthcare.model;

import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Gender {

	MALE("M", "Male"),
	FEMALE("F", "Female");
	
	@JsonValue
	private String code;
	
	private String description;
	
	Gender(String code, String description) {
		this.code = code;
		this.description = description;
	}
		
	public String getCode() {
		return code;
	}

	public String getDescription() {
		return description;
	}

	public static Gender getByCode(String code) {
		return Arrays.stream(values())
				.filter(gender -> gender.code.equals(code)).findFirst()
				.orElseThrow(() -> new IllegalArgumentException("Code for gender is invalid"));
	}
	
	@Override
	public String toString() {
		return this.description;
	}
}
