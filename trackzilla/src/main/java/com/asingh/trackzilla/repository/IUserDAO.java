package com.asingh.trackzilla.repository;

import com.asingh.trackzilla.model.User;

public interface IUserDAO {

	public User getUserById(int id);
	
	public int createNewUser(User user);	
}
