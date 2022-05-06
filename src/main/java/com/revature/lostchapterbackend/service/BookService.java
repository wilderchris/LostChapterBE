package com.revature.lostchapterbackend.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.revature.lostchapterbackend.model.Book;



/* To do
 * Implement featured books 
 * Implement Sales feature
 * 
 */

@Service
public interface BookService {
	/*Gets all books 
	 */
	public List<Book> getAllBooks();
	/* Get book by id
	 */
	public Book getBookById(int bookId);
	/* add book
	 */
	public int addBook(Book newBook);
	/* edit book
	 */
	public Book updateBook(Book bookToUpdate);
	/* search by genre
	 */
	public List<Book> getBookByGenre(String genre);
	/* search by ISBN
	 */
	public List<Book> getBookByIsbn(String isbn);
	/* search by contains string keyword
	 */
	public List<Book> getByKeyWord(String key);
	public List<Book> getFeaturedBooks();
	public List<Book> getBooksBySale();
}
