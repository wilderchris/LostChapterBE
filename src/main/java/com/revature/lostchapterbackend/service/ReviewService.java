package com.revature.lostchapterbackend.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.revature.lostchapterbackend.exceptions.BookNotFoundException;
import com.revature.lostchapterbackend.exceptions.InvalidParameterException;
import com.revature.lostchapterbackend.exceptions.ReviewNotFoundException;
import com.revature.lostchapterbackend.model.Book;
import com.revature.lostchapterbackend.model.Review;

@Service
public interface ReviewService {
	
	public List<Review> getAllReviews();
	public Review getReviewById(String reviewId) throws ReviewNotFoundException;
	public Review addReview(Review newReview) throws InvalidParameterException ;
	public Review updateReview(Review reviewToUpdate, String id)
			throws ReviewNotFoundException, InvalidParameterException ;
	public List<Review> getReviewsByBook(Book book) throws BookNotFoundException ;


}
