package com.revature.lostchapterbackend.exceptions;

public class BookNotFoundException extends Exception {

	/**This exception is thrown when the program is unable to find a book in the database with the given id
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BookNotFoundException() {
		super();

	}

	public BookNotFoundException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);

	}

	public BookNotFoundException(String message, Throwable cause) {
		super(message, cause);


	}


	
}
