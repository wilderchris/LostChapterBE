package com.revature.lostchapterbackend.controller;


import java.util.List;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.revature.lostchapterbackend.JWT.TokenProvider;
import com.revature.lostchapterbackend.model.Book;
import com.revature.lostchapterbackend.service.BookService;


@RestController
@RequestMapping(path="/book")
@CrossOrigin("*")
public class BookController {
	//This controller is used for the following
		//getAllBooks GET /books 
		//getFeaturedBooks GET /books/featured 
		//getBookById GET //books/{id}
		//getBookByGenre GET //books/genre/{genreId}
		//getBookByKeyword GET /books/search/{keyword}
		//getBookBySale GET books/sales 
		//addNewBook POST /books
		//updateBookById PUT /books/{id}
	private Logger logger = LoggerFactory.getLogger(BookController.class);

	//static for testing
	private static BookService bookServ;
	private static TokenProvider tokenProvider;
	
	public BookController() {
		super();
	}
	//field injection
	@Autowired
	public BookController(BookService bookServ, TokenProvider tokenProvider) {
		this.bookServ=bookServ;
		this.tokenProvider=tokenProvider;
	}
	
//	@GetMapping(path = "/genre")
//	public ResponseEntity<Object> getAllGenre() {
		//This method is responsible for getting books by their genre
		//If the genre does not exist then it will throw an error
//		logger.debug("BookController.getBookByGenreId() invoked.");
//
//		try {
//			List<Genre> genres = bookServ.getAllGenre();
//			if(genres!=null)
//			return ResponseEntity.ok(genres);
//			else
//				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
//		}
//		catch (NumberFormatException e) {
//			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
//		}
//
//	}
	//working
	@GetMapping
	public ResponseEntity<List<Book>>  getAllBooks() {
		//This method is responsible for getting all of the current books on the database
		
		logger.debug("BookController.getAllBooks() invoked.");
		List<Book> books = bookServ.getAllBooks();
		
		return ResponseEntity.ok(books);
	}

	@GetMapping(path = "/featured")
	public ResponseEntity<List<Book>> getFeaturedBooks() {

		//This method is responsible for getting all of the current featured books

		logger.info("BookController.getFeaturedBooks() invoked.");
		List<Book> featuredBooks = bookServ.getFeaturedBooks();

		return ResponseEntity.ok(featuredBooks);
	}
	
	//working
	@GetMapping(path = "/{bookId}")
	public ResponseEntity<Book> getBookById(@PathVariable int bookId) {
		//This method is responsible  for getting a book by its id
		//If the book does not exist it will throw an error

		logger.debug("BookController.getBookById() invoked.");

		Book book = bookServ.getBookById(bookId);
		
		if (book != null) {
			return ResponseEntity.ok(book);
		}
		else
			return ResponseEntity.notFound().build();

	}
	//working
	@GetMapping(path = "/isbn/{name}")

	public ResponseEntity<Object> getBookByIsbn(@PathVariable String isbn) {
  		//This method is responsible for getting books by their genre
		  //If the genre does not exist then it will throw an error
		logger.debug("BookController.getBookByGenreId() invoked.");

		try {
			List<Book> bookList = bookServ.getBookByIsbn(isbn);
			if(bookList!=null)
			return ResponseEntity.ok(bookList);
			else
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		catch (NumberFormatException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}

	}
	
	@GetMapping(path = "/genre/{name}")
	public ResponseEntity<Object> getBookByGenre(@PathVariable String name) {
  		//This method is responsible for getting books by their genre
		  //If the genre does not exist then it will throw an error
		
		logger.debug("BookController.getBookByGenreId() invoked.");

		try {
			List<Book> bookList = bookServ.getBookByGenre(name);
			if(bookList!=null)
			return ResponseEntity.ok(bookList);
			else
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		catch (NumberFormatException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}

	}
	//working
	@GetMapping(path = "/search/{key}")
	public ResponseEntity<Object> getBookByKeyword(@PathVariable String key) {
		//This method is responsible for getting all books with the inserted keyword
		logger.debug("BookController.getBookByKeyword() invoked.");
		try {
			List<Book> bookList = bookServ.getByKeyWord(key);
			if(bookList!=null)
			return ResponseEntity.ok(bookList);
			else
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		catch (NumberFormatException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}

	@GetMapping(path = "/books/sales")
	public ResponseEntity<List<Book>> getBookBySale() {
		//This method is responsible for getting all books that are currently on sale
		//logger.info("BookController.getBookBySale() invoked.");
		List<Book> bookList = bookServ.getBooksBySale();
		return ResponseEntity.ok(bookList);
	}

	//working
	@PostMapping
	public ResponseEntity<Void> addNewBook(@RequestBody Book newBook, @RequestHeader("Authorization") String authorization) {
		//This method allow the admin to add a new book to our database
		//This method will throw an error if any of the following occur
			//Currently no checking to see if book is valid
		String token = tokenProvider.extractToken(authorization);
		HttpHeaders jwtHeader = tokenProvider.getHeaderJWT(token);
		
		logger.debug("BookController.addNewBook() invoked.");

		if (newBook !=null) {
			bookServ.addBook(newBook);
//				return ResponseEntity.status(HttpStatus.CREATED).build();
			return new ResponseEntity<>(jwtHeader,HttpStatus.CREATED);
		}
//			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
			return new ResponseEntity<>(jwtHeader, HttpStatus.BAD_REQUEST);
	}

//	@Admin
//	@PutMapping(path = "/books/{id}")
//	public ResponseEntity<Object> updateBookById(@PathVariable(value = "id") String id,
//			@RequestBody AddOrUpdateBookDTO dto) throws InvalidParameterException, GenreNotFoundException,
//			ISBNAlreadyExists, SynopsisInputException, SaleDiscountRateException {
		//This method is responsible for the updating of a books information
				//This method will throw an error if any of the following occur
					//!!!!!DOES NOT CURRENTLY VALIDATE THAT THE UPDATED BOOK INFORMATION IS GOOD!!!!!
//		//logger.info("BookController.updateBookById() invoked.");
//
//		try {
//
//			Book updatedBook = bs.updateBook(null);
//
//			logger.info("updatedBook {}", updatedBook);
//
//			return ResponseEntity.status(200).body(updatedBook);
//
//		} catch (BookNotFoundException e) {
//			return ResponseEntity.status(400).body(e.getMessage());
//		} catch (InvalidParameterException e) {
//			return ResponseEntity.status(400).body(e.getMessage());
//		} catch (GenreNotFoundException e) {
//			return ResponseEntity.status(400).body(e.getMessage());
//		} catch (SaleDiscountRateException e) {
//			return ResponseEntity.status(400).body(e.getMessage());
//		} catch (ISBNAlreadyExists e) {
//			return ResponseEntity.status(400).body(e.getMessage());
//
//		}
//
//	}
}
