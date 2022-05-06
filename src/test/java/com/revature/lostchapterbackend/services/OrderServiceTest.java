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
import com.revature.lostchapterbackend.dao.OrderDAO;
import com.revature.lostchapterbackend.dao.UserDAO;
import com.revature.lostchapterbackend.exceptions.OrderDoesNotExist;
import com.revature.lostchapterbackend.exceptions.UserNotFoundException;
import com.revature.lostchapterbackend.model.Book;
import com.revature.lostchapterbackend.model.Order;
import com.revature.lostchapterbackend.model.User;
import com.revature.lostchapterbackend.service.OrderService;

@SpringBootTest(classes=LostChapterBackendApplication.class)
public class OrderServiceTest {
	@MockBean
	private OrderDAO orderDao;
	@MockBean
	private UserDAO userDao;
	@Autowired
	private OrderService orderServ;
	
	private static List<Order> mockOrders;
	
	@BeforeAll
	public static void mockOrderSetUp() {
			mockOrders = new ArrayList<>();
			User user1 = new User();
			user1.setUserId(1);
			User user2 = new User();
			user2.setUserId(2);
		
		for (int i=1; i<=5; i++) {
			Order order = new Order();
			order.setOrderId(i);
			if (i<3)
				order.setUser(user1);
			if  (i>3)
				order.setUser(user2);
				mockOrders.add(order);
		}
	}
	
	@Test
	public void getOrderByIdExists() throws OrderDoesNotExist {
		Order order = new Order();
		order.setOrderId(1);
		
		when(orderDao.findById(1)).thenReturn(Optional.of(order));
		Optional<Order> actualOrder = orderServ.getOrderById(1);
		assertEquals(order, actualOrder);
	}
	
	@Test
	public void getOrderByIdDoesNotExist() throws OrderDoesNotExist {
		when(orderDao.findById(2)).thenReturn(Optional.empty());
		
		Optional<Order> actualOrder = orderServ.getOrderById(2);
		assertNull(actualOrder);
	}
	
	@Test
	public void getAllOrdersByUserExists() throws UserNotFoundException {
		User user = new User();
		user.setUserId(1);
		
		when(orderDao.findByUser(2)).thenReturn(mockOrders);
		
		List<Order> actualOrders = orderServ.getAllOrdersByUser(1);
		boolean onlyUser = true;
		for (Order order : actualOrders) {
			if (!order.getUser().equals(user))
				onlyUser = false;
		}
		assertTrue(onlyUser);
	}
	
	@Test
	public void getAllOrdersByUserDoesNotExist() throws UserNotFoundException {
		
		when(orderDao.findByUser(0)).thenReturn(mockOrders);
		
		List<Order> actualOrders = orderServ.getAllOrdersByUser(0);
		assertTrue(actualOrders.isEmpty());
	}
	
	@Test
	public void addOrderSuccessfully() {
		Order newOrder = new Order();
		Order mockOrder = new Order();
		mockOrder.setOrderId(69);
		
		when(orderDao.save(newOrder)).thenReturn(mockOrder);
		
		int newId = orderServ.addOrder(newOrder);
		
		assertNotEquals(0, newId);
	}
	
	@Test
	public void addOrderUnsuccessfully() {
		Order order = new Order();
		
		when(orderDao.save(order)).thenReturn(order);
		
		int newId = orderServ.addOrder(order);
		
		assertEquals(0,newId);
	}
}