package com.asingh.trackzilla.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Repository;

import com.asingh.trackzilla.enums.TicketStatus;
import com.asingh.trackzilla.model.Ticket;

@Repository
@Transactional
public class TicketDAO implements ITicketDAO {

	@PersistenceContext
	private EntityManager entityManager;

	/*
	 * @SuppressWarnings("unchecked")
	 * 
	 * @Override public List<Ticket> getAllTickets() { String jpql =
	 * "select t from Ticket t order by t.title"; return (List<Ticket>)
	 * entityManager.createQuery(jpql).getResultList(); }
	 */

	@Override
	public List<Ticket> getAllTickets() {
		return entityManager.createNamedQuery("Ticket.getGenericTickets", Ticket.class).getResultList();
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
		Ticket dbTicket = getTicketById(ticket.getId());
		if (dbTicket == null) {
			throw new ResourceNotFoundException(
					"No Ticket found with ID:" + ticket.getId() + " and Title:" + ticket.getTitle());
		}
		dbTicket.setApplication(ticket.getApplication());
		dbTicket.setDescription(ticket.getDescription());
		dbTicket.setRelease(ticket.getRelease());
		dbTicket.setStatus(ticket.getStatus());
		dbTicket.setTitle(ticket.getTitle());
		entityManager.flush();
	}

	@Override
	public void deleteTicket(long ticketId) {
		entityManager.remove(getTicketById(ticketId));
	}

	public List<Ticket> getTicketByStatus(TicketStatus status) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Ticket> query = builder.createQuery(Ticket.class);
		Root<Ticket> ticket = query.from(Ticket.class);
		query.select(ticket);
		query.where(builder.equal(ticket.get("status"), status));
		query.orderBy(builder.desc(ticket.get("id")));
		return entityManager.createQuery(query).getResultList();
	}

}
