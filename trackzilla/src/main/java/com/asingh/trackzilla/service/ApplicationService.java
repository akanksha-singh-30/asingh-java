package com.asingh.trackzilla.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.asingh.trackzilla.model.Application;
import com.asingh.trackzilla.repository.ApplicationJpaRepository;

@Service
public class ApplicationService implements IApplicationService {

	@Autowired
	private ApplicationJpaRepository repository;

	@Override
	public Optional<List<Application>> getAllApplications() {
		return repository.getApplications();
	}

	@Override
	public Optional<Application> findApplicationById(long appId) {
		return repository.findById(appId);
	}

	@Override
	public Optional<Application> findByNameAndOwner(String name, String owner) {
		return repository.findByNameAndOwner(name, owner);
	}

	@Override
	public boolean createApplication(Application application) {
		try {
			if (repository.isApplicationExists(application.getName(), application.getOwner())) {
				return false;
			} else {
				return repository.createApplication(application);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public synchronized boolean updateApplication(Application application) {
		try {
			Optional<Application> opApp = repository.findByNameAndOwner(application.getName(), application.getOwner());		
			if (opApp.isPresent()) {
				Application dbApp = opApp.get();
				dbApp.setName(application.getName());
				dbApp.setOwner(application.getOwner());
				dbApp.setDescription(application.getDescription());				
				return repository.updateApplication(dbApp);
			} else {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean deleteApplication(long appId) {
		try {
			return repository.deleteById(appId) ;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public synchronized boolean createOrSaveApplication(Application application) {
		try {
			Optional<Application> appOptional = repository.findByNameAndOwner(application.getDescription(),
					application.getOwner());
			return appOptional.isPresent() ? repository.updateApplication(application)
					: repository.createApplication(application);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

}
