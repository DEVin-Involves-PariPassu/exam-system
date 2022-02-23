package com.senai.healthcare.service;

import static org.junit.Assert.assertEquals;

import com.senai.healthcare.service.exception.InsufficientCoinsException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.senai.healthcare.dto.ExamDTO;
import com.senai.healthcare.model.Gender;
import com.senai.healthcare.model.HealthcareInstitution;
import com.senai.healthcare.repository.HealthcareInstitutionRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ExamServiceTests {

	@Autowired
	private HealthcareInstitutionRepository institutionRepository;
	
	@Autowired
	private ExamService examService;
	
	private HealthcareInstitution inst1 = null;
	
	private HealthcareInstitution inst2 = null;
	
	@Before
	public void initialize() {
		
		inst1 = new HealthcareInstitution();
		inst1.setCnpj("07968306000185");
		inst1.setName("Hospital do Coração");
		
		inst2 = new HealthcareInstitution();
		inst2.setCnpj("95184594000144");
		inst2.setName("Clinica das Faturas");
		
		institutionRepository.save(inst1);
		institutionRepository.save(inst2);
	}
	
	@Test(expected = InsufficientCoinsException.class)
	public void testInsufficientCoinsCreateExam() throws Exception{
		
		for (int i = 0; i <= 20; i++) {
			ExamDTO examDTO = createExamDTO(i);
			examService.save(examDTO);
		}
		
	}
	
	@Test(expected = InsufficientCoinsException.class)
	public void testInsufficientCoinsGetExam() throws Exception{

		
		for (int i = 0; i <= 20; i++) {
			ExamDTO examDTO = createExamDTO(i);
			examService.save(examDTO);
			examService.getById(examDTO.getId(), examDTO.getInstitutionId());
		}
		
	}
	
	@Test
	public void validateDiscountCoinCreateExam() {
		
		ExamDTO examDTO = createExamDTO(1);
		examService.save(examDTO);
	
		HealthcareInstitution institution = institutionRepository.findById(examDTO.getInstitutionId()).get();
		
		assertEquals(19, institution.getCoins());
		
	}
	
//	@Test
//	public void validateDiscountCoinGetExam() {
//
//		ExamDTO examDTO = createExamDTO(1);
//		examService.save(examDTO);
//
//		examService.getById(examDTO.getId(), examDTO.getInstitutionId());
//
//		HealthcareInstitution institution = institutionRepository.findById(examDTO.getInstitutionId()).get();
//
//		assertEquals(18, institution.getCoins());
//
//	}
	
//	@Test
//	public void dontDiscountGetSameExam() {
//
//		ExamDTO examDTO = createExamDTO(1);
//		examService.save(examDTO);
//
//		examService.getById(examDTO.getId(), examDTO.getInstitutionId());
//		examService.getById(examDTO.getId(), examDTO.getInstitutionId());
//
//		HealthcareInstitution institution = institutionRepository.findById(examDTO.getInstitutionId()).get();
//
//		assertEquals(18, institution.getCoins());
//
//	}
	
	private ExamDTO createExamDTO(int i) {
		ExamDTO exam = new ExamDTO();
		exam.setInstitutionId(inst1.getId());
		exam.setPatientAge(20);
		exam.setPatientGender(Gender.MALE);
		exam.setPatientName("Geraldo Couto");
		exam.setPhysicianCRM("1234 DF");
		exam.setPhysicianName("Felipe Moreira");
		exam.setProcedureName("Hemograma - " + i);
		
		return exam;
	}
	
}
