package com.revature.lostchapterbackend.model;

import java.time.LocalDateTime;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.beans.factory.annotation.Autowired;

@Entity
@Table(name="ordr")
public class Order {
	//This class deals with Order objects
	//Has the values of
		//LocalDateTime transactionDate
		//int orderId
		//float totalPrice
	//Has the special methods of
		//hashCode: hashes all of the order information
		//equals: see if there is a matching order in the database 
		//toString: converts all of the order information into a string
	
	@Id
	@Column(name="order_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int orderId;
	@Column(name="total_price")
	private float totalPrice;
	@Column(name="transaction_date")
	private LocalDateTime transactionDate;
	
	@ManyToOne
	@JoinColumn(name="user_id")
	@Autowired
	private User user;

	public Order() {

		this.orderId = 0;
		this.totalPrice=0.0f;
		this.transactionDate=null;
		//this.user=new User();

	}

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public float getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(float totalPrice) {
		this.totalPrice = totalPrice;
	}

	public LocalDateTime getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(LocalDateTime transactionDate) {
		this.transactionDate = transactionDate;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public int hashCode() {
		return Objects.hash(orderId, totalPrice, transactionDate, user);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Order other = (Order) obj;
		return orderId == other.orderId && Float.floatToIntBits(totalPrice) == Float.floatToIntBits(other.totalPrice)
				&& Objects.equals(transactionDate, other.transactionDate) && Objects.equals(user, other.user);
	}

	@Override
	public String toString() {
		return "Order [orderId=" + orderId + ", totalPrice=" + totalPrice + ", transactionDate=" + transactionDate
				+ ", user=" + user + "]";
	}

	
	


}
