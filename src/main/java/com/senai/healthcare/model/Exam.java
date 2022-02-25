package com.senai.healthcare.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "exam")
public class Exam {

	@Id
	@GeneratedValue(strategy =  GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "id_instituion")
	@NotNull(message = "Healthcare Instituion is required")
	private HealthcareInstitution institution;

	@NotBlank(message = "Procedure Name is required.")
	private String procedureName;
	
	@NotBlank(message = "Patient name is required.")
	private String patientName;

	private int patientAge = 0;

	@NotNull(message = "Patient gender is required.")
	private Gender patientGender;
	
	@NotBlank(message = "Physician name is required.")
	private String physicianName;
	
	@NotBlank(message = "Physician CRM is required.")
	private String physicianCRM;
	
	private boolean read = false;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public HealthcareInstitution getInstitution() {
		return institution;
	}

	public void setInstitution(HealthcareInstitution institution) {
		this.institution = institution;
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

	public boolean isRead() {
		return read;
	}

	public void setRead(boolean read) {
		this.read = read;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((institution == null) ? 0 : institution.hashCode());
		result = prime * result + ((patientName == null) ? 0 : patientName.hashCode());
		result = prime * result + ((procedureName == null) ? 0 : procedureName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Exam other = (Exam) obj;
		if (institution == null) {
			if (other.institution != null)
				return false;
		} else if (!institution.equals(other.institution))
			return false;
		if (patientName == null) {
			if (other.patientName != null)
				return false;
		} else if (!patientName.equals(other.patientName))
			return false;
		if (procedureName == null) {
			if (other.procedureName != null)
				return false;
		} else if (!procedureName.equals(other.procedureName))
			return false;
		return true;
	}
	
	
}
