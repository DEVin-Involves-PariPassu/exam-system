package com.senai.healthcare.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.senai.healthcare.model.Gender;

public class ExamDTO {

	private Long id;
	
	@NotNull(message = "Healthcare Instituion ID is required")
	private Long institutionId;

	@NotBlank(message = "Procedure Name is required")
	private String procedureName;
	
	@NotBlank(message = "Patient name is required")
	private String patientName;

	private int patientAge = 0;

	@NotNull(message = "Patient gender is required")
	private Gender patientGender;
	
	@NotBlank(message = "Physician name is required")
	private String physicianName;
	
	@NotBlank(message = "Physician CRM is required")
	private String physicianCRM;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getInstitutionId() {
		return institutionId;
	}

	public void setInstitutionId(Long institutionId) {
		this.institutionId = institutionId;
	}

	public String getProcedureName() {
		return procedureName;
	}

	public void setProcedureName(String procedureName) {
		this.procedureName = procedureName;
	}

	public String getPatientName() {
		return patientName;
	}

	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}

	public int getPatientAge() {
		return patientAge;
	}

	public void setPatientAge(int patientAge) {
		this.patientAge = patientAge;
	}

	public Gender getPatientGender() {
		return patientGender;
	}

	public void setPatientGender(Gender patientGender) {
		this.patientGender = patientGender;
	}

	public String getPhysicianName() {
		return physicianName;
	}

	public void setPhysicianName(String physicianName) {
		this.physicianName = physicianName;
	}

	public String getPhysicianCRM() {
		return physicianCRM;
	}

	public void setPhysicianCRM(String physicianCRM) {
		this.physicianCRM = physicianCRM;
	}
	
	
}
