package com.revature.lostchapterbackend.service;

import java.security.InvalidParameterException;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.lostchapterbackend.dao.BookDAO;
import com.revature.lostchapterbackend.dao.ReviewDAO;
import com.revature.lostchapterbackend.exceptions.BookNotFoundException;
import com.revature.lostchapterbackend.exceptions.ReviewNotFoundException;
import com.revature.lostchapterbackend.model.Book;
import com.revature.lostchapterbackend.model.Review;

@Service
public class ReviewServiceImpl implements ReviewService {
	//This service is used to handle all aspects of Reviews and has the below methods
		//getAllReviews: gets all reviews in the database
		//getReviewById: get a review by its specific id
		//addReview: creates and adds a review to the database
		//updateReview: allows the user to update and change their review
		//getReviewsByBook: gets all reviews by passing in a book object
	
	private Logger logger = LoggerFactory.getLogger(ReviewService.class);

	private ReviewDAO revDao;
	private BookDAO bookDao;
	
	@Autowired
	public ReviewServiceImpl(ReviewDAO revDao, BookDAO bookDao) {
		// For mocking
		// For Unit Testing
		this.revDao = revDao;
		this.bookDao = bookDao;
	}

	@Override
	@Transactional
	public List<Review> getAllReviews() {
		logger.info("ReviewService.getAllReviews() invoked.");

		return revDao.findAll();
	}

	@Override
	@Transactional
	public Review getReviewById(String id) throws ReviewNotFoundException {
		logger.info("ReviewService.getReviewById() invoked.");

		try {
			int reviewId = Integer.parseInt(id);
			if (!revDao.findById(reviewId).isPresent()) {
				throw new ReviewNotFoundException();
			}
			return revDao.findById(reviewId).get();
		} catch (NumberFormatException e) {
			throw new InvalidParameterException("The Id entered must be an int.");

		}

	}

	@Override
	@Transactional
	public Review addReview(Review newReview) throws InvalidParameterException {
		logger.info("ReviewService.addReview() invoked.");

		// int newId = revDao.save(newReview).getReviewId();
		// newReview.setReviewId(newId);
		// return newReview;

		return revDao.saveAndFlush(newReview);

	}

	@Override
	@Transactional
	public Review updateReview(Review reviewToUpdate, String id)
			throws ReviewNotFoundException, InvalidParameterException {

		logger.info("ReviewService.updateReview() invoked.");

		try {
			int reviewId = Integer.parseInt(id);

			if (!revDao.findById(reviewId).isPresent()) {

				throw new ReviewNotFoundException();
			}

			logger.debug("revDao.findById(reviewId).get() {}", revDao.findById(reviewId).get());

			return revDao.saveAndFlush(reviewToUpdate);
		} catch (NumberFormatException e) {
			throw new InvalidParameterException("Id must be in Int format");
		}
	}

	@Override
	@Transactional
	public List<Review> getReviewsByBook(Book book) throws BookNotFoundException {
		if (!bookDao.findById(book.getBookId()).isPresent()) {
			throw new BookNotFoundException();
		}
		return revDao.findByBookOrderBySentAtDesc(book);
	}

}