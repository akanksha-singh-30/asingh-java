package com.asingh.trackzilla.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.asingh.trackzilla.model.Bug;

@Repository
public class BugDAO implements IBugDAO {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public void addBug(Bug bug) {
		entityManager.persist(bug);
	}

	public List<Bug> getAllBugs() {
		return entityManager.createNamedQuery("Bug.getAllBugs", Bug.class).getResultList();
	}
	
	public List<Bug> getSevereBugs() {
		return entityManager.createNamedQuery("Bug.getSevereBugs", Bug.class).getResultList();
	}
}
