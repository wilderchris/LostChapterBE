package com.revature.lostchapterbackend.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.revature.lostchapterbackend.JWT.TokenProvider;
import com.revature.lostchapterbackend.JWT.UserDetail;
import com.revature.lostchapterbackend.exceptions.UserNotFoundException;
import com.revature.lostchapterbackend.exceptions.UsernameAlreadyExists;
import com.revature.lostchapterbackend.model.User;
import com.revature.lostchapterbackend.service.UserService;

import com.revature.lostchapterbackend.constants.*;

@RestController
@RequestMapping(path="/users")
@CrossOrigin(origins = "http://localhost:4200",allowCredentials="true", exposedHeaders="Jwt-Token")
public class UserController {
	//This controller is used for the following
		//checkLogin GET /{userId}/auth
		//logIn POST /auth
		//getUserById GET /{userId}
		//getUserByEmail GET /email/{email}
		//getUserByUsername GET /username/{username}
		//updateUser PUT /{userId}
		//deleteUser DELETE /{userId}
	
	private static UserService userService;
	private static AuthenticationManager authenticationManager;
	private static TokenProvider tokenProvider;
	
	
	public UserController() {
		super();
	}
	
	@Autowired
	public UserController(UserService userService, AuthenticationManager authenticationManager, TokenProvider tokenProvider) {
		this.authenticationManager = authenticationManager;
		this.tokenProvider = tokenProvider;
		this.userService = userService;
	}
	
	@PostMapping
	public ResponseEntity<Map<String,Integer>> register(@RequestBody User newUser) throws UsernameAlreadyExists{
		//This methods responsibility is to sign up a new user
		//no checking if user already exists
		//no current path??
		try
		{
//			UserDetail userDetail = new UserDetail(newUser);
//			String token=tokenProvider.generateToken(userDetail);
			newUser = userService.register(newUser);
			Map<String, Integer> newIdMap = new HashMap<>();
			newIdMap.put("generatedId", newUser.getUserId());
			return ResponseEntity.status(HttpStatus.CREATED).body(newIdMap);
			
		}catch (UsernameAlreadyExists e)	{
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}
	}
				
	@PostMapping(path="/auth")
	public ResponseEntity<User> logIn(@RequestBody Map<String, String> credentials){
		//This methods responsibility is to log in the user

		String username = credentials.get("username");
		String password = credentials.get("password");
		

		try {
			//UserDetail userDetail1 = new UserDetail(userService.getUserByUsername(username));
			this.authenticate(username, password);
//			User user = userService.login(username, password);
			User user = userService.getUserByUsername(username);
			UserDetail userDetail = new UserDetail(user);
			HttpHeaders jwtHeader = this.getHeader(userDetail);
			
			String token = Integer.toString(user.getUserId());
			
			return new ResponseEntity<>(user, jwtHeader, HttpStatus.OK);

		} catch (UserNotFoundException e) {
			return ResponseEntity.notFound().build();
		}
	}
	
	@GetMapping(path="/{userId}/auth")
	public ResponseEntity<User> checkLogin(@PathVariable String userId,@RequestHeader("Authorization") String authorization) throws UserNotFoundException{
		
		try {
			String token = tokenProvider.extractToken(authorization);
			HttpHeaders jwtHeader = tokenProvider.getHeaderJWT(token);
			User loggedInPerson =userService.getUserById(Integer.parseInt(userId));
			if(loggedInPerson!=null)
				return new ResponseEntity<>(loggedInPerson, jwtHeader, HttpStatus.OK);
			else
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		catch (UserNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}

	@GetMapping(path="/{userId}")
	public ResponseEntity<User> getUserById(@PathVariable int userId) throws UserNotFoundException{
		//This methods responsibility is to return a user by their userId
		
		User user = userService.getUserById(userId);
		
		if (user != null) {
		return ResponseEntity.ok(user);
	} else
		return ResponseEntity.notFound().build();
	}
	
	@GetMapping(path="/email/{email}")
	public ResponseEntity<User> getUserByEmail(@PathVariable String email) throws UserNotFoundException{
		//This methods responsibility is to return a user by their email
		User user = userService.getUserByEmail(email);
		if (user != null) {
		return ResponseEntity.ok(user);
		} else
			return ResponseEntity.notFound().build();
	}
	
	@GetMapping(path="/username/{username}")
	public ResponseEntity<User> getUserByUsername(@PathVariable String username) throws UserNotFoundException{
		//This methods responsibility is to return a user by their username
		User user = userService.getUserByUsername(username);
		if (user != null) {
			return ResponseEntity.ok(user);
		} else
			return ResponseEntity.notFound().build();
	}

	
	@PutMapping(path="/{userId}")
	public ResponseEntity<User> updateUser(@PathVariable int userId,
			@RequestBody User userToEdit) throws UserNotFoundException {
		//This methods responsibility is to update a users information base on their userId
		//No checking if data is good
		
		if (userToEdit != null && userToEdit.getUserId() == userId) {
			userToEdit = userService.update(userToEdit);
			
			if (userToEdit != null)
				return ResponseEntity.ok(userToEdit);
			else
				return ResponseEntity.notFound().build();
		} else {
			// conflict: the id doesn't match the id of the user sent
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}
	}
	
	@DeleteMapping(path="/{userId}")
	public ResponseEntity<Void> deleteUser(@RequestBody User deleteUser) throws UserNotFoundException {
		//This methods responsibility is to delete a users information base on their userId
		if (deleteUser != null) {
			userService.deleteUser(deleteUser);
			return ResponseEntity.status(HttpStatus.CREATED).build();
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
	}

	private HttpHeaders getHeader(UserDetail userDetail) {
		HttpHeaders headers = new HttpHeaders();
		headers.add(SecurityConstant.JWT_TOKEN_HEADER, tokenProvider.generateToken(userDetail));
		
		return headers;
	}
	
	private void authenticate(String username, String pwd) {
		System.out.println("auth:"+new UsernamePasswordAuthenticationToken(username, pwd));
		this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, pwd));
	}
	//public String passwordHasher(String password) throws NoSuchAlgorithmException;
	//not sure how to go about creating the path for this one ^
}
