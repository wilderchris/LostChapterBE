package com.revature.lostchapterbackend.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import com.revature.lostchapterbackend.model.ShippingInformation;
@Repository
public interface ShippingInfoDAO extends JpaRepository<ShippingInformation, Integer> {
	//This DAO is used to hold ShippingInformation objects which can be found by their ids
	//Methods include
		//findByUser:finds a users ShippingInformation by using their userId
	public List<ShippingInformation> findShippingInformationByUser(int userId);
}
