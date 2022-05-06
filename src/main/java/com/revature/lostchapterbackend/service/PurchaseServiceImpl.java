package com.revature.lostchapterbackend.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.revature.lostchapterbackend.dao.PurchaseDAO;


import com.revature.lostchapterbackend.model.Purchase;
@Service
public class PurchaseServiceImpl implements PurchaseService{
	//This service is used to handle all aspects of Purchases and has the below methods
		//upDatePurchase: This method updates a purchase information
		//getPurchaseByuserId: This method gets a purchase using the usersid
		//deletePurchase: This method deletes a purchase by a purchase object
		//getPurchaseById: This method gets a purchase by an id
		//addBooksToPurchase: This method adds books to the purchase
		//checkBookInThePurchase: This method allows the user to check to see if a book is already in the purchase
		//deleteBookInPurchase: This method allows the user to delete a book by its book object in the purchase
		//deleteAllBooksInPurchase: This method allows the user to delete all of the books in the users purchase
		//incrementQuantity: This method allows the user to increase the quantity of books they are buying by 1 via a user object
		//incrementQuantityNoUser: This method allows the user to increase the quantity of books they are buying by 1 via a purchase object
		//decreaseQuantity: This method allows the user to decrease the quantity of books they are buying by 1 via a user object
		//decreaseQuantityNoUser: This method allows the user to decrease the quantity of books they are buying by 1 via a purchase object
		//createPurchase: This method creates a new purchase in the database



	private PurchaseDAO PurchaseDao;
	@Autowired
	public  PurchaseServiceImpl(PurchaseDAO PurchaseDao) {
		this.PurchaseDao=PurchaseDao;
	}
	
	@Override
	@Transactional
	public Purchase upDatePurchase(Purchase newPurchase) {
		if (PurchaseDao.existsById(newPurchase.getPurchaseId())) {
			PurchaseDao.save(newPurchase);
			newPurchase = PurchaseDao.findById(newPurchase.getPurchaseId()).get();
			return newPurchase;
		}
		return null;
	}

	@Override
	@Transactional
	public List<Purchase> getPurchaseByuserId(int Id) {
		
		return PurchaseDao.findByOrder_User(Id);
	}

	@Override
	@Transactional
	public void deletePurchase(Purchase PurchaseToDelete) {
		PurchaseDao.delete(PurchaseToDelete);
		}
	
	@Override
	@Transactional
	public Purchase getPurchaseById(int id) {
		Optional<Purchase> Purchase = PurchaseDao.findById(id);
		if (Purchase.isPresent())
			return Purchase.get();
		else return null;
	}
//
//	@Override
//	@Transactional
//	public void addBooksToPurchase(Book newbook, int userId) {
//		Purchase currentPurchase= PurchaseDao.findByuser(userId);
//		BookToBuy newBookToBuy = new BookToBuy();
//		newBookToBuy.setBook(newbook);
//		List<BookToBuy> booksInPurchase= currentPurchase.getBooks();
//		booksInPurchase.add(newBookToBuy);
//		currentPurchase.setBooks(booksInPurchase);
//		PurchaseDao.save(currentPurchase);
//	}
//
//	@Override
//	@Transactional
//	public boolean checkBookInThePurchase(Book book, int userId) {
//		Purchase currentPurchase= PurchaseDao.findByuser(userId);
//		List<BookToBuy> booksInPurchase= currentPurchase.getBooks();
//		Boolean bookExists=false;
//		for(int i=0;i<booksInPurchase.size();i++) {
//			if(booksInPurchase.get(i).getBook().getBookId()==book.getBookId()) {
//				 bookExists=true;
//				}
//		}
//		return bookExists;
//	}
//
//	@Override
//	@Transactional
//	public Purchase deleteBookInPurchase(Book book, int userId){
//		Purchase currentPurchase= PurchaseDao.findByuser(userId);
//		List<BookToBuy> booksInPurchase= currentPurchase.getBooks();
//		BookToBuy rmbookToBuy= new BookToBuy(); 
//		for(int i=0;i<booksInPurchase.size();i++) {
//			if(booksInPurchase.get(i).getBook().getBookId()==book.getBookId()) {
//				rmbookToBuy=booksInPurchase.get(i);
//			}
//		}
//		
//		booksInPurchase.remove(rmbookToBuy);
//		currentPurchase.setBooks(booksInPurchase);
//		PurchaseDao.save(currentPurchase);
//		return currentPurchase;
//	}
//
//	@Override
//	@Transactional
//	public Purchase deleteAllBooksInPurchase(int userId) {
//		Purchase currentPurchase= PurchaseDao.findByuser(userId);
//		List<BookToBuy> booksInPurchase= currentPurchase.getBooks();
//		booksInPurchase.clear();
//		currentPurchase.setBooks(booksInPurchase);
//		PurchaseDao.save(currentPurchase);
//		return currentPurchase;
//	}
//
//	@Override
//	@Transactional
//	public void addBooksToPurchaseNoUser(Book newbook, Purchase currentPurchase) {
//		BookToBuy newBookToBuy = new BookToBuy();
//		newBookToBuy.setBook(newbook);
//		List<BookToBuy> booksInPurchase= currentPurchase.getBooks();
//		booksInPurchase.add(newBookToBuy);
//		currentPurchase.setBooks(booksInPurchase);
//		PurchaseDao.save(currentPurchase);
//	}
//
//	@Override
//	@Transactional
//	public boolean checkBookInThePurchaseNoUser(Book book, Purchase currentPurchase) {
//	
//		List<BookToBuy> booksInPurchase= currentPurchase.getBooks();
//		for(BookToBuy b:booksInPurchase) {
//			if(book.equals(b))
//				return true;
//		}
//		return false;
//	}
//
//	@Override
//	@Transactional
//	public Purchase deleteBookInPurchaseNoUser(Book book, Purchase currentPurchase)  {
//		
//		
//		List<BookToBuy> booksInPurchase= currentPurchase.getBooks();
//		BookToBuy rmbookToBuy= new BookToBuy(); 
//		for(int i=0;i<booksInPurchase.size();i++) {
//			if(booksInPurchase.get(i).getBook().getBookId()==book.getBookId()) {
//				rmbookToBuy=booksInPurchase.get(i);
//			}
//		}
//		
//		booksInPurchase.remove(rmbookToBuy);
//		currentPurchase.setBooks(booksInPurchase);
//		PurchaseDao.save(currentPurchase);
//		return currentPurchase;
//	}
//
//	@Override
//	@Transactional
//	public Purchase deleteAllBooksInPurchaseNoUser(Purchase currentPurchase) {
//		List<BookToBuy> booksInPurchase= currentPurchase.getBooks();
//		booksInPurchase.clear();
//		currentPurchase.setBooks(booksInPurchase);
//		PurchaseDao.save(currentPurchase);
//		return currentPurchase;
//	}
//
//	@Override
//	@Transactional
//	public void incrementQuantity(Book book, int userId) {
//		Purchase currentPurchase= PurchaseDao.findByuser(userId);
//		List<BookToBuy> booksInPurchase= currentPurchase.getBooks();
//		for(int i=0;i<booksInPurchase.size();i++) {
//			if(booksInPurchase.get(i).getBook().getBookId()==book.getBookId()) {
//				booksInPurchase.get(i).setQuantityToBuy(booksInPurchase.get(i).getQuantityToBuy()+1);
//			}
//		}
//		currentPurchase.setBooks(booksInPurchase);
//		PurchaseDao.save(currentPurchase);
//	}
//
//	@Override
//	@Transactional
//	public void incrementQuantityNoUser(Book book, Purchase currentPurchase) {
//		List<BookToBuy> booksInPurchase= currentPurchase.getBooks();
//		for(int i=0;i<booksInPurchase.size();i++) {
//			if(booksInPurchase.get(i).getBook().getBookId()==book.getBookId()) {
//				booksInPurchase.get(i).setQuantityToBuy(booksInPurchase.get(i).getQuantityToBuy()+1);
//			}
//		}
//		currentPurchase.setBooks(booksInPurchase);
//		PurchaseDao.save(currentPurchase);
//		
//	}
//
//	@Override
//	@Transactional
//	public void decreaseQuantity(Book book, int userId) {
//		Purchase currentPurchase= PurchaseDao.findByuser(userId);
//		List<BookToBuy> booksInPurchase= currentPurchase.getBooks();
//		for(int i=0;i<booksInPurchase.size();i++) {
//			if(booksInPurchase.get(i).getBook().getBookId()==book.getBookId()) {
//				if(booksInPurchase.get(i).getQuantityToBuy()>1)
//				{
//				booksInPurchase.get(i).setQuantityToBuy(booksInPurchase.get(i).getQuantityToBuy()-1);
//				currentPurchase.setBooks(booksInPurchase);
//				PurchaseDao.save(currentPurchase);
//				}
//				else
//				{
//					booksInPurchase.remove(booksInPurchase.get(i));
//					currentPurchase.setBooks(booksInPurchase);
//					PurchaseDao.save(currentPurchase);
//				}
//			}
//		}
//		
//	}
//
	@Override
	@Transactional
	public int createPurchase(Purchase newPurchase) {
		
		Purchase purchase = PurchaseDao.save(newPurchase);
		if(purchase != null)
		return purchase.getPurchaseId();
		else return 0;
	}
//
//	@Override
//	@Transactional
//	public void decreaseQuantityNoUser(Book book, Purchase currentPurchase) {
//		List<BookToBuy> booksInPurchase= currentPurchase.getBooks();
//		for(int i=0;i<booksInPurchase.size();i++) {
//			if(booksInPurchase.get(i).getBook().getBookId()==book.getBookId()) {
//				if(booksInPurchase.get(i).getQuantityToBuy()>1)
//				{
//				booksInPurchase.get(i).setQuantityToBuy(booksInPurchase.get(i).getQuantityToBuy()-1);
//				currentPurchase.setBooks(booksInPurchase);
//				PurchaseDao.save(currentPurchase);
//				}
//				else
//				{
//					booksInPurchase.remove(booksInPurchase.get(i));
//					currentPurchase.setBooks(booksInPurchase);
//					PurchaseDao.save(currentPurchase);
//				}
//			}
//		}
//		
//	}




	
	
}
