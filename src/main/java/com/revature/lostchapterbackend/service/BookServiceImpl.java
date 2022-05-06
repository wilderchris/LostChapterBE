package com.revature.lostchapterbackend.service;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.lostchapterbackend.dao.BookDAO;
import com.revature.lostchapterbackend.model.Book;



@Service
public class BookServiceImpl implements BookService {
	//This service is used to handle all aspects of books and has the below methods
		//getFeaturedBooks:This method is responsible for getting all of the current featured books
		//getBooksBySale: This method is responsible for getting all books that are currently on sale
		//getAllBooks: This method is responsible for getting all of the current book on the database
		//getBookById: This method is responsible  for getting a book by its id
		//addBook: This method allow the admin to add a new book to our database
		//updateBook: This method is responsible for the updating of a books information
		//getBookByGenre: This method is responsible for getting books by their genre
		//getByISBN: This method is responsible  for getting a book by its isbn
		//getByKeyWord: This method is responsible for getting all books with the inserted keyword
	private Logger logger = LoggerFactory.getLogger(BookService.class);
	private BookDAO bookDao;
	

	@Autowired
	public BookServiceImpl(BookDAO bookDao) {
		this.bookDao = bookDao;
	}

	

//	@Override
//	public List<Genre> getAllGenre() {
//		
//		return genreDao.findAll();
//	}



	@Override
	public List<Book> getFeaturedBooks() {
		List<Book> all=bookDao.findAll();
		List<Book> featured= new ArrayList<Book>();
		for(Book book:all) {
			if(book.isFeatured()) {
				
				featured.add(book);
				
			}
		}
		return featured;
	}

	@Override
	public List<Book> getBooksBySale() {
		List<Book> all=bookDao.findAll();
		List<Book> sale= new ArrayList<Book>();
		for(Book book:all) {
			if(book.isSaleIsActive()) {
				sale.add(book);		
			} 
		}
		return sale;
	}



	@Override
	@Transactional
	public List<Book> getAllBooks() {
		logger.debug("BookService.getAllBooks() invoked.");
		List<Book> books = bookDao.findAll();
		return books;
	}



	@Override
	@Transactional
	public Book getBookById(int bookId) {
		logger.debug("BookService.getBookById() invoked.");
		Optional<Book> book = bookDao.findById(bookId);
		if (book.isPresent())
			return book.get();
		else return null;
	}



	@Override
	@Transactional
	public int addBook(Book newBook) {
		logger.debug("BookService.addBook() invoked.");
		Book book = bookDao.save(newBook);
		if(book != null)
		return book.getBookId();
		else return 0;
	}



	@Override
	@Transactional
	public Book updateBook(Book bookToUpdate) {
		logger.debug("BookService.updateBook() invoked.");
		Optional<Book> BookFromDatabase = bookDao.findById(bookToUpdate.getBookId());
		if (BookFromDatabase.isPresent()) {
			bookDao.save(bookToUpdate);
			return bookDao.findById(bookToUpdate.getBookId()).get();
		}
		return null;
	}


		
		@Override
		@Transactional
		public List<Book> getBookByGenre(String genre) {
			logger.debug("BookService.getBookByGenre() invoked.");
			
			return bookDao.findByGenre(genre.toLowerCase());
		}
		

		@Override
		@Transactional
		public List<Book> getBookByIsbn(String isbn){
			logger.debug("BookService.getByIsbn() invoked.");
			return bookDao.findByIsbn(isbn);
		}



		@Override
		@Transactional
		public List<Book> getByKeyWord(String key) {
			logger.debug("BookService.getByKeyWord() invoked.");
			List<Book> books = bookDao.findAll();
			List<Book> found= new ArrayList<Book>(); 
			if(books!=null)
			for(Book book:books) {
				if(book.getBookName().toLowerCase().contains(key.toLowerCase())) {
					found.add(book);
				}
			}
			else return null;
			return found;
		}	

			
//	public List<Book> getFeaturedBooks() {
//		logger.info("BookService.getFeaturedBooks() invoked.");
//
//		Page<Book> bookPage = bd.findAll(PageRequest.of(0, 15, Sort.by("quantity").descending()));
//		List<Book> b = null;
//		if (bookPage.hasContent()) {
//			System.out.println(bookPage.getContent());
//			b = bookPage.getContent();
//		}
//
//		return b;
//	}
//



}
