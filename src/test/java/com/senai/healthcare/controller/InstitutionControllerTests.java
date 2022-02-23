package com.senai.healthcare.controller;

import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class InstitutionControllerTests {

	@Autowired
	private MockMvc mockMvc;
	
	
	@Test
	public void validateNullFields() throws Exception {
		
		mockMvc.perform(MockMvcRequestBuilders.post("/api/institutions")
				.content("")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isBadRequest())
				.andReturn();
		
		final StringBuilder json = new StringBuilder("{")
				.append("\"name\": \"Hospital Anchieta\"")
				.append("}");
				
		
		mockMvc.perform(MockMvcRequestBuilders.post("/api/institutions")
				.content(json.toString())
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isBadRequest())
				.andExpect(MockMvcResultMatchers.jsonPath("$.messages", Matchers.hasItem("CNPJ is required")))
				.andReturn();
		
		json.setLength(0);
		json.append("{")
			.append("\"cnpj\": \"34131907000101\"")
			.append("}");
		
		mockMvc.perform(MockMvcRequestBuilders.post("/api/institutions")
				.content(json.toString())
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isBadRequest())
				.andExpect(MockMvcResultMatchers.jsonPath("$.messages", Matchers.hasItem("Name is required")))
				.andReturn();
		
	}
	
	@Test
	public void cnpjIsInValid() throws Exception {
		
		final StringBuilder json = new StringBuilder("{")
				.append("\"name\": \"Hospital Anchieta\",")
				.append("\"cnpj\": \"34131907000104\"")
				.append("}");
		
		mockMvc.perform(MockMvcRequestBuilders.post("/api/institutions")
				.content(json.toString())
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isBadRequest())
				.andExpect(MockMvcResultMatchers.jsonPath("$.messages", Matchers.hasItem("CNPJ is invalid")))
				.andReturn();
	}
	
	@Test
	public void createInstitution() throws Exception {
		
		final StringBuilder json = new StringBuilder("{")
				.append("\"name\": \"Hospital Anchieta\",")
				.append("\"cnpj\": \"34131907000101\"")
				.append("}");
		
		mockMvc.perform(MockMvcRequestBuilders.post("/api/institutions")
				.content(json.toString())
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isCreated())
				.andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Hospital Anchieta"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.cnpj").value("34131907000101"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.id").isNotEmpty())
				.andReturn();
	}
	
	@Test
	public void rejectTwoInstitutionSameCnpj() throws Exception{
		
		final StringBuilder json = new StringBuilder("{")
				.append("\"name\": \"Hospital Anchieta\",")
				.append("\"cnpj\": \"46505180000164\"")
				.append("}");
		
		mockMvc.perform(MockMvcRequestBuilders.post("/api/institutions")
				.content(json.toString())
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON))
				.andReturn();
		
		mockMvc.perform(MockMvcRequestBuilders.post("/api/institutions")
				.content(json.toString())
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isBadRequest())
				.andExpect(MockMvcResultMatchers.jsonPath("$.messages", Matchers.hasItem("Instituion with CNPJ is already exist.")))
				.andReturn();
		
	}
	
}
