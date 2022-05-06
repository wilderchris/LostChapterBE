package com.revature.lostchapterbackend.controller;

import java.util.List;
import com.revature.lostchapterbackend.JWT.TokenProvider;

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

import com.revature.lostchapterbackend.exceptions.UserNotFoundException;

import com.revature.lostchapterbackend.model.ShippingInformation;


import com.revature.lostchapterbackend.service.ShippingService;


@RestController
@RequestMapping(path="/shipping")
@CrossOrigin("*")
public class ShippingController {
	//This controller is used for the following
		//updateshipping PUT /update
		//addBookToshipping POST /add
		//getshippingByUser GET user/{userId}
		//getshippingById GET /{shippingId}
		//deletePurchase DELETE /{shippingId}
	private static TokenProvider tokenProvider;
	private static ShippingService shippingServ;
	public ShippingController() {
		super();
	}
	
	@Autowired
	public ShippingController(ShippingService shippingServ,TokenProvider tokenProvider) {
		this.shippingServ=shippingServ;
		this.tokenProvider=tokenProvider;
	}
	/*Update existing ship info*/
	@PutMapping(path = "/update") 
	public ResponseEntity<Object> updateshipping(@RequestBody ShippingInformation newshipping,@RequestHeader("Authorization") String authorization){
		//This methods responsibility it to add a new book to the shipping collection if it doesnt exist already or to increase the quantity if it is present
		//This method uses the userID in shipping to find the users shipping collection
		String token = tokenProvider.extractToken(authorization);
		HttpHeaders jwtHeader = tokenProvider.getHeaderJWT(token);
		if (newshipping !=null) {
			shippingServ.upDateShippingInformation(newshipping);
			return new ResponseEntity<>(jwtHeader, HttpStatus.ACCEPTED);
		}
		else
			return new ResponseEntity<>(jwtHeader, HttpStatus.BAD_REQUEST);
	}
	
	/*add ship info*/
	@PostMapping(path = "/add") 
	public ResponseEntity<Object> addBookToshipping(@RequestBody ShippingInformation shipAdd,@RequestHeader("Authorization") String authorization){
		String token = tokenProvider.extractToken(authorization);
		HttpHeaders jwtHeader = tokenProvider.getHeaderJWT(token);
		if (shipAdd !=null) {
			shippingServ.addShippingInformation(shipAdd);
			return new ResponseEntity<>(jwtHeader, HttpStatus.CREATED);
		}
		
		return new ResponseEntity<>(jwtHeader, HttpStatus.BAD_REQUEST);
	}
	/*get all users ship info*/
	@GetMapping(path = "user/{userId}") 
	public ResponseEntity<Object> getshippingByUser(@PathVariable int userId,@RequestHeader("Authorization") String authorization)throws UserNotFoundException{
		//gets the purchase by its PurchaseId
		String token = tokenProvider.extractToken(authorization);
		HttpHeaders jwtHeader = tokenProvider.getHeaderJWT(token);
		List<ShippingInformation> shippings = shippingServ.getShippingInformationByUser(userId);	
		if (shippings != null)
			return new ResponseEntity<>(shippings,jwtHeader, HttpStatus.OK);
		else
			return new ResponseEntity<>(jwtHeader, HttpStatus.NOT_FOUND);
	}
	/*Get ship info by id*/
	@GetMapping(path = "/{shippingId}") 
	public ResponseEntity<Object> getshippingById(@PathVariable int shippingId,@RequestHeader("Authorization") String authorization){
		//gets the purchase by its PurchaseId
		String token = tokenProvider.extractToken(authorization);
		HttpHeaders jwtHeader = tokenProvider.getHeaderJWT(token);
		ShippingInformation ship = shippingServ.getShippingInformationById(shippingId);	
		if (ship != null)
			return new ResponseEntity<>(ship,jwtHeader, HttpStatus.OK);
		else
			return new ResponseEntity<>(jwtHeader, HttpStatus.NOT_FOUND);
	}
	/*delete existing ship info*/
	
	@DeleteMapping(path = "/{shippingId}")
	public ResponseEntity<Void> deletePurchase(@RequestBody ShippingInformation shipToDelete, @RequestHeader("Authorization") String authorization){
		//deletes the purchase by its PurchaseId
		String token = tokenProvider.extractToken(authorization);
		HttpHeaders jwtHeader = tokenProvider.getHeaderJWT(token);
		if (shipToDelete !=null) {
			shippingServ.deleteShipping(shipToDelete);
			return new ResponseEntity<>(jwtHeader, HttpStatus.OK);
		}
		return new ResponseEntity<>(jwtHeader, HttpStatus.BAD_REQUEST);
	}
}