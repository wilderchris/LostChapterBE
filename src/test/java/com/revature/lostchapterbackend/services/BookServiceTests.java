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
import com.revature.lostchapterbackend.dao.BookDAO;
import com.revature.lostchapterbackend.model.Book;
import com.revature.lostchapterbackend.model.ShippingInformation;
import com.revature.lostchapterbackend.service.BookService;

@SpringBootTest(classes=LostChapterBackendApplication.class)
public class BookServiceTests {
	@MockBean
	private BookDAO bookDao;
	@Autowired
	private BookService bookServ;
	
	private static List<Book> mockBooks;
	
	private static List<Book> mockNonFeaturedBooks;
	
	@BeforeAll
	public static void mockBookSetUp() {
			mockBooks = new ArrayList<>();
		
		for (int i=1; i<=5; i++) {
			Book book = new Book();
			book.setBookId(i);
			if (i<3)
				book.setGenre("Horror");
				book.setBookName("To Kill a Mocking Bird");
				book.setIsbn("1");
				book.setFeatured(true);
			if  (i>3)
				book.setGenre("Action");
				book.setBookName("Fahrenheit 451");
				book.setIsbn("2");
			mockBooks.add(book);
		}
	}
	
	@BeforeAll
	public static void mockNonFeaturedBookSetUp() {
			mockNonFeaturedBooks = new ArrayList<>();
		
		for (int i=1; i<=5; i++) {
			Book book = new Book();
			book.setBookId(i);
			if (i<3)
				book.setGenre("Horror");
				book.setBookName("To Kill a Mocking Bird");
				book.setIsbn("1");
				book.setFeatured(false);
			mockBooks.add(book);
		}
	}
	
	@Test
	public void getAllBooks() {
		when(bookDao.findAll()).thenReturn(mockBooks);
		
		List<Book> actualBooks = bookServ.getAllBooks();
		
		assertEquals(mockBooks, actualBooks);
	}
	
	@Test
	public void getBookByIdExists() {
		Book book = new Book();
		book.setBookId(1);
		
		when(bookDao.findById(1)).thenReturn(Optional.of(book));
		
		Book actualBook = bookServ.getBookById(1);
		assertEquals(book, actualBook);
	}
	
	@Test
	public void getBookByIdDoesNotExist() {
		when(bookDao.findById(1)).thenReturn(Optional.empty());
		
		Book actualBook = bookServ.getBookById(1);
		assertNull(actualBook);
	}
	
	@Test
	public void getBooksByGenreExists() {
		String genre = "Horror";
		
		when(bookDao.findByGenre("Horror")).thenReturn(mockBooks);
		
		List<Book> actualBooks = bookServ.getBookByGenre(genre);
		boolean onlyHorror = true;
		for (Book book : actualBooks) {
			if (!book.getGenre().equals("Horror"))
				onlyHorror = false;
		}
		assertTrue(onlyHorror);
	}

	
	@Test
	public void getBooksByGenreDoesNotExist() {
		String genre = "Mystery";
		
		when(bookDao.findByGenre("Mystery")).thenReturn(mockBooks);

		List<Book> actualBooks = bookServ.getBookByGenre(genre);
		assertTrue(actualBooks.isEmpty());
	}
	
	@Test
	public void getBooksByIsbnExists() {
		String Isbn = "1";
		
		when(bookDao.findByIsbn(Isbn)).thenReturn(mockBooks);
		System.out.println(mockBooks);
		List<Book> actualBooks = bookServ.getBookByIsbn(Isbn);
		System.out.println("SYSOUT HERE");
		System.out.println(actualBooks);
		boolean onlyIsbn = true;
		for (Book book : actualBooks) {
			if (!book.getIsbn().equals("1"))
				onlyIsbn = true;
		}
		assertTrue(onlyIsbn);
	}

	
	@Test
	public void getBooksByIsbnDoesNotExist() {
		String Isbn = "0";
		List<Book> mockBook = new ArrayList<>();
		
		when(bookDao.findByIsbn(Isbn)).thenReturn(mockBook);
		
		List<Book> actualBooks = bookServ.getBookByIsbn(Isbn);
		
		assertTrue(actualBooks.isEmpty());
	}
	
	@Test
	public void getBooksByKeyWordExists() {
		String keyWord = "Kill";
		
		when(bookDao.findAll()).thenReturn(mockBooks);
		
		List<Book> actualBooks = bookServ.getByKeyWord(keyWord);
		boolean onlyKeyWord = true;
		for (Book book : actualBooks) {
			if (!book.getBookName().toLowerCase().contains(keyWord.toLowerCase()))
				onlyKeyWord = false;
		}
		assertTrue(onlyKeyWord);
	}
	
	@Test
	public void getBooksByKeyWordDoesNotExist() {
		String keyWord = "bubble gum";
		
		when(bookDao.findAll()).thenReturn(mockBooks);
		
		List<Book> actualBooks = bookServ.getByKeyWord(keyWord);
		assertTrue(actualBooks.isEmpty());
	}
	
	

	@Test
	public void getBooksByFeaturedExists() {
		when(bookDao.findAll()).thenReturn(mockBooks);
		
		List<Book> actualBooks = bookServ.getFeaturedBooks();
		boolean onlyFeatured = true;
		for (Book book : actualBooks) {
			if (!book.isFeatured())
				onlyFeatured = false;
		}
		assertTrue(onlyFeatured);
	}
	
	@Test
	public void getBooksByFeaturedDoesNotExist() {
		when(bookDao.findAll()).thenReturn(mockNonFeaturedBooks);
		
		List<Book> actualBooks = bookServ.getFeaturedBooks();
		assertTrue(actualBooks.isEmpty());
	}
	
	@Test
	public void addBookSuccessfully() {
		Book newBook = new Book();
		Book mockBook = new Book();
		mockBook.setBookId(69);
		
		when(bookDao.save(newBook)).thenReturn(mockBook);
		
		int newId = bookServ.addBook(newBook);
		
		assertNotEquals(0, newId);
	}
	
	@Test
	public void addBookUnSuccessfully() {
		Book book = new Book();
		
		when(bookDao.save(book)).thenReturn(book);
		
		int newId = bookServ.addBook(book);
		
		assertEquals(0,newId);
	}
	
	@Test
	public void updateBookSuccessfully() {
		Book editedBook = new Book();
		editedBook.setBookId(1);
		editedBook.setBookName("My Last Book");
		
		when(bookDao.findById(1)).thenReturn(Optional.of(editedBook));
		when(bookDao.save(Mockito.any(Book.class))).thenReturn(editedBook);
		
		Book actualBook = bookServ.updateBook(editedBook);
		
		assertEquals(editedBook, actualBook);

	}
	
	@Test
	public void updateBookUnsuccessfully() {
		when(bookDao.findById(2)).thenReturn(Optional.empty());
		
		Book editedBook = new Book();
		editedBook.setBookId(2);
		editedBook.setBookName("My First Book");
		
		Book actualBook = bookServ.updateBook(editedBook);
		
		assertNull(actualBook);
		verify(bookDao, times(0)).save(Mockito.any(Book.class));
	}
	
}