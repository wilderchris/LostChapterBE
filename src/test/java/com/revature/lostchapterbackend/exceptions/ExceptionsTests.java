//package com.revature.lostchapterbackend.exceptions;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//import static org.mockito.Mockito.when;
//
//import java.util.Optional;
//
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.mock.mockito.MockBean;
//
//import com.revature.lostchapterbackend.dao.BookDAO;
//import com.revature.lostchapterbackend.model.Book;
//import com.revature.lostchapterbackend.service.BookService;
//import com.revature.lostchapterbackend.service.ReviewService;
//
//public class ExceptionsTests {
//
//	@Autowired
//	private BookService bookServ;
//	
//	@Autowired
//	private ReviewService revServ;
//	
//	@Autowired
//	@MockBean
//	private BookDAO bookDao;
//
//	@Test
//	void BookNotFoundException() {
////		BookNotFoundException bookNotFoundException = 
//		BookNotFoundException mockException = new BookNotFoundException();
//		when(bookDao.findById(1)).thenThrow(mockException);
//
//		assertThrows(BookNotFoundException.class, 
//		() -> {
//	//		when(bookDao.findById(1)).thenReturn(null);
//			Book book = new Book();
//			book.setBookId(1);
//			revServ.getReviewsByBook(book);
//		});
////		assertEquals("Book not found in Bookstore!", bookNotFoundException.getMessage());
//	}
//}
