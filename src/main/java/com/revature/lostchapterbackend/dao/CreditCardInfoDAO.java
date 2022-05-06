package com.revature.lostchapterbackend.dao;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import com.revature.lostchapterbackend.model.CreditCardInfo;

@Repository
public interface CreditCardInfoDAO extends JpaRepository< CreditCardInfo, Integer>{
	//This DAO is used to hold credit card objects which can be found by their ids
	//Methods include
		//findByUser: finds a credit card by its userId

	public List<CreditCardInfo> findByUser(int userId);
}
