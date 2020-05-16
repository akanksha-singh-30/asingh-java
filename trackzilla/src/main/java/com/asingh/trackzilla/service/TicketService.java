package com.asingh.trackzilla.service;

import java.util.List;
import java.util.stream.Stream;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.asingh.trackzilla.enums.TicketStatus;
import com.asingh.trackzilla.model.Ticket;
import com.asingh.trackzilla.repository.TicketDAO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
public class TicketService implements ITicketService {

	@Autowired
	TicketDAO repository;

	@Override
	public List<Ticket> getAllTickets() {
		return repository.getAllTickets();
	}

	@Override
	public Ticket getTicketById(long ticketId) {
		return repository.getTicketById(ticketId);
	}

	@Override
	public void addTicket(Ticket ticket) {
		repository.addTicket(ticket);
	}

	@Override
	public void updateTicket(Ticket ticket) {
		repository.updateTicket(ticket);
	}

	@Override
	public void deleteTicket(long ticketId) {
		repository.deleteTicket(ticketId);
	}

	@Override
	public synchronized void closeTicket(long ticketId) {
		Ticket ticket = repository.getTicketById(ticketId);
		if (ticket == null)
			return;
		ticket.setStatus(TicketStatus.CLOSED);
		repository.updateTicket(ticket);
	}

	public List<Ticket> getTicketByStatus(String status) {
		if (!Stream.of(TicketStatus.values()).anyMatch(e -> e.toString().equals(status))) {
			log.error(status + " is not a valid Ticket status");
			throw new IllegalArgumentException(status + " is not a valid Ticket status");
		}
		return repository.getTicketByStatus(TicketStatus.valueOf(status));
	}
}
