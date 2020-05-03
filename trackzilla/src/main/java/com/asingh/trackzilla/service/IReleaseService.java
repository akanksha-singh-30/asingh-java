package com.asingh.trackzilla.service;

import java.util.List;
import java.util.Optional;

import com.asingh.trackzilla.model.Release;

public interface IReleaseService {
	public void addRelease(Release release);

	public void addApplication(Long appId, Long releaseId);

	public Optional<List<Release>> getAllReleases();
}
