package com.senai.healthcare.model.converters;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import com.senai.healthcare.model.Gender;

@Converter(autoApply = true)
public class GenderConverter implements AttributeConverter<Gender, String>{

	@Override
	public String convertToDatabaseColumn(Gender gender) {
		if(gender == null) {
			return null;
		}
		return gender.getCode();
	}

	@Override
	public Gender convertToEntityAttribute(String code) {
		if(code == null) {
			return null;
		}
		return Gender.getByCode(code);
	}

}
