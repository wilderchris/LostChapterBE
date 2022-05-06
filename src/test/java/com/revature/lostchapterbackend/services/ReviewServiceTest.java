package com.revature.lostchapterbackend.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.revature.lostchapterbackend.LostChapterBackendApplication;
import com.revature.lostchapterbackend.dao.ReviewDAO;
import com.revature.lostchapterbackend.exceptions.BookNotFoundException;
import com.revature.lostchapterbackend.exceptions.InvalidParameterException;
import com.revature.lostchapterbackend.exceptions.ReviewNotFoundException;
import com.revature.lostchapterbackend.model.Book;
import com.revature.lostchapterbackend.model.Order;
import com.revature.lostchapterbackend.model.Review;
import com.revature.lostchapterbackend.model.User;
import com.revature.lostchapterbackend.service.ReviewService;

@SpringBootTest(classes=LostChapterBackendApplication.class)
public class ReviewServiceTest {
	@MockBean
	private ReviewDAO reviewDao;
	@Autowired
	private ReviewService reviewServ;
	
	private static List<Review> mockReviews;
	
	@BeforeAll
	public static void mockReviewSetUp() {
			mockReviews = new ArrayList<>();
			Book book1 = new Book();
			book1.setBookId(1);
			Book book2 = new Book();
			book2.setBookId(2);
		
		for (int i=1; i<=5; i++) {
			Review review = new Review();
			review.setReviewId(i);
			if (i<3)
				review.setBook(book1);
			if  (i>3)
				review.setBook(book2);
			mockReviews.add(review);
		}
	}
	
	
	@Test 
	public void getAllReviews() {
		when(reviewDao.findAll()).thenReturn(mockReviews);
		
		List<Review> actualReviews = reviewServ.getAllReviews();
		
		assertEquals(mockReviews, actualReviews);
	}
	
	@Test 
	public void getReviewById() throws ReviewNotFoundException {
		Review review = new Review();
		review.setReviewId(1);
		
		when(reviewDao.findById(1)).thenReturn(Optional.of(review));
		
		Review actualReview = reviewServ.getReviewById("1");
		assertEquals(review, actualReview);
	}
	
	@Test 
	public void getReviewByIdDoesNotExist() throws ReviewNotFoundException {
		when(reviewDao.findById(1)).thenReturn(Optional.empty());
		
		Review actualReview = reviewServ.getReviewById("1");
		assertNull(actualReview);
	}
	
	@Test 
	public void getReviewsByBookExists() throws BookNotFoundException  {
		Book book = new Book();
		book.setBookId(1);
		
		when(reviewDao.findReviewByBook(1)).thenReturn(mockReviews);
		
		List<Review> actualReviews = reviewServ.getReviewsByBook(book);
		boolean onlyBook = true;
		for (Review review : actualReviews) {
			if (!review.getBook().equals(book))
				onlyBook = false;
		}
		assertTrue(onlyBook);
	}
	
	@Test 
	public void getReviewsByBookDoesNotExist() throws BookNotFoundException  {
		
		when(reviewDao.findReviewByBook(2)).thenReturn(mockReviews);
		
		List<Review> actualReviews = reviewServ.getReviewsByBook(new Book());
		assertTrue(actualReviews.isEmpty());
	}
	
	@Test 
	public void addReviewSuccessfully() throws InvalidParameterException {
		Review newReview = new Review();
		Review mockReview = new Review();
		mockReview.setReviewId(69);
		
		when(reviewDao.save(newReview)).thenReturn(mockReview);
		
		Review addedReview = reviewServ.addReview(newReview);
		
		assertNotEquals(0, addedReview);
	}
	
	@Test 
	public void addReviewUnsuccessfully() throws InvalidParameterException {
		Review review = new Review();
		
		when(reviewDao.save(review)).thenReturn(review);
		
		Review newReview = reviewServ.addReview(review);
		
		assertEquals(review,newReview);
	}
	
	@Test 
	public void updateReviewExists() throws ReviewNotFoundException, InvalidParameterException {
		Review editedReview = new Review();
		editedReview.setReviewId(1);
		editedReview.setReviewText("My Last Book");
		
		when(reviewDao.findById(1)).thenReturn(Optional.of(editedReview));
		when(reviewDao.save(Mockito.any(Review.class))).thenReturn(editedReview);
		
		Review actualReview = reviewServ.updateReview(editedReview, "1");
		
		assertEquals(editedReview, actualReview);
	}
	
	@Test 
	public void updateReviewDoesNotExist() throws ReviewNotFoundException, InvalidParameterException {
		when(reviewDao.findById(2)).thenReturn(Optional.empty());
		
		Review editedReview = new Review();
		editedReview.setReviewId(2);
		editedReview.setReviewText("My First Book");
		
		Review actualReview = reviewServ.updateReview(editedReview, "2");
		
		assertNull(actualReview);
		verify(reviewDao, times(0)).save(Mockito.any(Review.class));
	}
	
//	@Test 
//	public void addReviewSuccessfully() throws InvalidParameterException {
//		Review newReview = new Review();
//		Review mockReview = new Review();
//		mockReview.setReviewId(69);
//		
//		when(reviewDao.save(newReview)).thenReturn(mockReview);
//		
//		int newId = reviewServ.addReview(newReview);
//		
//		assertNotEquals(0, newId);
//	}
//	
//	@Test 
//	public void addReviewUnsuccessfully() throws InvalidParameterException {
//		Review review = new Review();
//		
//		when(reviewDao.save(review)).thenReturn(review);
//		
//		int newId = reviewServ.addReview(review);
//		
//		assertEquals(0,newId);
//	}
//	
//	@Test 
//	public void updateReviewExists() throws ReviewNotFoundException, InvalidParameterException {
//		Review editedReview = new Review();
//		editedReview.setReviewId(1);
//		editedReview.setReviewText("My Last Book");
//		
//		when(reviewDao.findById(2)).thenReturn(Optional.of(editedReview));
//		when(reviewDao.save(Mockito.any(Review.class))).thenReturn(editedReview);
//		
//		Review actualReview = reviewServ.updateReview(editedReview);
//		
//		assertEquals(editedReview, actualReview);
//	}
//	
//	@Test 
//	public void updateReviewDoesNotExist() throws ReviewNotFoundException, InvalidParameterException {
//		when(reviewDao.findById(2)).thenReturn(Optional.empty());
//		
//		Review editedReview = new Review();
//		editedReview.setReviewId(2);
//		editedReview.setReviewText("My First Book");
//		
//		Review actualReview = reviewServ.updateReview(editedReview);
//		
//		assertNull(actualReview);
//		verify(reviewDao, times(0)).save(Mockito.any(Review.class));
//	}
//	

}
