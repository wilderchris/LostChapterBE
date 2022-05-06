package com.revature.lostchapterbackend.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties({"hibernateLazyInitializer"})
@Entity
@Table(name="book")
public class Book {
	//This is the second most important object in this project
	//Has the values of
		//String ISBN, bookName, synopsis, author, genre, publisher, bookImage
		//int year, quantityOnHand
		//boolean saleIsActive, featured
		//float bookPrice, saleDiscountRate, saleDiscountRate
	//Has the special methods of
		//hashCode: hashes all of the book information
		//equals: see if there is a matching book in the database 
		//toString: converts all of the books information into a string

	@Id
	@Column(name="book_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int bookId;

	@Column(name="isbn")
	private String isbn;

	@Column(name="book_name")
	private String bookName;

	@Column(name="synopsis")
	private String synopsis;

	@Column(name="author")
	private String author;

	@Column(name="genre")
	private String genre;

	@Column(name="year")
	private int year;

	@Column(name="publisher")
	private String publisher;

	@Column(name="book_image")
	private String bookImage;
	
	@Column(name="sale_is_active")
	private boolean saleIsActive;
	@Column(name="featured")
	private boolean featured;
	@Column(name="book_price")
	private float bookPrice;
	@Column(name="sale_discount_rate")
	private float saleDiscountRate;
	@Column(name="quantity_on_hand")
	private int quantityOnHand;
	
	public Book(int bookId) {
		super();
		this.bookId = bookId;
	}
	public Book() {
		bookId = 0;
		isbn = " ";
		bookName = " ";
		synopsis = "";
		author = "";
		genre = "unknown";
		year = 0;
		publisher = "";
		bookImage = "";
		saleIsActive=false;
		featured=false;
		bookPrice=0.0f;
		saleDiscountRate=0.0f;
		quantityOnHand=0;
	}
	public int getBookId() {
		return bookId;
	}
	public void setBookId(int bookId) {
		this.bookId = bookId;
	}
	public String getIsbn() {
		return isbn;
	}
	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}
	public String getBookName() {
		return bookName;
	}
	public void setBookName(String bookName) {
		this.bookName = bookName;
	}
	public String getSynopsis() {
		return synopsis;
	}
	public void setSynopsis(String synopsis) {
		this.synopsis = synopsis;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getGenre() {
		return genre;
	}
	public void setGenre(String genre) {
		this.genre = genre;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public String getPublisher() {
		return publisher;
	}
	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}
	public String getBookImage() {
		return bookImage;
	}
	public void setBookImage(String bookImage) {
		this.bookImage = bookImage;
	}
	public boolean isSaleIsActive() {
		return saleIsActive;
	}
	public void setSaleIsActive(boolean saleIsActive) {
		this.saleIsActive = saleIsActive;
	}
	public boolean isFeatured() {
		return featured;
	}
	public void setFeatured(boolean featured) {
		this.featured = featured;
	}
	public float getBookPrice() {
		return bookPrice;
	}
	public void setBookPrice(float bookPrice) {
		this.bookPrice = bookPrice;
	}
	public float getSaleDiscountRate() {
		return saleDiscountRate;
	}
	public void setSaleDiscountRate(float saleDiscountRate) {
		this.saleDiscountRate = saleDiscountRate;
	}
	public int getQuantityOnHand() {
		return quantityOnHand;
	}
	public void setQuantityOnHand(int quantityOnHand) {
		this.quantityOnHand = quantityOnHand;
	}
	
	@Override
	public String toString() {
		return "Book [bookId=" + bookId + ", isbn=" + isbn + ", bookName=" + bookName + ", synopsis=" + synopsis
				+ ", author=" + author + ", genre=" + genre + ", year=" + year + ", publisher=" + publisher
				+ ", bookImage=" + bookImage + ", saleIsActive=" + saleIsActive + ", featured=" + featured
				+ ", bookPrice=" + bookPrice + ", saleDiscountRate=" + saleDiscountRate + ", quantityOnHand="
				+ quantityOnHand + "]";
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((author == null) ? 0 : author.hashCode());
		result = prime * result + bookId;
		result = prime * result + ((bookImage == null) ? 0 : bookImage.hashCode());
		result = prime * result + ((bookName == null) ? 0 : bookName.hashCode());
		result = prime * result + Float.floatToIntBits(bookPrice);
		result = prime * result + (featured ? 1231 : 1237);
		result = prime * result + ((genre == null) ? 0 : genre.hashCode());
		result = prime * result + ((isbn == null) ? 0 : isbn.hashCode());
		result = prime * result + ((publisher == null) ? 0 : publisher.hashCode());
		result = prime * result + quantityOnHand;
		result = prime * result + Float.floatToIntBits(saleDiscountRate);
		result = prime * result + (saleIsActive ? 1231 : 1237);
		result = prime * result + ((synopsis == null) ? 0 : synopsis.hashCode());
		result = prime * result + year;
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Book other = (Book) obj;
		if (author == null) {
			if (other.author != null)
				return false;
		} else if (!author.equals(other.author))
			return false;
		if (bookId != other.bookId)
			return false;
		if (bookImage == null) {
			if (other.bookImage != null)
				return false;
		} else if (!bookImage.equals(other.bookImage))
			return false;
		if (bookName == null) {
			if (other.bookName != null)
				return false;
		} else if (!bookName.equals(other.bookName))
			return false;
		if (Float.floatToIntBits(bookPrice) != Float.floatToIntBits(other.bookPrice))
			return false;
		if (featured != other.featured)
			return false;
		if (genre == null) {
			if (other.genre != null)
				return false;
		} else if (!genre.equals(other.genre))
			return false;
		if (isbn == null) {
			if (other.isbn != null)
				return false;
		} else if (!isbn.equals(other.isbn))
			return false;
		if (publisher == null) {
			if (other.publisher != null)
				return false;
		} else if (!publisher.equals(other.publisher))
			return false;
		if (quantityOnHand != other.quantityOnHand)
			return false;
		if (Float.floatToIntBits(saleDiscountRate) != Float.floatToIntBits(other.saleDiscountRate))
			return false;
		if (saleIsActive != other.saleIsActive)
			return false;
		if (synopsis == null) {
			if (other.synopsis != null)
				return false;
		} else if (!synopsis.equals(other.synopsis))
			return false;
		if (year != other.year)
			return false;
		return true;
	}
}


	