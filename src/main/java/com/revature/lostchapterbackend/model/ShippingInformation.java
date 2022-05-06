package com.revature.lostchapterbackend.model;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.beans.factory.annotation.Autowired;

@Entity
@Table
public class ShippingInformation {
	//This class deals with ShippingInformation objects
	//Has the values of
		//string firstName, lastname, streetname, city, state, zipcode, deliveryDate
	//Has the special methods of
		//hashCode: hashes all of the ShippingInformation information
		//equals: see if there is a matching ShippingInformation in the database 
		//toString: converts all of the ShippingInformation information into a string

	@Id
	@Column(name="shipping_info_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int shippingInfoId;

	private String firstName;
	private String lastName;
	private String streetName;
	private String city;
	private String state;
	private String zipCode;
	private String deliveryDate; 
	@ManyToOne
	@JoinColumn(name="user_id")
	@Autowired
	private User user;
	public ShippingInformation() {
		super();
	}

	public ShippingInformation(String firstName, String lastName, String streetName, String city, String state,
			String zipCode, String deliveryDate) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.streetName = streetName;
		this.city = city;
		this.state = state;
		this.zipCode = zipCode;
		this.deliveryDate = deliveryDate;
	}

	public int getShippingInfoId() {
		return shippingInfoId;
	}

	public void setShippingInfoId(int shippingInfoId) {
		this.shippingInfoId = shippingInfoId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getStreetName() {
		return streetName;
	}

	public void setStreetName(String streetName) {
		this.streetName = streetName;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getDeliveryDate() {
		return deliveryDate;
	}

	public void setDeliveryDate(String deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "ShippingInformation [shippingInfoId=" + shippingInfoId + ", firstName=" + firstName + ", lastName="
				+ lastName + ", streetName=" + streetName + ", city=" + city + ", state=" + state + ", zipCode="
				+ zipCode + ", deliveryDate=" + deliveryDate + ", user=" + user + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(city, deliveryDate, firstName, lastName, shippingInfoId, state, streetName, user, zipCode);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ShippingInformation other = (ShippingInformation) obj;
		return Objects.equals(city, other.city) && Objects.equals(deliveryDate, other.deliveryDate)
				&& Objects.equals(firstName, other.firstName) && Objects.equals(lastName, other.lastName)
				&& shippingInfoId == other.shippingInfoId && Objects.equals(state, other.state)
				&& Objects.equals(streetName, other.streetName) && Objects.equals(user, other.user)
				&& Objects.equals(zipCode, other.zipCode);
	}

	
	

}