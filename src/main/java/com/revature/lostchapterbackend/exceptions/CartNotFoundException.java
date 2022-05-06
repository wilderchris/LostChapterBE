package com.revature.lostchapterbackend.exceptions;

public class CartNotFoundException extends Exception {
	//This exception is thrown when the program is unable to find a cart in the database with the given id

	private static final long serialVersionUID = 1L;

	public CartNotFoundException(String message) {
		super(message);
	}

	public CartNotFoundException(Throwable cause) {
		super(cause);
	}
}
