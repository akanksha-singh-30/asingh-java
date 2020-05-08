package com.asingh.trackzilla.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.asingh.trackzilla.model.Application;
import com.asingh.trackzilla.model.Release;

@Repository
public class ReleaseDAO implements IReleaseDAO {

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	private IApplicationDAO applicationDAO;

	@Override
	public void addRelease(Release release) {
		entityManager.persist(release);
	}

	@Override
	public void addApplication(Long appId, Long releaseId) {
		Release release = getReleaseById(releaseId);
		Application application = applicationDAO.findById(appId).get();
		release.addApplication(application);
		entityManager.flush();
	}

	@Override
	public Release getReleaseById(long releaseId) {
		return entityManager.find(Release.class, releaseId);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Release> getAllReleases() {
		String jpql = "select r from Release r order by r.releaseDate desc";
		return entityManager.createQuery(jpql).getResultList();
	}

}
