package com.revature.lostchapterbackend.service;

import java.security.NoSuchAlgorithmException;

import org.springframework.stereotype.Service;

import com.revature.lostchapterbackend.exceptions.InvalidLoginException;
import com.revature.lostchapterbackend.exceptions.UserNotFoundException;
import com.revature.lostchapterbackend.exceptions.UsernameAlreadyExists;
import com.revature.lostchapterbackend.model.User;

@Service
public interface UserService {
	public User login(String username, String password) throws UserNotFoundException, InvalidLoginException;
	public User register(User newUser) throws UsernameAlreadyExists;
	public User getUserById(int userId) throws UserNotFoundException;
	public User getUserByEmail(String email) throws UserNotFoundException;
	public User getUserByUsername(String username) throws UserNotFoundException;
	public User update(User user) throws UserNotFoundException;
	public User deleteUser(User user) throws UserNotFoundException;
	public String passwordHasher(String password) throws NoSuchAlgorithmException;
	
}
