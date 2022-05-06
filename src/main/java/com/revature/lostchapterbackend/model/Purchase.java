package com.revature.lostchapterbackend.model;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.beans.factory.annotation.Autowired;

@Entity
@Table
public class Purchase {
	//This class deals with Purchase objects
	//Has the values of
		//Book book
		//Order order
		//int purchaseId, quantityToBuy
	//Has the special methods of
		//hashCode: hashes all of the Purchase information
		//equals: see if there is a matching Purchase in the database 
		//toString: converts all of the Purchase information into a string

	@Id
	@Column(name="purchase_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int purchaseId;

	@ManyToOne
	@JoinColumn(name="book_id")
	@Autowired
	private Book book;
	@Column(name="quantity_to_buy")
	private int quantityToBuy;
	@ManyToOne
	@JoinColumn(name="order_id")
	@Autowired
	private Order order;
	
	public Purchase() {
		this.purchaseId = 0;
		this.book = new Book();
		this.quantityToBuy=0;
	}

	public int getPurchaseId() {
		return purchaseId;
	}

	public void setPurchaseId(int purchaseId) {
		this.purchaseId = purchaseId;
	}

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	public int getQuantityToBuy() {
		return quantityToBuy;
	}

	public void setQuantityToBuy(int quantityToBuy) {
		this.quantityToBuy = quantityToBuy;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	@Override
	public String toString() {
		return "Purchase [purchaseId=" + purchaseId + ", book=" + book + ", quantityToBuy=" + quantityToBuy + ", order="
				+ order + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(book, order, purchaseId, quantityToBuy);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Purchase other = (Purchase) obj;
		return Objects.equals(book, other.book) && Objects.equals(order, other.order) && purchaseId == other.purchaseId
				&& quantityToBuy == other.quantityToBuy;
	}



	

	
}