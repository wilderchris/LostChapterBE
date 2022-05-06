package com.revature.lostchapterbackend.constants;

public class AuthorityConstant {
	public static final String[] CUSTOMER_AUTHORITIES = { "READ" };
	public static final String[] ADMIN_AUTHORITIES = { "READ", "CREATE", "UPDATE" };
	public static final String[] DEV_AUTHORITIES = { "READ", "CREATE", "UPDATE", "DELETE" };
}
