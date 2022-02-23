package com.senai.healthcare.service;

import com.senai.healthcare.service.exception.HealthcareInstitutionAlreadyExistException;
import org.springframework.stereotype.Service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;

import com.senai.healthcare.model.HealthcareInstitution;
import com.senai.healthcare.repository.HealthcareInstitutionRepository;

@Service
public class HealthcareInstitutionService {

	@Autowired
	private HealthcareInstitutionRepository institutionRepository;
	
	
	@Transactional
	public void save(HealthcareInstitution institution) {
		
		Optional<HealthcareInstitution> institutionExist = institutionRepository.findByCnpj(institution.getCnpj());
		
		if(institutionExist.isPresent()) {
			throw new HealthcareInstitutionAlreadyExistException("Instituion with CNPJ is already exist.");
		}
		
		institutionRepository.save(institution);
	}


	public Optional<HealthcareInstitution> getById(Long id) {
		return institutionRepository.findById(id);
	}
}
