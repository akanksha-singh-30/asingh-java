package com.asingh.trackzilla.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.asingh.trackzilla.model.Enhancement;

@Repository
public class EnhancementDAO implements IEnhancementDAO {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public void addEnhancement(Enhancement enhancement) {
		entityManager.persist(enhancement);
	}

	public List<Enhancement> getAllEnhancements() {
		return entityManager.createNamedQuery("Enhancement.getAllEnhancements", Enhancement.class).getResultList();
	}
}
