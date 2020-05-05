package com.asingh.trackzilla.test.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;

import com.asingh.trackzilla.model.Application;
import com.asingh.trackzilla.model.Release;
import com.asingh.trackzilla.repository.ReleaseDAO;

@DataJpaTest
@Import(ReleaseDAO.class)
public class ReleaseDaoJpaTest {

	@Autowired
	TestEntityManager entityManager;

	@Autowired
	ReleaseDAO dao;

	Release release = null;

	final static String releaseJsonWithoutApp = "";

	@BeforeEach
	public void init() {
		this.release = new Release("RELEASE_1.0", LocalDate.of(2020, 05, 30));
	}

	@Test
	public void persistReleaseWithNewApplication() {
		Application application = new Application("Test Application 7", "Unknown Owner 7");
		application.setDescription("Description of Test Application 7");
		this.release.addApplication(application);
		Release saved_Release = entityManager.persist(this.release);

		assertNotNull(saved_Release);
		assertEquals("RELEASE_1.0", saved_Release.getDescription());
		assertEquals(LocalDate.of(2020, 05, 30).toString(), saved_Release.getReleaseDate().toString());
		assertNotNull(saved_Release.getApplications().get(0));
		assertEquals(application.getName(), saved_Release.getApplications().get(0).getName());
	}

	@Test
	public void persistReleaseWithoutApplicationAndFind() {
		Release saved_Release = entityManager.persist(this.release);

		assertNotNull(saved_Release);
		assertEquals(this.release.getDescription(), saved_Release.getDescription());
		assertEquals(LocalDate.of(2020, 05, 30).toString(), saved_Release.getReleaseDate().toString());
		assertEquals(0, saved_Release.getApplications().size());

		Release searched_Release = dao.getReleaseById(saved_Release.getId());
		assertNotNull(searched_Release);
		assertEquals(saved_Release.getId(), searched_Release.getId());
		assertEquals(this.release.getDescription(), searched_Release.getDescription());
	}

	@Test
	public void addApplicationToTicket() {
		Release saved_Release = entityManager.persist(this.release);

		assertDoesNotThrow(() -> {
			dao.addApplication(3l, saved_Release.getId());
		});

		Release updatedRelease = dao.getReleaseById(saved_Release.getId());

		assertNotNull(updatedRelease.getApplications().get(0));
		assertEquals(3l, updatedRelease.getApplications().get(0).getId());
		assertEquals("Test Application 3", updatedRelease.getApplications().get(0).getName());
	}

	@Test
	public void getAllReleases() {
		Release release1 = new Release("RELEASE_1.0", LocalDate.of(2020, 05, 30));
		Release release2 = new Release("RELEASE_2.0", LocalDate.of(2020, 06, 30));
		Release release3 = new Release("RELEASE_3.0", LocalDate.of(2020, 07, 30));
		Stream.of(release1, release2, release3).forEach((r) -> {
			entityManager.persist(r);
		});		
		List<Release> releases = dao.getAllReleases();
		
		assertThat(releases.size()).isGreaterThanOrEqualTo(3);
	}
}
