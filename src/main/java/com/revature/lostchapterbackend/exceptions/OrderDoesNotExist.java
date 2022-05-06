package com.revature.lostchapterbackend.exceptions;

public class OrderDoesNotExist extends Exception {
	//This exception is thrown when the program is unable to find an order in the database with the given id
 
	private static final long serialVersionUID = 1L;

	public OrderDoesNotExist(String message) {
		super(message);
	}

	public OrderDoesNotExist(Throwable cause) {
		super(cause);
	}
}
