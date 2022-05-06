package com.revature.lostchapterbackend.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.revature.lostchapterbackend.model.Purchase;

@Repository
public interface PurchaseDAO extends JpaRepository<Purchase, Integer>{
	//This DAO is used to hold Purchase objects which can be found by their ids
	//Methods include
		//findByOrder: finds an order by its orderId
		//findByOrder_User: finds an order by its userId
	public List<Purchase> findByOrder(int orderId);
	public List<Purchase> findByOrder_User(int userId);
	
}
