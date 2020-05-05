package com.asingh.trackzilla.test.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;

import com.asingh.trackzilla.model.Application;
import com.asingh.trackzilla.repository.ApplicationJpaRepository;

@Import(ApplicationJpaRepository.class)
@DataJpaTest(showSql = true)
public class ApplicationDAOJpaTest {

	@Autowired
	TestEntityManager entityManager;

	@Autowired
	private ApplicationJpaRepository repository;

	private Application app;

	@Test
	public void createApplicationByTestEntityManager() {
		Application savedApp = entityManager.persist(app);
		assertNotNull(savedApp.getId());
	}

	@BeforeEach
	public void createApplicationInstance() {
		this.app = new Application("Test Application", "Unknown Owner");
		this.app.setDescription("Application for Unit test");
	}

	@Test
	public void isApplicationExists() {
		Application savedApp = entityManager.persist(app);
		boolean success = repository.isApplicationExists(savedApp.getName(), savedApp.getOwner());
		assertTrue(success);
	}

	@Test
	public void createOrSaveApplication() {
		boolean success = repository.createApplication(app);
		assertTrue(success);
	}

	@Test
	public void getApplications() {
		entityManager.persist(app);
		Application app2 = new Application("Repository Test Application 2", "Unknown Owner 2");
		app2.setDescription("Application 2 for Unit test");
		entityManager.persist(app2);
		Optional<List<Application>> opApps = repository.getApplications();
		assertThat(opApps.isPresent()).isTrue();
		assertThat(opApps.get().size()).isGreaterThanOrEqualTo(2);
	}

	@Test
	public void findById() {
		Application savedApp = entityManager.persist(app);
		Optional<Application> opApp = repository.findById(savedApp.getId());
		assertThat(opApp.isPresent()).isTrue();
		assertThat(opApp.get().getId()).isEqualTo(savedApp.getId());
		assertThat(opApp.get().getName()).isEqualTo(savedApp.getName());
		assertThat(opApp.get().getOwner()).isEqualTo(savedApp.getOwner());
	}

	@Test
	public void findByIdNegTest() {
		Optional<Application> opApp = repository.findById(123456);
		assertThat(opApp.isPresent()).isFalse();
	}

	@Test
	public void findByNameAndOwner() {
		Application savedApp = entityManager.persist(app);
		Optional<Application> opApp = repository.findByNameAndOwner(savedApp.getName(), savedApp.getOwner());
		assertThat(opApp.isPresent()).isTrue();
		assertThat(opApp.get().getId()).isEqualTo(savedApp.getId());
		assertThat(opApp.get().getName()).isEqualTo(savedApp.getName());
		assertThat(opApp.get().getOwner()).isEqualTo(savedApp.getOwner());
	}

	@Test
	public void deleteApplicationByNameAndOwner() {
		Application savedApp = entityManager.persist(app);
		assertThat(repository.deleteApplication(savedApp.getName(), savedApp.getOwner())).isTrue();
	}

	@Test
	public void deleteById() {
		Application savedApp = entityManager.persist(app);
		assertThat(repository.deleteById(savedApp.getId())).isTrue();
	}

	@Test
	public void updateApplication() {
		Application savedApp = entityManager.persist(app);
		savedApp.setDescription("App desc updated from updateApplication()");
		repository.updateApplication(savedApp);
		assertThat(repository.findById(savedApp.getId()).get().getDescription())
				.isEqualTo("App desc updated from updateApplication()");
	}
}
