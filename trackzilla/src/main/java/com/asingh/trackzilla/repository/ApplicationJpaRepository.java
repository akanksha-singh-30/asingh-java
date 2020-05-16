package com.asingh.trackzilla.repository;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.asingh.trackzilla.model.Application;

@Transactional
@Repository
public class ApplicationJpaRepository implements IApplicationDAO {

	@PersistenceContext
	EntityManager entityManager;

	@Override
	public Optional<Application> findById(long appId) {
		Application app = entityManager.find(Application.class, appId);
		if (app == null) {
			return Optional.empty();
		} else {
			return Optional.of(app);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public Optional<List<Application>> getApplications() {
		String query = "select a from Application a";
		List<Application> applications = entityManager.createQuery(query).getResultList();
		if (applications.size() > 0) {
			return Optional.of(applications);
		} else {
			return Optional.empty();
		}
	}

	@Override
	public boolean isApplicationExists(String name, String owner) {
		String jpql = "from Application a where a.name = ?1 and a.owner = ?2";
		int count = entityManager.createQuery(jpql).setParameter(1, name).setParameter(2, owner).getResultList().size();
		return count > 0;
	}

	@Override
	@SuppressWarnings("unchecked")
	public Optional<Application> findByNameAndOwner(String name, String owner) {
		String jpql = "from Application a where a.name = :name and a.owner = :owner";
		List<Application> applications = (List<Application>) entityManager.createQuery(jpql).setParameter("name", name)
				.setParameter("owner", owner).getResultList();
		if (applications.size() > 0) {
			return Optional.of(applications.get(0));
		} else {
			return Optional.empty();
		}
	}

	@Override
	public boolean createApplication(Application application) {
		try {
			entityManager.persist(application);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean deleteById(long appId) {
		try {
			entityManager.remove(findById(appId)
					.orElseThrow(() -> new EntityNotFoundException("Application not found with id :" + appId)));
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean deleteApplication(String name, String owner) {
		try {
			entityManager.remove(findByNameAndOwner(name, owner).orElseThrow(() -> new EntityNotFoundException(
					"Application not found with name :" + name + " owner :" + owner)));
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean updateApplication(Application app) {
		try {
			entityManager.flush();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public long saveAppWithHib(Application app) {
		return (long) entityManager.unwrap(Session.class).save(app);
	}
}
