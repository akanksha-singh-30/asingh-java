package com.asingh.trackzilla.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.asingh.trackzilla.model.Application;
import com.asingh.trackzilla.repository.ApplicationRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/tza")
public class ApplicationRestController {

	@Autowired(required = false)
	private ApplicationRepository applicationRepository;

	public ApplicationRepository getApplicationRepository() {
		return applicationRepository;
	}

	public void setApplicationRepository(ApplicationRepository applicationRepository) {
		this.applicationRepository = applicationRepository;
	}

	@GetMapping(value = "/application")
	public List<Application> getAllApplication() {
		return applicationRepository.findAll();
	}

	@GetMapping(value = "/application/{id}")
	public ResponseEntity<Application> getAllApplication(@PathVariable Long id) {
		log.info("Processiing GET request for application ID:" + id);
		Optional<Application> dbApp = applicationRepository.findById(id);
		if (dbApp.isPresent()) {
			return new ResponseEntity<Application>(dbApp.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<Application>(HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping(value = "/application")
	public ResponseEntity<Application> createOrSaveApplication(@RequestBody Application application,
			UriComponentsBuilder builder) {
		log.info("Processiing POST request for application ID:" + application.toString());
		Application savedApp = applicationRepository.save(application);
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(builder.path("/application/{id}").buildAndExpand(savedApp.getId()).toUri());
		return new ResponseEntity<Application>(savedApp, headers, HttpStatus.CREATED);
	}

	@PutMapping(value = "/application/{id}")
	public Application updateApplication(@PathVariable Long id, @RequestBody Application application) {
		log.info("Processiing PUT request for application ID:" + id);
		Application dbApp = applicationRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Application with id" + id + "not found"));
		dbApp.setName(application.getName());
		dbApp.setOwner(application.getOwner());
		dbApp.setDescription(application.getDescription());
		return applicationRepository.save(dbApp);
	}

	@DeleteMapping(value = "/application/{id}")
	public ResponseEntity<Application> deleteApplication(@PathVariable Long id) {
		log.info("Processiing DELETE request for application ID:" + id);
		applicationRepository.deleteById(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

}
