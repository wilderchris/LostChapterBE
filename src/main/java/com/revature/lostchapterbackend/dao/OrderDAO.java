package com.revature.lostchapterbackend.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.revature.lostchapterbackend.model.Order;
@Repository
public interface OrderDAO extends JpaRepository<Order, Integer>{
	//This DAO is used to hold order objects which can be found by their ids
	//Methods include
		//findByUser: finds an order by its userId

	public List<Order> findByUser(int userId);
	

}
