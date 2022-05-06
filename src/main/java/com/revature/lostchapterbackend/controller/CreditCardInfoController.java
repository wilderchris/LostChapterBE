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

import com.revature.lostchapterbackend.model.CreditCardInfo;

import com.revature.lostchapterbackend.service.CreditCardInfoServ;


@RestController
@RequestMapping(path="/ccinfo")
@CrossOrigin("*")
public class CreditCardInfoController {
	//This controller is used for the following
		//getCCByUser GET /user/{userId}
		//updateCreditCardInfo PUT /update
		//addCreditCardInfo POST /add
		//getCCById GET /{ccId}
		//deletePurchase DELETE /{ccId}

	private static CreditCardInfoServ CreditCardInfoServ;
	private static TokenProvider tokenProvider;
	public CreditCardInfoController() {
		super();
	}
	
	@Autowired
	public CreditCardInfoController(CreditCardInfoServ CreditCardInfoServ, TokenProvider tokenProvider) {
		this.CreditCardInfoServ=CreditCardInfoServ;
		this.tokenProvider=tokenProvider;
	}
	@GetMapping(path = "/user/{userId}") 
	public ResponseEntity<Object> getCCByUser(@PathVariable int userId, @RequestHeader("Authorization") String authorization){
		//gets the purchase by its PurchaseId
		String token = tokenProvider.extractToken(authorization);
		HttpHeaders jwtHeader = tokenProvider.getHeaderJWT(token);
		List<CreditCardInfo> orders = CreditCardInfoServ.getCreditCardInfoByUser(userId);	
		if (orders != null)
			return new ResponseEntity<>(orders,jwtHeader, HttpStatus.OK);
		else
			return new ResponseEntity<>(jwtHeader, HttpStatus.NOT_FOUND);
	}
	@PutMapping(path = "/update") 
	public ResponseEntity<Object> updateCreditCardInfo(@RequestBody CreditCardInfo newCreditCardInfo, @RequestHeader("Authorization") String authorization){
		//This methods responsibility it to add a new book to the CreditCardInfo collection if it doesnt exist already or to increase the quantity if it is present
		//This method uses the userID in order to find the users CreditCardInfo collection
		String token = tokenProvider.extractToken(authorization);
		HttpHeaders jwtHeader = tokenProvider.getHeaderJWT(token);
		if (newCreditCardInfo !=null) {
			CreditCardInfoServ.upDateCreditCardInfo(newCreditCardInfo);
			return new ResponseEntity<>(jwtHeader, HttpStatus.ACCEPTED);
		}
		else
			return new ResponseEntity<>(jwtHeader, HttpStatus.BAD_REQUEST);
	}
	
	@PostMapping(path = "/add") 
	public ResponseEntity<Object> addCreditCardInfo(@RequestBody CreditCardInfo ccAdd,@RequestHeader("Authorization") String authorization){
		String token = tokenProvider.extractToken(authorization);
		HttpHeaders jwtHeader = tokenProvider.getHeaderJWT(token);
		if (ccAdd !=null) {
			CreditCardInfoServ.addCreditCardInfo(ccAdd);
			return new ResponseEntity<>(jwtHeader, HttpStatus.CREATED);
		}
		
		return new ResponseEntity<>(jwtHeader, HttpStatus.BAD_REQUEST);
	}
	@GetMapping(path = "/{ccId}") 
	public ResponseEntity<Object> getCCById(@PathVariable int ccId,@RequestHeader("Authorization") String authorization) {
		//gets the purchase by its PurchaseId
		String token = tokenProvider.extractToken(authorization);
		HttpHeaders jwtHeader = tokenProvider.getHeaderJWT(token);
		CreditCardInfo cc = CreditCardInfoServ.getCreditCardInfoById(ccId);
		if (cc != null)
			return new ResponseEntity<>(cc,jwtHeader, HttpStatus.OK);
		else
			return new ResponseEntity<>(cc,jwtHeader, HttpStatus.NOT_FOUND);
	}

	
	@DeleteMapping(path = "/{ccId}")
	public ResponseEntity<Void> deletePurchase(@RequestBody CreditCardInfo ccToDelete,@RequestHeader("Authorization") String authorization){
		//deletes the purchase by its PurchaseId
		String token = tokenProvider.extractToken(authorization);
		HttpHeaders jwtHeader = tokenProvider.getHeaderJWT(token);
		if (ccToDelete !=null) {
			CreditCardInfoServ.deleteCreditCardInfo(ccToDelete);
			return new ResponseEntity<>(jwtHeader, HttpStatus.OK);
		}
		return new ResponseEntity<>(jwtHeader, HttpStatus.BAD_REQUEST);
	}
	
}
