package com.revature.lostchapterbackend.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import com.revature.lostchapterbackend.JWT.TokenProvider;
import com.revature.lostchapterbackend.exceptions.UserNotFoundException;
import com.revature.lostchapterbackend.model.Book;
import com.revature.lostchapterbackend.model.Order;
import com.revature.lostchapterbackend.model.Purchase;
import com.revature.lostchapterbackend.model.User;
import com.revature.lostchapterbackend.service.PurchaseService;

@RestController
@RequestMapping(path="/Purchase")
@CrossOrigin("*")
public class PurchaseController {

	//This controller is used for the following
		//getPurchaseByUser GET user/{userId}
		//updatePurchase PUT/adjust
		//addBookToPurchase PUT /add/{bookToBuyId}/{PurchaseId}
		//deleteBookToPurchase POST /delete/{bookToBuyId}/{userId}
		//deleteBookToPurchaseNoUser POST /delete/{bookToBuyId}/{PurchaseId}
		//addQuantity POST /add/quantity/{bookToBuyId}/{userId}
		//addQuantityNoUser POST /add/quantity/{bookToBuyId}/{PurchaseId}
		//decreaseQuantity POST /decrease/quantity/{bookToBuyId}/{userId}
		//decreaseQuantityNoUser POST /decrease/quantity/{bookToBuyId}/{PurchaseId}
		//getPurchaseById GET /{PurchaseId}
		//deletePurchase DELETE /{PurchaseId}
	private static PurchaseService PurchaseServ;
	
	private static TokenProvider tokenProvider;
	public PurchaseController() {
		super();
		}
	//field injection
	@Autowired
	public PurchaseController(PurchaseService PurchaseServ,TokenProvider tokenProvider) {
		this.PurchaseServ= PurchaseServ;
		this.tokenProvider=tokenProvider;
	}
	/*get all purchases by user*/
	@GetMapping(path = "user/{userId}") 
	public ResponseEntity<Object> getPurchaseByUser(@PathVariable int userId,@RequestHeader("Authorization") String authorization){
		//gets the purchase by its PurchaseId
		String token = tokenProvider.extractToken(authorization);
		HttpHeaders jwtHeader = tokenProvider.getHeaderJWT(token);
		List<Purchase> purchase = PurchaseServ.getPurchaseByuserId(userId);	
		if (purchase != null)
			return new ResponseEntity<>(purchase,jwtHeader, HttpStatus.OK);
		else
			return new ResponseEntity<>(jwtHeader, HttpStatus.BAD_REQUEST);
	}
	/*update purchase */
	@PutMapping(path = "/adjust") 
	public ResponseEntity<Object> updatePurchase(@RequestBody Purchase newPurchase,@RequestHeader("Authorization") String authorization){
		//This methods responsibility it to add a new book to the purchase collection if it doesnt exist already or to increase the quantity if it is present
		//This method uses the userID in order to find the users Purchase collection
		String token = tokenProvider.extractToken(authorization);
		HttpHeaders jwtHeader = tokenProvider.getHeaderJWT(token);
		if (newPurchase !=null&& newPurchase.getQuantityToBuy()!=0) {
			PurchaseServ.upDatePurchase(newPurchase);
			return new ResponseEntity<>(jwtHeader, HttpStatus.ACCEPTED);
			}
		else if (newPurchase !=null&& newPurchase.getQuantityToBuy()==0) {
		PurchaseServ.deletePurchase(newPurchase);	
		return new ResponseEntity<>(jwtHeader, HttpStatus.ACCEPTED);
		}
		else
			return new ResponseEntity<>(jwtHeader, HttpStatus.BAD_REQUEST);
	}
	/*create new purchase*/
	@PostMapping(path = "/add") 
	public ResponseEntity<Object> addBookToPurchase(@RequestBody Purchase bookToAdd,@RequestHeader("Authorization") String authorization){
		String token = tokenProvider.extractToken(authorization);
		HttpHeaders jwtHeader = tokenProvider.getHeaderJWT(token);
		if (bookToAdd !=null) {
			PurchaseServ.createPurchase(bookToAdd);
			return new ResponseEntity<>(jwtHeader, HttpStatus.CREATED);
		}
		
		return new ResponseEntity<>(jwtHeader, HttpStatus.NOT_FOUND);
	}

	@GetMapping(path = "/{PurchaseId}") 
	public ResponseEntity<Object> getPurchaseById(@PathVariable int PurchaseId,@RequestHeader("Authorization") String authorization) {
		//gets the purchase by its PurchaseId
		String token = tokenProvider.extractToken(authorization);
		HttpHeaders jwtHeader = tokenProvider.getHeaderJWT(token);
		Purchase Purchase = PurchaseServ.getPurchaseById(PurchaseId);
		if (Purchase != null)
			return new ResponseEntity<>(Purchase,jwtHeader, HttpStatus.OK);
		else
			return new ResponseEntity<>(jwtHeader, HttpStatus.NOT_FOUND);
	}

	
	@DeleteMapping(path = "/{PurchaseId}")
	public ResponseEntity<Void> deletePurchase(@RequestBody Purchase PurchaseToDelete,@RequestHeader("Authorization") String authorization){
		//deletes the purchase by its PurchaseId
		String token = tokenProvider.extractToken(authorization);
		HttpHeaders jwtHeader = tokenProvider.getHeaderJWT(token);
		if (PurchaseToDelete !=null) {
			PurchaseServ.deletePurchase(PurchaseToDelete);
			return new ResponseEntity<>(jwtHeader, HttpStatus.OK);
		}
		return new ResponseEntity<>(jwtHeader, HttpStatus.BAD_REQUEST);
	}
	
	
}
