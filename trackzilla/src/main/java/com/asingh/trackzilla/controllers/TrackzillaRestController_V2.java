package com.asingh.trackzilla.controllers;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.asingh.trackzilla.model.Application;
import com.asingh.trackzilla.model.Bug;
import com.asingh.trackzilla.model.Enhancement;
import com.asingh.trackzilla.model.Release;
import com.asingh.trackzilla.model.Ticket;
import com.asingh.trackzilla.service.ApplicationService;
import com.asingh.trackzilla.service.BugService;
import com.asingh.trackzilla.service.EnhancementService;
import com.asingh.trackzilla.service.ReleaseService;
import com.asingh.trackzilla.service.TicketService;

import io.micrometer.core.annotation.Timed;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/tza/v2/")
public class TrackzillaRestController_V2 {

	private ApplicationService applicationService;

	private TicketService ticketService;

	private BugService bugService;

	private EnhancementService enhancementService;

	private ReleaseService releaseService;

//	private final Counter ticketCounter;	

	@Autowired
	public TrackzillaRestController_V2(ApplicationService applicationService, TicketService ticketService,
			BugService bugService, EnhancementService enhancementService, ReleaseService releaseService
//			,MeterRegistry registry
	) {
		super();
		this.applicationService = applicationService;
		this.ticketService = ticketService;
		this.bugService = bugService;
		this.enhancementService = enhancementService;
		this.releaseService = releaseService;
//		this.ticketCounter = Counter.builder("api.createTicket").register(registry);		
	}

	@GetMapping(value = "/application/{id}")
	public ResponseEntity<Application> getApplicationById(@PathVariable Long id) {
		log.info("Processing GET request for application ID:" + id);
		Optional<Application> opApp = applicationService.findApplicationById(id);

		return opApp.isPresent() ? new ResponseEntity<Application>(opApp.get(), HttpStatus.OK)
				: new ResponseEntity<Application>(HttpStatus.NOT_FOUND);
	}

	@Timed("api.allAplications")
	@GetMapping(value = "/applications")
	public ResponseEntity<List<Application>> getApplications() {
		log.info("Processing GET request for all applications");
		Optional<List<Application>> opApps = applicationService.getAllApplications();

		return opApps.isPresent() ? new ResponseEntity<List<Application>>(opApps.get(), HttpStatus.OK)
				: new ResponseEntity<List<Application>>(HttpStatus.NOT_FOUND);
	}

	@GetMapping(value = "/application/{name}/{owner}")
	public ResponseEntity<Application> getApplicationByNameAndOwner(@PathVariable String name,
			@PathVariable String owner) {
		log.info("Processing GET request find by name :" + name + " owner:" + owner);
		Optional<Application> opApp = applicationService.findByNameAndOwner(name, owner);

		return opApp.isPresent() ? new ResponseEntity<Application>(opApp.get(), HttpStatus.OK)
				: new ResponseEntity<Application>(HttpStatus.NOT_FOUND);
	}

	@PostMapping(value = "/application")
	public ResponseEntity<Void> createApplication(@RequestBody Application application, UriComponentsBuilder builder) {
		log.info("Processing POST request for application ID:" + application.toString());
		if (applicationService.createApplication(application)) {
			HttpHeaders headers = new HttpHeaders();
			headers.setLocation(builder.path("/application/{id}").buildAndExpand(application.getId()).toUri());
			return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
		} else {
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		}
	}

	@PutMapping(value = "/application")
	public ResponseEntity<Void> updateApplication(@RequestBody Application application) {
		log.info("Processing PUT request for application ID:" + application.getId());
		if (applicationService.updateApplication(application)) {
			return ResponseEntity.accepted().build();
		} else {
			return ResponseEntity.badRequest().build();
		}
	}

	@DeleteMapping(value = "/application/{id}")
	public ResponseEntity<Void> deleteApplication(@PathVariable Long id) {
		log.info("Processiing DELETE request for application ID:" + id);
		if (applicationService.deleteApplication(id)) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("/ticket/{id}")
	public ResponseEntity<Ticket> getTicketById(@PathVariable long id) {
		Ticket ticket = ticketService.getTicketById(id);
		return Objects.isNull(ticket) ? new ResponseEntity<>(HttpStatus.NOT_FOUND)
				: new ResponseEntity<Ticket>(ticket, HttpStatus.OK);
	}

	@GetMapping("/tickets")
	public ResponseEntity<List<Ticket>> getAllTickets() {
		List<Ticket> tickets = ticketService.getAllTickets();
		return Objects.isNull(tickets) ? new ResponseEntity<>(HttpStatus.NOT_FOUND)
				: new ResponseEntity<List<Ticket>>(tickets, HttpStatus.OK);
	}

	@PostMapping("/ticket")
	public ResponseEntity<Void> addTicket(@RequestBody Ticket ticket, UriComponentsBuilder builder) {
//		ticketCounter.increment();
		ticketService.addTicket(ticket);
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(builder.path("/ticket/{id}").buildAndExpand(ticket.getId()).toUri());
		return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
	}

	@PutMapping("/ticket")
	public ResponseEntity<Void> updateTicket(@RequestBody Ticket ticket) {
		ticketService.updateTicket(ticket);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	@PutMapping("/ticket/{id}")
	public ResponseEntity<Void> closeTicket(@PathVariable long id) {
		ticketService.closeTicket(id);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	@DeleteMapping("/ticket/{id}")
	public ResponseEntity<Void> deleteTicket(@RequestBody Ticket ticket) {
		ticketService.deleteTicket(ticket.getId());
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}

	@PostMapping("/release")
	public ResponseEntity<Void> addNewRelease(@RequestBody Release release, UriComponentsBuilder builder) {
		releaseService.addRelease(release);
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(builder.path("/release/{id}").buildAndExpand(release.getId()).toUri());
		return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
	}

	@GetMapping("/releases")
	public ResponseEntity<List<Release>> getAllReleases() {
		Optional<List<Release>> opRelease = releaseService.getAllReleases();
		return opRelease.isPresent() ? new ResponseEntity<List<Release>>(opRelease.get(), HttpStatus.OK)
				: new ResponseEntity<List<Release>>(HttpStatus.NOT_FOUND);
	}

	@GetMapping("/release/{id}")
	public ResponseEntity<Release> getReleaseById(@PathVariable long id) {
		Optional<Release> opRelease = releaseService.getReleaseById(id);
		return opRelease.isPresent() ? new ResponseEntity<Release>(opRelease.get(), HttpStatus.OK)
				: new ResponseEntity<Release>(HttpStatus.NOT_FOUND);
	}

	@PutMapping("/release/{appId}/{releaseId}")
	public ResponseEntity<Void> addApplicationToRelease(@PathVariable Long appId, @PathVariable long releaseId) {
		releaseService.addApplication(appId, releaseId);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	@PostMapping("/bug")
	public ResponseEntity<Void> addNewBug(@RequestBody Bug bug) {
		bugService.addBug(bug);
		return new ResponseEntity<Void>(HttpStatus.CREATED);
	}

	@GetMapping("/bugs")
	public ResponseEntity<List<Bug>> getAllBugs() {
		List<Bug> bugs = bugService.getAllBugs();
		return bugs.size() > 0 ? new ResponseEntity<List<Bug>>(bugs, HttpStatus.OK)
				: new ResponseEntity<List<Bug>>(HttpStatus.NOT_FOUND);
	}

	@PostMapping("/enhancement")
	public ResponseEntity<Void> addNewEnhancement(@RequestBody Enhancement enhancement) {
		enhancementService.addEnhancement(enhancement);
		return new ResponseEntity<Void>(HttpStatus.CREATED);
	}

	@GetMapping("/enhancements")
	public ResponseEntity<List<Enhancement>> getAllEnhancements() {
		List<Enhancement> enhancements = enhancementService.getAllEnhancements();
		return enhancements.size() > 0 ? new ResponseEntity<List<Enhancement>>(enhancements, HttpStatus.OK)
				: new ResponseEntity<List<Enhancement>>(HttpStatus.NOT_FOUND);
	}

	@GetMapping("/ticket/status/{status}")
	public ResponseEntity<List<Ticket>> getTicketByStatus(@PathVariable String status) throws IllegalArgumentException {
		List<Ticket> closedTickets = ticketService.getTicketByStatus(status);
		return closedTickets.size() > 0 ? new ResponseEntity<List<Ticket>>(closedTickets, HttpStatus.OK)
				: new ResponseEntity<List<Ticket>>(HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<String> handleIllegalArgumentException(Exception e) {		
		return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
	}

}
