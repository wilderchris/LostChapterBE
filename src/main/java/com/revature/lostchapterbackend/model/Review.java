package com.revature.lostchapterbackend.model;

import java.time.LocalDateTime;
import java.util.Objects;

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
@Table
public class Review {
	//This class deals with Review objects
	//Has the values of
		//Book book
		//User user
		//int reviewId, ratingOne, ratingTwo, ratingThree
		//string reviewTitle, reviewText
		//LocalDateTime sentAt
	//Has the special methods of
		//hashCode: hashes all of the Review information
		//equals: see if there is a matching Review in the database 
		//toString: converts all of the Review information into a string
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int reviewId;

	@ManyToOne
	@JoinColumn(name="book_id")
	private Book book;
	
	@OneToOne
	@JoinColumn(name="user_id")
	private User user;
	
	private String reviewTitle;
	private String reviewText;
	//rating names to be set on the front end based on book genre
	//For Fiction, ratingOne is Plot
	//For Non-Fiction, ratingOne is ???
	
	private int ratingOne;
	
	//For Fiction, ratingTwo is Pacing
	//For Non-Fiction, ratingTwo is ???
	
	private int ratingTwo;
	
	//For Fiction, ratingThree is Prose
	//For Non-Fiction, ratingThree is ???
	
	private int ratingThree;

	private LocalDateTime sentAt;
	
	public Review(int reviewId, Book book, User user, String reviewTitle, String reviewText, int ratingOne,
			int ratingTwo, int ratingThree, LocalDateTime sentAt) {
		super();
		this.reviewId = reviewId;
		this.book = book;
		this.user = user;
		this.reviewTitle = reviewTitle;
		this.reviewText = reviewText;
		this.ratingOne = ratingOne;
		this.ratingTwo = ratingTwo;
		this.ratingThree = ratingThree;
		this.sentAt = sentAt;
	}
	
	public Review() {
		super();
		this.reviewId = 0;
		this.book = new Book();
		this.user = new User();
		this.reviewTitle = "";
		this.reviewText = "";
		this.ratingOne = 0;
		this.ratingTwo = 0;
		this.ratingThree = 0;
		this.sentAt = null;
	}
	
	public double getOverallRating() {
		return (ratingOne + ratingTwo + ratingThree)/3;
		
	}

	public int getReviewId() {
		return reviewId;
	}
	public void setReviewId(int reviewId) {
		this.reviewId = reviewId;
	}
	public Book getBook() {
		return book;
	}
	public void setBook(Book book) {
		this.book = book;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public String getReviewTitle() {
		return reviewTitle;
	}
	public void setReviewTitle(String reviewTitle) {
		this.reviewTitle = reviewTitle;
	}
	public String getReviewText() {
		return reviewText;
	}
	public void setReviewText(String reviewText) {
		this.reviewText = reviewText;
	}
	public int getRatingOne() {
		return ratingOne;
	}
	public void setRatingOne(int ratingOne) {
		this.ratingOne = ratingOne;
	}
	public int getRatingTwo() {
		return ratingTwo;
	}
	public void setRatingTwo(int ratingTwo) {
		this.ratingTwo = ratingTwo;
	}
	public int getRatingThree() {
		return ratingThree;
	}
	public void setRatingThree(int ratingThree) {
		this.ratingThree = ratingThree;
	}
	public LocalDateTime getSentAt() {
		return sentAt;
	}
	public void setSentAt(LocalDateTime sentAt) {
		this.sentAt = sentAt;
	}
	@Override
	public int hashCode() {
		return Objects.hash(book, ratingOne, ratingThree, ratingTwo, reviewId, reviewText, reviewTitle, sentAt, user);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Review other = (Review) obj;
		return Objects.equals(book, other.book) && ratingOne == other.ratingOne && ratingThree == other.ratingThree
				&& ratingTwo == other.ratingTwo && reviewId == other.reviewId
				&& Objects.equals(reviewText, other.reviewText) && Objects.equals(reviewTitle, other.reviewTitle)
				&& Objects.equals(sentAt, other.sentAt) && Objects.equals(user, other.user);
	}
	@Override
	public String toString() {
		return "Review [reviewId=" + reviewId + ", book=" + book + ", user=" + user + ", reviewTitle=" + reviewTitle
				+ ", reviewText=" + reviewText + ", ratingOne=" + ratingOne + ", ratingTwo=" + ratingTwo
				+ ", ratingThree=" + ratingThree + ", sentAt=" + sentAt + "]";
	}	
}