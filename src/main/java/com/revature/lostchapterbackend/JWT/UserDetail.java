package com.revature.lostchapterbackend.JWT;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.revature.lostchapterbackend.constants.AuthorityConstant;
import com.revature.lostchapterbackend.model.User;

import static java.util.Arrays.stream;

public class UserDetail implements UserDetails {

	private User user;
	private String[] userAuthorities;
	
		
	public UserDetail(User user) {
		this.user = user;
		
		if(this.user.getRole() == "DEV") {
			this.userAuthorities = AuthorityConstant.DEV_AUTHORITIES;
		} else if(this.user.getRole() == "ADMIN") {
			this.userAuthorities = AuthorityConstant.ADMIN_AUTHORITIES;
		} else { //if(this.role == "USER") { //Default
			this.userAuthorities = AuthorityConstant.CUSTOMER_AUTHORITIES;
		}
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return stream(this.getUserAuthorities()).map(SimpleGrantedAuthority::new).collect(Collectors.toList());
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return this.user.getPassword();
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return this.user.getUsername();
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return this.user.isNotLocked();
	}


	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return this.user.isActive();
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	/**
	 * @return the user
	 */
	public User getUser() {
		return user;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * @return the userAuthorities
	 */
	public String[] getUserAuthorities() {
		return userAuthorities;
	}

	/**
	 * @param userAuthorities the userAuthorities to set
	 */
	public void setUserAuthorities(String[] userAuthorities) {
		this.userAuthorities = userAuthorities;
	}
}
