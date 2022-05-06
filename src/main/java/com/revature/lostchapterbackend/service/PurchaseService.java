package com.revature.lostchapterbackend.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.revature.lostchapterbackend.model.Book;
import com.revature.lostchapterbackend.model.Purchase;
/*Front End Implementations notes
 * If the user is not logged in they will not be able to use the Purchase feature.
 * However, to get around this we can add a new Purchase to the table without a user. 
 * the Purchase id must be stored in the front end and compared as a global variable whenever doing login checks
 * The Purchase id essentially becomes the identifier of the non-logged in person
 * when the person logs in the Purchase should compared to the current Purchase then combined 
 * Last delete the Purchase that was used when user was not logged in
 * Same goes for registering
 * But instead of deleting the Purchase if they decide to register we set the current Purchases' user to the newly created user 
 * We should set a timer and if the timer runs out when the user is not logged in then the Purchase gets deleted
 * */


/* To do :
 *  Verify there is inventory of product when adding to Purchase.
 *  When checking out verifying once again there is enough inventory as some may have checkedout before
 * */

@Service
public interface PurchaseService {
	
public int createPurchase(Purchase newPurchase);	
	
/* Returns Purchase by id using spring boot repo*/
public Purchase getPurchaseById(int Id);
/*Get purchase by userid*/
public List<Purchase> getPurchaseByuserId(int Id);
/*deletes Purchase*/
public void deletePurchase(Purchase PurchaseToDelete);

/*Using the login token we find the user's Purchase
 *Then we add the book to the list of books in Purchase
 *Spring boot adds a new entry to the booktobuy table
 */
/*for updating quantity*/
public Purchase upDatePurchase(Purchase newPurchase);
//
///*Using the login token we find the user's Purchase
// * Then we check the Purchases list for the book
// * if book is found we return true else false
// * */
//public boolean checkBookInThePurchase(Book book, int userId);
//
///*Using the login token we find the user's Purchase
// *Then we delete the book from the list 
// *Spring cascades to delete the booktobuy entry
// */
//public Purchase deleteBookInPurchase(Book book, int userId) ;
//
///*Using the login token we find the user's Purchase
// *Then we delete the book from the list 
// *Spring cascades to delete the booktobuy entry
// */
//public Purchase deleteAllBooksInPurchase(int userId);
//
///*If no user is logged in methods 
// * */
//public void addBooksToPurchaseNoUser(Book newbook, Purchase currentPurchase);

/*we check the Purchases list for the book
 * if book is found we return true else false
 * */
//public boolean checkBookInThePurchaseNoUser(Book book, Purchase currentPurchase);

/*we delete the book from the list 
 *Spring cascades to delete the booktobuy entry
 */
//public Purchase deleteBookInPurchaseNoUser(Book book, Purchase currentPurchase) ;
//
///* we delete the book from the list 
// *Spring cascades to delete the booktobuy entry
// */
//public Purchase deleteAllBooksInPurchaseNoUser(Purchase currentPurchase);
///*
// * increments book quantity 
// * */
//public void incrementQuantity(Book book, int userId);
//
///*
// *  increments book quantity 
// * */
//public void incrementQuantityNoUser(Book book, Purchase currentPurchase);
//
///*
// * decreases quantity
// * */
//public void decreaseQuantity(Book book, int userId);
//
///*
// * decreases quantity
// * */
//public void decreaseQuantityNoUser(Book book, Purchase currentPurchase);


}
