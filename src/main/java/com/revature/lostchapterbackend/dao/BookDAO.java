package com.revature.lostchapterbackend.dao;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.revature.lostchapterbackend.model.Book;
@Repository
public interface BookDAO extends JpaRepository<Book, Integer> {
	//This DAO is used to hold book objects which can be found by their ids
	//Methods include
		//findByGenre: finds books by their genre name
		//findByISBN: finds a book its unique ISBN
	public List<Book> findByGenre(String genre);
	//public List<Book> findBysaleIsActiveTrue();
	public List<Book> findByIsbn(String isbn);
	
}


