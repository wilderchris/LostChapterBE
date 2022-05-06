package com.revature.lostchapterbackend.model;

import java.time.LocalDate;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="usr")
public class User {
	//This class deals with User objects
	//Has the values of
		//string username, password, firstName, lastname, password
		//LocalDate birthday
		//boolean isActive, isNotLocked
	//Has the special methods of
		//hashCode: hashes all of the User information
		//equals: see if there is a matching User in the database 
		//toString: converts all of the User information into a string
	@Id
	@Column(name="user_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int userId;
	
	@Column(name="username")
	private String username;
	@Column(name="passwrd")
	private String password;
	@Column(name="first_name")
	private String firstName;
	@Column(name="last_name")
	private String lastName;
	@Column(name="email")
	private String email;
	@Column(name="birthday")
	private LocalDate birthday;

	@Column(name="user_role")
	private String role;
	
	@Column(name="is_active")
	private boolean isActive;
	
	@Column(name="is_not_locked")
	private boolean isNotLocked;
	
	public User() {
		super();
	}
public User(int userId) {
	super();
	this.userId = userId;
}
	public User(String username, String password, String firstName, String lastName,  String email,
			LocalDate birthday, String role) {
		super();
		this.username = username;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.birthday = birthday;
		this.role = role;
		this.isNotLocked=true;
		this.isActive=true;
	}
	

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public boolean isNotLocked() {
		return isNotLocked;
	}

	public void setNotLocked(boolean isNotLocked) {
		this.isNotLocked = isNotLocked;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public LocalDate getBirthday() {
		return birthday;
	}

	public void setBirthday(LocalDate birthday) {
		this.birthday = birthday;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	@Override
	public String toString() {
		return "User [userId=" + userId + ", username=" + username + ", password=" + password + ", firstName="
				+ firstName + ", lastName=" + lastName + ", email=" + email + ", birthday=" + birthday + ", role="
				+ role + ", isActive=" + isActive + ", isNotLocked=" + isNotLocked + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(birthday, email, firstName, isActive, isNotLocked, lastName, password, role, userId,
				username);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		return Objects.equals(birthday, other.birthday) && Objects.equals(email, other.email)
				&& Objects.equals(firstName, other.firstName) && isActive == other.isActive
				&& isNotLocked == other.isNotLocked && Objects.equals(lastName, other.lastName)
				&& Objects.equals(password, other.password) && Objects.equals(role, other.role)
				&& userId == other.userId && Objects.equals(username, other.username);
	}



	
}
