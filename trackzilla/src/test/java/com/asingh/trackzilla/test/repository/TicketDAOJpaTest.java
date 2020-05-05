package com.asingh.trackzilla.test.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;

import com.asingh.trackzilla.enums.TicketStatus;
import com.asingh.trackzilla.model.Application;
import com.asingh.trackzilla.model.Ticket;
import com.asingh.trackzilla.repository.TicketDAO;

@DataJpaTest
@Import(TicketDAO.class)
public class TicketDAOJpaTest {

	@Autowired
	TestEntityManager entityManger;

	@Autowired
	private TicketDAO repository;

	private Ticket ticket;

	@BeforeEach
	public void init() {
		Application application = new Application("Trackzilla", "Akanksha");
		entityManger.persist(application);
		this.ticket = new Ticket("Implement Entity for Application",
				"Create an Entity class for Ticket having id, title, description, application, status as fields");
		this.ticket.setApplication(application);
	}

	@Test
	public void persistTicket() {
		Ticket savedTicket = entityManger.persist(this.ticket);
		assertNotNull(savedTicket.getId());
		assertEquals("Implement Entity for Application", savedTicket.getTitle());
		assertNotNull(savedTicket.getApplication());
		assertEquals("Trackzilla", savedTicket.getApplication().getName());
//		assertEquals("DRAFT", savedTicket.getStatus() );
	}

	@Test
	public void addTicket() {
		assertDoesNotThrow(() -> {
			repository.addTicket(this.ticket);
		});
	}

	@Test
	public void updateTicket() {
		Ticket savedTicket = entityManger.persist(this.ticket);
		long savedTicketId = savedTicket.getId();
		Application newApp = new Application("Bugzilla", "Utkarsh");
		entityManger.persist(newApp);
		savedTicket.setStatus(TicketStatus.IN_PROGRESS);
		savedTicket.setApplication(newApp);
		repository.updateTicket(savedTicket);
		Ticket updatedTicket = repository.getTicketById(savedTicketId);
		assertNotNull(updatedTicket);
		assertEquals(TicketStatus.IN_PROGRESS, updatedTicket.getStatus());
		assertNotNull(updatedTicket.getApplication());
		assertEquals("Bugzilla", updatedTicket.getApplication().getName());
	}

	@Test
	public void getTicketByIdNegTest() {

		Ticket findTicket = repository.getTicketById(1001L);
		assertThat(findTicket).isNull();
	}

	@Test
	public void getAllTickets() {
		Ticket ticket1 = new Ticket("Test Ticket 1", "Description 1");
		Ticket ticket2 = new Ticket("Test Ticket 2", "Description 2");
		Ticket ticket3 = new Ticket("Test Ticket 3", "Description 3");
		Stream.of(ticket1, ticket2, ticket3).forEach((t) -> {
			repository.addTicket(t);
		});
		List<Ticket> tickets = repository.getAllTickets();
		assertNotNull(tickets);
		assertThat(tickets.size()).isGreaterThanOrEqualTo(3);
	}

	@Test
	public void deleteTicket() {
		Ticket savedTicket = entityManger.persist(this.ticket);
		long savedTicketId = savedTicket.getId();
		assertThat(savedTicket).isNotNull();
		repository.deleteTicket(savedTicketId);
		Ticket findTicket = repository.getTicketById(savedTicketId);
		assertThat(findTicket).isNull();
	}

}
