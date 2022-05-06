package com.revature.lostchapterbackend.exceptions;

public class UserNotFoundException extends Exception {
	//This exception is thrown when the program is unable to find a user in the database
	/**
	 * 
	 */
	private static final long serialVersionUID = 3396371978946917292L;

	public UserNotFoundException() {
		super();
	}

	public UserNotFoundException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public UserNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public UserNotFoundException(String message) {
		super(message);
	}

	public UserNotFoundException(Throwable cause) {
		super(cause);
	}
	
	

}
