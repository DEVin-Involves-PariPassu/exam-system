package com.senai.healthcare.controller;

import com.senai.healthcare.repository.ExamRepository;
import com.senai.healthcare.repository.HealthcareInstitutionRepository;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.senai.healthcare.dto.ExamDTO;
import com.senai.healthcare.model.Exam;
import com.senai.healthcare.model.Gender;
import com.senai.healthcare.model.HealthcareInstitution;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ExamControllerTests {

	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private HealthcareInstitutionRepository institutionRepository;
	
	@Autowired
	private ExamRepository examRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
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
	
	@After
	public void finalize() {
		examRepository.deleteAll();
		institutionRepository.deleteAll();
	}
	
	@Test
	public void validateNullFields() throws Exception{
		
		mockMvc.perform(MockMvcRequestBuilders.post("/api/exams")
				.content("{}")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isBadRequest())
				.andExpect(MockMvcResultMatchers.jsonPath("$.messages", 
						Matchers.containsInAnyOrder(
								"Healthcare Instituion ID is required",
								"Procedure Name is required",
								"Patient name is required",
								"Patient gender is required",
								"Physician name is required",
								"Physician CRM is required")))
				.andReturn();
		
	}
	
	@Test
	public void testExamNotExists() throws Exception {
		
		mockMvc.perform(MockMvcRequestBuilders.get("/api/exams/9999" + "?institutionId=" + inst1.getId())
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isNoContent())
				.andExpect(MockMvcResultMatchers.jsonPath("$.messages", Matchers.hasItem("The exam does not exist")))
				.andReturn();
	}
	
	@Test
	public void testInvalidInstitutionId() throws Exception {
		
		ExamDTO exam = createExamDTO();
		exam.setInstitutionId(999L);
		
		mockMvc.perform(MockMvcRequestBuilders.post("/api/exams")
				.content(convertToJsonString(exam))
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isBadRequest())
				.andExpect(MockMvcResultMatchers.jsonPath("$.messages", Matchers.hasItem("Institution ID is invalid")))
				.andReturn();
		
	}
	
	@Test
	public void testCreateExam() throws Exception {
		
		ExamDTO exam = createExamDTO();
		
		mockMvc.perform(MockMvcRequestBuilders.post("/api/exams")
				.content(convertToJsonString(exam))
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isCreated())
				.andExpect(MockMvcResultMatchers.jsonPath("$.id").isNotEmpty())
				.andReturn();
		
	}
	
	@Test
	public void getExam() throws Exception {
		
		Exam exam = createExam();
		
		examRepository.save(exam);
		
		mockMvc.perform(MockMvcRequestBuilders.get("/api/exams/" + exam.getId() + "?institutionId=" + inst1.getId())
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.patientName").value("Juliana Alves"))
				.andReturn();
	}
	
	@Test
	public void updateExam() throws Exception {
		
		Exam exam = createExam();
		
		examRepository.save(exam);
		
		ExamDTO examDTO = modelMapper.map(exam, ExamDTO.class);
		examDTO.setPatientName("Juliana Sales");
		
		mockMvc.perform(MockMvcRequestBuilders.put("/api/exams/" + exam.getId() + "?institutionId=" + inst1.getId())
				.content(convertToJsonString(examDTO))
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andReturn();
		
		mockMvc.perform(MockMvcRequestBuilders.get("/api/exams/" + exam.getId() + "?institutionId=" + inst1.getId())
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.patientName").value("Juliana Sales"))
				.andReturn();
		
	}
	
	@Test
	public void accessDeniedToUpdateExam() throws Exception {
		
		Exam exam = createExam();
		
		examRepository.save(exam);
		
		ExamDTO examDTO = modelMapper.map(exam, ExamDTO.class);
		examDTO.setPatientName("Juliana Sales");
		examDTO.setInstitutionId(inst2.getId());
		
		mockMvc.perform(MockMvcRequestBuilders.put("/api/exams/" + exam.getId())
				.content(convertToJsonString(examDTO))
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isUnauthorized())
				.andReturn();
	}
	
	@Test
	public void deleteExamById() throws Exception {
		
		Exam exam = createExam();
		
		examRepository.save(exam);
		
		mockMvc.perform(MockMvcRequestBuilders.delete("/api/exams/" + exam.getId() + "?institutionId=" + inst1.getId())
				.content(convertToJsonString(exam))
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andReturn();
		
		mockMvc.perform(MockMvcRequestBuilders.get("/api/exams/" + exam.getId() + "?institutionId=" + inst1.getId())
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isNoContent())
				.andExpect(MockMvcResultMatchers.jsonPath("$.messages", Matchers.hasItem("The exam does not exist")))
				.andReturn();
		
	}
	
	
	@Test
	public void accesDeniedToDeleteExam() throws Exception {
		
		Exam exam = createExam();
		
		examRepository.save(exam);
		
		mockMvc.perform(MockMvcRequestBuilders.delete("/api/exams/" + exam.getId() + "?institutionId=" + inst2.getId())
				.content(convertToJsonString(exam))
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isUnauthorized())
				.andReturn();
	}

	private Exam createExam() {
		Exam exam = new Exam();
		exam.setInstitution(inst1);
		exam.setPatientAge(18);
		exam.setPatientGender(Gender.FEMALE);
		exam.setPatientName("Juliana Alves");
		exam.setPhysicianCRM("124 GO");
		exam.setPhysicianName("William Silva");
		exam.setProcedureName("Raio-X");
		
		return exam;
	}
	
	private ExamDTO createExamDTO() {
		ExamDTO exam = new ExamDTO();
		exam.setInstitutionId(inst1.getId());
		exam.setPatientAge(20);
		exam.setPatientGender(Gender.MALE);
		exam.setPatientName("Geraldo Couto");
		exam.setPhysicianCRM("1234 DF");
		exam.setPhysicianName("Felipe Moreira");
		exam.setProcedureName("Hemograma");
		
		return exam;
	}
	
	private String convertToJsonString(Object object) {
		try {
			return new ObjectMapper().writeValueAsString(object);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
	}
	
}
