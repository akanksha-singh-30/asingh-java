package com.asingh.trackzilla.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.asingh.trackzilla.model.Bug;
import com.asingh.trackzilla.repository.BugDAO;

@Service
@Transactional
public class BugService implements IBugService {

	@Autowired
	private BugDAO dao;

	@Override
	public void addBug(Bug bug) {
		dao.addBug(bug);
	}
	
	public List<Bug> getAllBugs() {
		return dao.getAllBugs();
	}
}
