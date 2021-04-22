package com.asingh.trackzilla.test.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import com.asingh.trackzilla.enums.TicketStatus;
import com.asingh.trackzilla.model.Ticket;
import com.asingh.trackzilla.repository.TicketDAO;
import com.asingh.trackzilla.service.TicketService;

//@ExtendWith(SpringExtension.class)
@SpringJUnitConfig
public class TicketServiceUnitTest {

	@Mock
	private TicketDAO ticketRepo;

	@InjectMocks
	private TicketService tservice;

	@BeforeAll
	public static void init() {
		MockitoAnnotations.initMocks(TicketServiceUnitTest.class);
	}

	@Test
	public void getAllTickets() {
		Ticket ticket1 = new Ticket("Test Ticket 1", "Description 1");
		Ticket ticket2 = new Ticket("Test Ticket 2", "Description 2");
		Ticket ticket3 = new Ticket("Test Ticket 3", "Description 3");
		when(ticketRepo.getAllTickets()).thenReturn(Stream.of(ticket1, ticket2, ticket3).collect(Collectors.toList()));
		List<Ticket> tickets = tservice.getAllTickets();

		assertEquals(3, tickets.size());
	}

	@Test
	public void getTicketById() {
		Ticket ticket = new Ticket("Test Ticket 1", "Description 1");
		ticket.setId(1);
		when(ticketRepo.getTicketById(1l)).thenReturn(ticket);
		Ticket searchedTicket = tservice.getTicketById(1l);

		assertEquals(ticket.getId(), searchedTicket.getId());
		assertEquals(ticket.getTitle(), searchedTicket.getTitle());
		assertEquals(ticket.getDescription(), searchedTicket.getDescription());
	}

	@Test
	public void getTicketByStatus() {
		String statusDraft = "DRAFT";
		
		Ticket ticket1 = new Ticket("Test Ticket 1", "Description 1");
		Ticket ticket2 = new Ticket("Test Ticket 2", "Description 2");
		when(ticketRepo.getTicketByStatus(TicketStatus.valueOf(statusDraft))).thenReturn(List.of(ticket1, ticket2));
		List<Ticket> tickets = tservice.getTicketByStatus(statusDraft);
		assertEquals(2, tickets.size());
	}
}
