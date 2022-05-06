package com.revature.lostchapterbackend.controller;


import java.util.List;
import java.util.Optional;
import com.revature.lostchapterbackend.JWT.TokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpHeaders;
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

import com.revature.lostchapterbackend.exceptions.OrderDoesNotExist;
import com.revature.lostchapterbackend.exceptions.UserNotFoundException;
import com.revature.lostchapterbackend.model.Order;

import com.revature.lostchapterbackend.service.OrderService;



@RestController
@RequestMapping(path="/order")
@CrossOrigin("*")
public class OrderController {
	//This controller is used for the following
		//updateOrder PUT /update
		//addBookToOrder POST /add
		//getOrderByUser GET user/{userId}
		//getOrderById GET /{orderId}
		//deletePurchase DELETE /{orderId}
	private static TokenProvider tokenProvider;
	private static OrderService orderServ;
	public OrderController() {
		super();
	}
	
	@Autowired
	public OrderController(OrderService orderServ,TokenProvider tokenProvider) {
		this.orderServ=orderServ;
		this.tokenProvider=tokenProvider;
	}
	/*needs update*/
	@PutMapping(path = "/update") 
	public ResponseEntity<Object> updateOrder(@RequestBody Order newOrder,@RequestHeader("Authorization") String authorization) throws OrderDoesNotExist{
		//This methods responsibility it to add a new book to the Order collection if it doesnt exist already or to increase the quantity if it is present
		//This method uses the userID in order to find the users Order collection
		String token = tokenProvider.extractToken(authorization);
		HttpHeaders jwtHeader = tokenProvider.getHeaderJWT(token);
		if (newOrder !=null) {
			orderServ.updateOrderBy(newOrder);
			return new ResponseEntity<>(jwtHeader, HttpStatus.ACCEPTED);
		}
		else
			return new ResponseEntity<>(jwtHeader, HttpStatus.BAD_REQUEST);
	}
	
	@PostMapping(path = "/add") 
	public ResponseEntity<Object> addBookToOrder(@RequestBody Order orderAdd,@RequestHeader("Authorization") String authorization){
		String token = tokenProvider.extractToken(authorization);
		HttpHeaders jwtHeader = tokenProvider.getHeaderJWT(token);
		if (orderAdd !=null) {
			orderServ.addOrder(orderAdd);
			return new ResponseEntity<>(jwtHeader, HttpStatus.CREATED);
		}
		
		return new ResponseEntity<>(jwtHeader, HttpStatus.BAD_REQUEST);
	}
	@GetMapping(path = "/user/{userId}") 
	public ResponseEntity<Object> getOrderByUser(@PathVariable int userId,@RequestHeader("Authorization") String authorization)throws UserNotFoundException{
		//gets the purchase by its PurchaseId
		String token = tokenProvider.extractToken(authorization);
		HttpHeaders jwtHeader = tokenProvider.getHeaderJWT(token);
		List<Order> orders = orderServ.getAllOrdersByUser(userId);	
		if (orders != null)
			return new ResponseEntity<>(orders,jwtHeader, HttpStatus.OK);
		else
			return new ResponseEntity<>(jwtHeader, HttpStatus.NOT_FOUND);
	}
	@GetMapping(path = "/{orderId}") 
	public ResponseEntity<Object> getOrderById(@PathVariable int orderId,@RequestHeader("Authorization") String authorization) throws OrderDoesNotExist{
		//gets the purchase by its PurchaseId
		String token = tokenProvider.extractToken(authorization);
		HttpHeaders jwtHeader = tokenProvider.getHeaderJWT(token);
		Optional<Order>  order = orderServ.getOrderById(orderId);	
		if (order != null)
			return new ResponseEntity<>(order,jwtHeader, HttpStatus.OK);
		else
			return new ResponseEntity<>(order,jwtHeader, HttpStatus.NOT_FOUND);
	}

	
	@DeleteMapping(path = "/{orderId}")
	public ResponseEntity<Void> deletePurchase(@RequestBody Order orderToDelete,@RequestHeader("Authorization") String authorization){
		//deletes the purchase by its PurchaseId
		String token = tokenProvider.extractToken(authorization);
		HttpHeaders jwtHeader = tokenProvider.getHeaderJWT(token);
		if (orderToDelete !=null) {
			orderServ.deleteOrder(orderToDelete);
			return new ResponseEntity<>(jwtHeader, HttpStatus.OK);
		}
		return new ResponseEntity<>(jwtHeader, HttpStatus.BAD_REQUEST);
	}
}
