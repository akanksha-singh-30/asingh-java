package com.asingh.trackzilla.repository;

import java.util.List;

import com.asingh.trackzilla.model.Ticket;

public interface ITicketDAO {	
    List<Ticket> getAllTickets();
    
    void addTicket(Ticket ticket);
    
    Ticket getTicketById(long ticketId);
    
    void updateTicket(Ticket ticket);
    
    void deleteTicket(long ticketId);
  
}
