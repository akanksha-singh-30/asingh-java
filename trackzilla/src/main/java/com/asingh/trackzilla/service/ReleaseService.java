package com.asingh.trackzilla.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.asingh.trackzilla.model.Release;
import com.asingh.trackzilla.repository.IReleaseDAO;

@Transactional
@Service
public class ReleaseService implements IReleaseService {

	@Autowired
	protected IReleaseDAO dao;

	@Override
	public void addRelease(Release release) {
		dao.addRelease(release);
	}

	@Override
	public void addApplication(Long appId, Long releaseId) {
		dao.addApplication(appId, releaseId);
	}

	public Optional<List<Release>> getAllReleases() {
		List<Release> releases = dao.getAllReleases();
		return (releases == null || releases.size() <= 0) ? Optional.empty() : Optional.of(releases);
	}

}
