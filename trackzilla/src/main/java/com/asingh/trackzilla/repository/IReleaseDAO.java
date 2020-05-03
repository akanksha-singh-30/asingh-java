package com.asingh.trackzilla.repository;

import java.util.List;

import com.asingh.trackzilla.model.Release;

public interface IReleaseDAO {
	
	public void addRelease(Release release);

	public void addApplication(Long appId, Long releaseId);

	public Release getReleaseById(long releaseId);
	
	public List<Release> getAllReleases() ;
}
