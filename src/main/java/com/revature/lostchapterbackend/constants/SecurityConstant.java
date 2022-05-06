package com.revature.lostchapterbackend.constants;

public class SecurityConstant {
	public static final long EXPIRATION_TIME = 432000000;			// 5 Days - expressed in milliseconds
	public static final String TOKEN_PREFIX = "BookMark ";				// this is sent if user is authenticated
	public static final String JWT_TOKEN_HEADER = "Jwt-Token";		// Custome/Default header
	public static final String TOKEN_CANNOT_BE_VERIFIED = "Token cannot be verified";
	public static final String PROJECT_3 = "SIERRA - LOST CHAPTER 2";
	public static final String PROJECT_3_ADMIN = "User Management Portal";
	public static final String AUTHORITIES = "authorities";
	public static final String FORBIDDEN_MESSAGE = "Please log in for access.";
	public static final String ACCESS_DENIED_MESSAGE = "You are not allowed to access this page.";
	public static final String OPTIONS_HTTP_METHOD = "OPTIONS";
	public static final String[] PUBLIC_URLS = { "/users/register", "/users/auth", "/book/featured", "/users","/book", "/book/{bookId}","/book/books/sales","/book/featured","/book/genre/{name}","/book/search/{key}","/reviews/{reviewId}","/reviews/book/{bookId}","/reviews"};
}
