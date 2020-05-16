package com.asingh.trackzilla.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.asingh.trackzilla.model.Enhancement;
import com.asingh.trackzilla.repository.EnhancementDAO;

@Service
@Transactional
public class EnhancementService implements IEnhancementService {

	@Autowired
	private EnhancementDAO dao;

	@Override
	public void addEnhancement(Enhancement enhancement) {
		dao.addEnhancement(enhancement);
	}
	
	public List<Enhancement> getAllEnhancements() {
		return dao.getAllEnhancements();
	}
}
