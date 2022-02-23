package com.senai.healthcare.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.senai.healthcare.model.Exam;
import com.senai.healthcare.model.HealthcareInstitution;

@Repository
public interface ExamRepository extends JpaRepository<Exam, Long>{

	public Optional<Exam> findByPatientNameAndProcedureNameAndInstitution(
			String patientName, 
			String procedureName,
			HealthcareInstitution institution);
	
}
