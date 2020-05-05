package com.asingh.trackzilla.test.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.asingh.trackzilla.model.Application;
import com.asingh.trackzilla.repository.ApplicationJpaRepository;
import com.asingh.trackzilla.service.ApplicationService;

@ExtendWith(SpringExtension.class)
public class ApplicationServiceUnitTest {

	@MockBean
	private ApplicationJpaRepository mockAppRepo;

	@Autowired
	private ApplicationService appService;

	private Application app;

	@BeforeEach
	public void init() {
		String name = "Test Application 5";
		String owner = "Unknown owner 5";
		this.app = new Application(name, owner);
		app.setDescription("Description of Test Application 5");
		app.setId(5L);
	}

	@Test
	public void getAllApplications() {
		when(mockAppRepo.getApplications()).thenReturn(Optional.of(Stream.of(this.app).collect(Collectors.toList())));

		Optional<List<Application>> opApp = appService.getAllApplications();
		assertEquals(1, opApp.get().size());
		assertEquals(5L, opApp.get().get(0).getId());
		verify(mockAppRepo).getApplications();
	}

	@Test
	public void findApplicationById() {
		when(mockAppRepo.findById(5L)).thenReturn(Optional.of(this.app));

		Optional<Application> opApp = appService.findApplicationById(5L);
		assertEquals(5L, opApp.get().getId());
		verify(mockAppRepo).findById(5L);
	}

	@Test
	public void findApplicationByNameAndOwner() {
		String name = "Test Application 5";
		String owner = "Unknown owner 5";
		when(mockAppRepo.findByNameAndOwner(name, owner)).thenReturn(Optional.of(this.app));

		Optional<Application> opApp = appService.findByNameAndOwner(name, owner);
		assertEquals(name, opApp.get().getName());
		assertEquals(owner, opApp.get().getOwner());
		verify(mockAppRepo).findByNameAndOwner(name, owner);
	}

	@Test
	public void createApplication() {
		when(mockAppRepo.isApplicationExists(this.app.getName(), this.app.getOwner())).thenReturn(false);
		when(mockAppRepo.createApplication(this.app)).thenReturn(true);

		assertEquals(true, appService.createApplication(this.app));
		verify(mockAppRepo).isApplicationExists(this.app.getName(), this.app.getOwner());
		verify(mockAppRepo).createApplication(this.app);
	}

	@Test
	public void updateApplication() {
		this.app.setDescription("Updating app description through test class");
		when(mockAppRepo.findByNameAndOwner(this.app.getName(), this.app.getOwner())).thenReturn(Optional.of(this.app));
		when(mockAppRepo.updateApplication(this.app)).thenReturn(true);

		assertEquals(true, appService.updateApplication(this.app));
		verify(mockAppRepo).findByNameAndOwner(this.app.getName(), this.app.getOwner());
		verify(mockAppRepo).updateApplication(this.app);
	}

	@Test
	public void updateApplicationAppDoesNotExists() {
		this.app.setDescription("Updating app description through test class");
		when(mockAppRepo.findByNameAndOwner(this.app.getName(), this.app.getOwner())).thenReturn(Optional.empty());

		assertEquals(false, appService.updateApplication(this.app));
		verify(mockAppRepo).findByNameAndOwner(this.app.getName(), this.app.getOwner());
		verify(mockAppRepo, times(0)).updateApplication(this.app);
	}

	@Test
	public void deleteById() {
		when(mockAppRepo.deleteById(4L)).thenReturn(true);

		assertEquals(true, appService.deleteApplication(4L));
		verify(mockAppRepo, times(1)).deleteById(4L);
	}

	@Test
	public void deleteByIdNegTest() {
		when(mockAppRepo.deleteById(400L)).thenReturn(false);

		assertEquals(false, appService.deleteApplication(400L));
		verify(mockAppRepo, times(1)).deleteById(400L);
	}

	@Test
	public void createOrSaveApplicationAppDoesNotExists() {
		when(mockAppRepo.findByNameAndOwner(this.app.getName(), this.app.getOwner())).thenReturn(Optional.empty());
		when(mockAppRepo.createApplication(this.app)).thenReturn(true);

		assertEquals(true, appService.createOrSaveApplication(this.app));
		verify(mockAppRepo, times(1)).createApplication(this.app);
	}

	@Test
	public void createOrSaveApplicationAppExists() {
		when(mockAppRepo.findByNameAndOwner(this.app.getName(), this.app.getOwner())).thenReturn(Optional.of(this.app));
		when(mockAppRepo.updateApplication(this.app)).thenReturn(true);
		this.app.setDescription(
				"App Description updated through com.asingh.trackzilla.controller.unitTest.ApplicationServiceUnitTest.createOrSaveApplicationAppExists()");

		assertEquals(true, appService.updateApplication(this.app));
		verify(mockAppRepo, times(1)).updateApplication(this.app);
	}

	@Configuration
	@Import(ApplicationService.class) 
	static class Config {

	}
}
