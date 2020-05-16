package com.asingh.trackzilla.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.asingh.trackzilla.model.User;

@Repository
@Transactional
public class UserDAO implements IUserDAO {

	@PersistenceContext
	EntityManager entityManager;
	
//	private SessionFactory factory;
	
//	private Session session;

//	@Autowired
//	public void setSession() {
//		this.session = entityManager.unwrap(Session.class);
//	}

	public User getUserById(int id) {
		return entityManager.find(User.class, id);
	}

	public int createNewUser(User user) {		
		return (int) entityManager.unwrap(Session.class).save(user);		
	}

}
