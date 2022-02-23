package com.senai.healthcare.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.senai.healthcare.model.HealthcareInstitution;

@Repository
public interface HealthcareInstitutionRepository extends JpaRepository<HealthcareInstitution, Long>{

	public Optional<HealthcareInstitution> findByCnpj(String cnpj);
	
}
