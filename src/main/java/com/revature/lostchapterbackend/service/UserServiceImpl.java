package com.revature.lostchapterbackend.service;

import java.security.NoSuchAlgorithmException;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.revature.lostchapterbackend.JWT.UserDetail;
import com.revature.lostchapterbackend.dao.UserDAO;
import com.revature.lostchapterbackend.exceptions.InvalidLoginException;
import com.revature.lostchapterbackend.exceptions.UserNotFoundException;
import com.revature.lostchapterbackend.exceptions.UsernameAlreadyExists;
import com.revature.lostchapterbackend.model.User;

@Service
@Transactional
@Qualifier("UserDetailService")
public class UserServiceImpl implements UserService, UserDetailsService {
	//This service is used to handle all aspects of the User class and has the below methods
		//register: This methods responsibility is to sign up a new user
		//login: This methods responsibility is to log in the user
		//getUserById: This methods responsibility is to return a user by their userId
		//getUserByEmail: This methods responsibility is to return a user by their email
		//getUserByUsername:This methods responsibility is to return a user by their username 
		//update: This methods responsibility is to update a users information base on their userId
		//deleteUser: This methods responsibility is to delete a users information base on their userId
		//passwordHasher: This methods responsibility is to encode the users password for safe keeping
		//loadUserByUsername: Returns a users information?
	private UserDAO userDao;
	private BCryptPasswordEncoder hashPassword;
	
	@Autowired
	public UserServiceImpl(UserDAO userDao, BCryptPasswordEncoder hashPassword) {
		this.userDao = userDao;
		this.hashPassword = hashPassword;
	}
	
	@Override
	@Transactional
	public User register(User newUser) throws UsernameAlreadyExists {
		try 
		{
			String encryptedPassword = this.hashPassword.encode(newUser.getPassword());
			newUser.setPassword(encryptedPassword);
			newUser = userDao.save(newUser);
			return newUser;
		} catch(Exception e) {
			if(e.getMessage() != null && e.getMessage().contains("unique")) {
				throw new UsernameAlreadyExists("Username Already Exists! Try Again!");
			} else {
				return null;
			}
		}
	}

	
	@Override
	@Transactional
	public User login(String username, String password) throws UserNotFoundException, InvalidLoginException{
		User userFromDatabase = userDao.findByUsername(username);
		String encryptedPassword = this.hashPassword.encode(password);
		if (userFromDatabase != null && userFromDatabase.getPassword().equals(encryptedPassword)) 
		{
			return userFromDatabase;
		} else if (userFromDatabase == null ) {
			throw new UserNotFoundException();	
		} else {
			throw new InvalidLoginException();
		}
		
	}

	
	@Override
	@Transactional
	public User getUserById(int userId) {
		Optional<User> user = userDao.findById(userId);
		if (user.isPresent()) return user.get();
		else return null;
	}

	@Override
	@Transactional
	public User getUserByEmail(String email) {
		User user = userDao.findByEmail(email);
		return user;
				
	}

	@Override
	@Transactional
	public User getUserByUsername(String username) {
		User user = userDao.findByUsername(username.toLowerCase().replace(" ", ""));
		return user;
	}

	@Override
	@Transactional()
	public User update(User user) {
		if (userDao.existsById(user.getUserId())) {
			userDao.save(user);
			user = userDao.findById(user.getUserId()).get();
			return user;
		}
		return null;
	}

	@Override
	@Transactional
	public User deleteUser(User user) {
		User userFromDatabase = userDao.findById(user.getUserId()).get();
		if(userFromDatabase != null) {
			userDao.delete(userFromDatabase);
		}
		return userFromDatabase;
		
	}

	@Override
	@Transactional
	public String passwordHasher(String password) throws NoSuchAlgorithmException {
//		String algorithm = "SHA-256";
//		String hashedPassword = HashUtil.hashInputPassword(password.trim(), algorithm);
		return hashPassword.encode(password);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		User user = userDao.findByUsername(username);
		if(user==null) {
			throw new UsernameNotFoundException(username + "is not registered.");
		} else {
			UserDetail userDetail = new UserDetail(user);
			return userDetail;
		}
	}	
}


