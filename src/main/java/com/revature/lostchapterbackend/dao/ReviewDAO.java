package com.revature.lostchapterbackend.dao;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.revature.lostchapterbackend.model.Book;
import com.revature.lostchapterbackend.model.Review;
@Repository
public interface ReviewDAO extends JpaRepository<Review, Integer> {
	//This DAO is used to hold review objects which can be found by their ids
	//Methods include
		//findByBookOrderBySentAtDesc: 
	public List<Review> findByBookOrderBySentAtDesc(Book book);
	public List<Review> findReviewByBook(int bookId);
}
