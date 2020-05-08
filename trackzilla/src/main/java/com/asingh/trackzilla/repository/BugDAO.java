package com.asingh.trackzilla.repository;

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
}
