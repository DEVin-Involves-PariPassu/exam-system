package com.senai.healthcare.controller;

import java.net.URI;
import java.util.Optional;

import javax.validation.Valid;
import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.senai.healthcare.model.HealthcareInstitution;
import com.senai.healthcare.service.HealthcareInstitutionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "Institution")
@RestController
@RequestMapping("/api/institutions")
public class InstitutionController {

	@Autowired
	private HealthcareInstitutionService institutionService;
	
	@ApiOperation(value = "Create new Healthcare Institution")
	@PostMapping
	public ResponseEntity<HealthcareInstitution> createInstituion(@Valid @RequestBody HealthcareInstitution institution) {
		
		institutionService.save(institution);
		
		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
			.path("/{id}")
			.buildAndExpand(institution.getId()).toUri();
		
		return ResponseEntity.created(location).body(institution);
		
	}
	
	
	@ApiOperation(value = "Retrieve an Institution by id")
	@GetMapping("/{id}")
	public ResponseEntity<HealthcareInstitution> getById(@PathParam("id") Long id) {
		
		Optional<HealthcareInstitution> institution = this.institutionService.getById(id);
		
		return institution.isPresent()
				? ResponseEntity.ok(institution.get())
				: ResponseEntity.noContent().build();
		
	}
	
}
