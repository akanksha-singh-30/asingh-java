package com.asingh.trackzilla.repository;

import java.util.List;
import java.util.Optional;

import com.asingh.trackzilla.model.Application;

public interface IApplicationDAO {
	
	public Optional<Application> findById(long appId) ;
	
	public Optional<List<Application>> getApplications();
 
	public boolean isApplicationExists(String name, String owner) ;

	public Optional<Application> findByNameAndOwner(String name, String owner) ;

	public boolean createApplication(Application app) ;

	public boolean updateApplication(Application app);
	
	public boolean deleteById(long appId) ;

	public boolean deleteApplication(String name, String owner) ;

}
