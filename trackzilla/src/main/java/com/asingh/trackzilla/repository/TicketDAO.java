package com.asingh.trackzilla.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.asingh.trackzilla.model.Ticket;

@Repository
@Transactional
public class TicketDAO implements ITicketDAO {

	@PersistenceContext
	private EntityManager entityManager;

	@SuppressWarnings("unchecked")
	@Override
	public List<Ticket> getAllTickets() {
		String jpql = "select t from Ticket t order by t.title";
		return (List<Ticket>) entityManager.createQuery(jpql).getResultList();
	}

	@Override
	public void addTicket(Ticket ticket) {
		entityManager.persist(ticket);
	}

	@Override
	public Ticket getTicketById(long ticketId) {
		return entityManager.find(Ticket.class, ticketId);
	}

	@Override
	public void updateTicket(Ticket ticket) {
		entityManager.flush();
	}

	@Override
	public void deleteTicket(long ticketId) {
		entityManager.remove(getTicketById(ticketId));
	}

	@Override
	public void closeTicket(long ticketId) {
		// TODO Close ticket updates the status of the ticket to "Closed". This should
		// be handled by the service layer

	}
}
