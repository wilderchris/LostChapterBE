package com.revature.lostchapterbackend.exceptions;

public class UsernameAlreadyExists extends Exception {
	//This exception is thrown when trying to create a new user, but the username has already been taken

	private static final long serialVersionUID = 1L;

	public UsernameAlreadyExists(String message) {
		super(message);
	}

	public UsernameAlreadyExists(Throwable cause) {
		super(cause);
	}
}
