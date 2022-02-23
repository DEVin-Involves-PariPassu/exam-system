package com.senai.healthcare.service;

import java.util.Optional;

import javax.transaction.Transactional;

import com.senai.healthcare.service.exception.ExamNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.senai.healthcare.dto.ExamDTO;
import com.senai.healthcare.model.Exam;
import com.senai.healthcare.model.HealthcareInstitution;
import com.senai.healthcare.repository.ExamRepository;
import com.senai.healthcare.repository.HealthcareInstitutionRepository;
import com.senai.healthcare.service.exception.ExamAlreadyExistException;
import com.senai.healthcare.service.exception.InsufficientCoinsException;
import com.senai.healthcare.service.exception.PermissionDeniedException;

@Service
public class ExamService {

	private final int OPERATION_COST = 1;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private ExamRepository examRepository;
	
	@Autowired
	private HealthcareInstitutionRepository institutionRepository;

	@Transactional
	public ExamDTO getById(Long id, Long idInstitution) {
		
		Exam exam = examRepository.findById(id)
				.orElseThrow(() -> 
				new ExamNotFoundException("The exam does not exist"));
		
		if(!exam.getInstitution().getId().equals(idInstitution)) {
			throw new PermissionDeniedException("You don't have permission to get this exam");
		}
		
		if(!exam.isRead() && !exam.getInstitution().haveCoins(OPERATION_COST)) {
			throw new InsufficientCoinsException("Insufficient coins for this operation.");
		}
		
		if(exam.isRead()) {
			exam.getInstitution().chargeCoins(OPERATION_COST);
			exam.setRead(true);			
			examRepository.save(exam);
			institutionRepository.save(exam.getInstitution());
		}
		
		
		return modelMapper.map(exam, ExamDTO.class);
		
	}

	@Transactional
	public void save(ExamDTO examDTO) {
		
		Exam exam = validateAndConvertExamDtoToExam(examDTO);
		
		if(!exam.getInstitution().haveCoins(OPERATION_COST)) {
			throw new InsufficientCoinsException("Insufficient coins for this operation.");
		}
		
		examRepository.save(exam);
		examDTO.setId(exam.getId());
		
		exam.getInstitution().chargeCoins(OPERATION_COST);
		institutionRepository.save(exam.getInstitution());
		
	}
	
	@Transactional
	public void updateExam(ExamDTO examDTO) {
		
		Optional<Exam> examExist = examRepository.findById(examDTO.getId());
		
		if(!examExist.isPresent()) {
			throw new ExamNotFoundException("The exam does not exist");
		}
		
		if(!examExist.get().getInstitution().getId().equals(examDTO.getInstitutionId())) {
			throw new PermissionDeniedException("You don't have permission to this operation");
		}
		
		Exam exam = validateAndConvertExamDtoToExam(examDTO);
		
		if(!examDTO.getInstitutionId().equals(exam.getInstitution().getId())) {
			throw new PermissionDeniedException("You don't have permission to this operation");
		}
		
		examRepository.save(exam);
	}

	@Transactional
	public void deleteById(Long id, Long idInstitution) {
		
		Optional<Exam> exam = examRepository.findById(id);
		
		if(!exam.isPresent()) {
			throw new ExamNotFoundException("The exam does not exist");
		}
		
		if(!exam.get().getInstitution().getId().equals(idInstitution)) {
			throw new PermissionDeniedException("You don't have permission to this operation");
		}
		
		examRepository.delete(exam.get());
	}
	
	private Exam validateAndConvertExamDtoToExam(ExamDTO examDTO) {
		
		Optional<HealthcareInstitution> institution = institutionRepository.findById(examDTO.getInstitutionId());
		
		if(!institution.isPresent()) {
			throw new IllegalArgumentException("Institution ID is invalid");
		}
		
		isUniqueExam(examDTO, institution.get());
		
		Exam exam = modelMapper.map(examDTO, Exam.class);
		
		exam.setInstitution(institution.get());
		
		return exam;
	}

	
	private void isUniqueExam(ExamDTO examDTO, HealthcareInstitution institution) {
		Optional<Exam> examExist = examRepository.findByPatientNameAndProcedureNameAndInstitution(examDTO.getPatientName(), examDTO.getProcedureName(), institution);
		
		if(examExist.isPresent() && !examExist.get().getId().equals(examDTO.getId())) {
			throw new ExamAlreadyExistException("This exam alredy exist");
		}
	}

	
}
