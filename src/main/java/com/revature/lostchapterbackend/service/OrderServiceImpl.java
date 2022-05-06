package com.revature.lostchapterbackend.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.lostchapterbackend.dao.OrderDAO;
import com.revature.lostchapterbackend.exceptions.OrderDoesNotExist;
import com.revature.lostchapterbackend.exceptions.UserNotFoundException;
import com.revature.lostchapterbackend.model.Order;

@Service
public class OrderServiceImpl implements OrderService {
	//This service is used to handle all aspects of Orders and has the below methods
		//getOrderById: This method gets an order by its id
		//getAllOrdersByUser: This method returns all of the users orders by using their information
		//getOrderByCartId: This method returns an order by a cart id
		//addOrder: This method allows for the creation of new orders in the database
		//deleteOrder: This method allows for the deletion of orders in the database
		//updateOrderBy: This method allows for the updating of the orders information

	private OrderDAO orderdao;
	
	@Autowired
	public OrderServiceImpl(OrderDAO orderdao) {
		this.orderdao = orderdao;
	}
	
	@Override
	@Transactional
	public Optional<Order>  getOrderById(int orderId) throws OrderDoesNotExist {
		try
		{
			Optional<Order> order = orderdao.findById(orderId);
			return order;
		}catch(Exception e)
		{
			throw new OrderDoesNotExist("Order Id Not Found, Try Again!");
		}
	}

	@Override
	@Transactional
	public List<Order> getAllOrdersByUser(int userId) throws UserNotFoundException{
		try
		{
			List<Order> orders = orderdao.findByUser(userId);
			return orders;
		}catch(Exception e)
		{
			throw new UserNotFoundException("User Id Not Found, Try Again!");
		}
	}

//	@Override
//	@Transactional
//	public Order getOrderByCartId(int cartId) throws CartNotFoundException{
//		try
//		{
//			Order order = orderdao.findBycart(cartId);
//			return order;
//		}catch(Exception e)
//		{
//			throw new CartNotFoundException("Cart Id Not Found, Try Again!");
//		}
//	}

	@Override
	@Transactional
	public int addOrder(Order newOrder) {
		Order order = orderdao.save(newOrder);
		if(order != null)
		return order.getOrderId();
		else return 0;
	}

	@Override
	@Transactional
	public void deleteOrder(Order orderToDelete) {
	
		orderdao.delete(orderToDelete);
	}

	@Override
	@Transactional
	public Order updateOrderBy(Order updateOrder) throws OrderDoesNotExist {
		if (orderdao.existsById(updateOrder.getOrderId())) {
			orderdao.save(updateOrder);
			updateOrder =orderdao.findById(updateOrder.getOrderId()).get();
			return updateOrder;
		}
		return null;
	}



}
