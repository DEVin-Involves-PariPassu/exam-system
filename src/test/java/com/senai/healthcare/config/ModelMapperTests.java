package com.senai.healthcare.config;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.senai.healthcare.dto.ExamDTO;
import com.senai.healthcare.model.Exam;
import com.senai.healthcare.model.Gender;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ModelMapperTests {

	@Autowired
	private ModelMapper modelMapper;
	
	@Test
	public void validMapExamDtoToExam() throws Exception{
		
		ExamDTO examDTO = createExamDTO();
		
		Exam exam = modelMapper.map(examDTO, Exam.class);
		
		assertEquals(examDTO.getId(), exam.getId());
		assertEquals(examDTO.getInstitutionId(), exam.getInstitution().getId());
		assertEquals(examDTO.getPatientAge(), exam.getPatientAge());
		assertEquals(examDTO.getPatientGender(), exam.getPatientGender());
		assertEquals(examDTO.getPatientName(), exam.getPatientName());
		assertEquals(examDTO.getPhysicianCRM(), exam.getPhysicianCRM());
		assertEquals(examDTO.getPhysicianName(), exam.getPhysicianName());
		assertEquals(examDTO.getProcedureName(), exam.getProcedureName());
	}

	private ExamDTO createExamDTO() {
		ExamDTO dto = new ExamDTO();
		dto.setId(3L);
		dto.setInstitutionId(5L);
		dto.setPatientAge(22);
		dto.setPatientGender(Gender.MALE);
		dto.setPatientName("Pedro Alves");
		dto.setPhysicianCRM("1543 DF");
		dto.setPhysicianName("Charles Pereira");
		dto.setProcedureName("Hemograma");
		return dto;
	}
	
}
