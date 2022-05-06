package com.revature.lostchapterbackend.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.revature.lostchapterbackend.model.User;

@Repository
public interface UserDAO extends JpaRepository <User, Integer>{
	//This DAO is used to hold User objects which can be found by their ids
	//Methods include
		//findByEmail: finds a user by using an email
		//findByUsername: finds a user by using an username

	public User findByEmail(String email);
	public User findByUsername(String username);

}
