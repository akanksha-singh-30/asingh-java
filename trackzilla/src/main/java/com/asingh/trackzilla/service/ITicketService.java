package com.asingh.trackzilla.service;

import java.util.List;

import com.asingh.trackzilla.model.Ticket;

public interface ITicketService {
	List<Ticket> getAllTickets();

	Ticket getTicketById(long ticketId);

	void addTicket(Ticket ticket);

	void updateTicket(Ticket ticket);

	void deleteTicket(long ticketId);

	void closeTicket(long ticketId);
}
