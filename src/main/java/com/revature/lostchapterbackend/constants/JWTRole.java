package com.revature.lostchapterbackend.constants;

import static com.revature.lostchapterbackend.constants.AuthorityConstant.*;

public enum JWTRole {

	ROLE_CUSTOMER(CUSTOMER_AUTHORITIES),
	ROLE_ADMIN(ADMIN_AUTHORITIES),
	ROLE_DEV(DEV_AUTHORITIES);
	
	private String[] authorities;
	
	JWTRole(String... authorities){
		this.authorities = authorities;
	}
	
	public String[] getAuthorities() {
		return authorities;
	}
}
