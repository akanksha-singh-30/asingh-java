package com.asingh.trackzilla.service;

import java.util.List;
import java.util.Optional;

import com.asingh.trackzilla.model.Application;

public interface IApplicationService {
	
	public boolean createApplication(Application application);
	
	public Optional<Application> findApplicationById(long appId);
	
	public Optional<Application> findByNameAndOwner(String name, String owner);

	public Optional<List<Application>> getAllApplications();
	
	public boolean createOrSaveApplication(Application application);
	
	public boolean updateApplication(Application application);
	
	public boolean deleteApplication(long appId);
}
