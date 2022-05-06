package com.revature.lostchapterbackend.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.lostchapterbackend.dao.CreditCardInfoDAO;
import com.revature.lostchapterbackend.model.CreditCardInfo;
import com.revature.lostchapterbackend.model.Purchase;

@Service
public class CreditCardInfoImpl implements CreditCardInfoServ {
	//This service is used to handle all aspects of credit cards and has the below methods
		//upDateCreditCardInfo: This method allows for the user to update their credit card information
		//getCreditCardInfoById:  This method gets a credit cards info by its id
		//addCreditCardInfo: This method creates a new credit card in the datbase
		//getCreditCardInfoByUser: This method gets a credit card by its users information
		//deleteCreditCardInfo: This method deletes a credit cards information from the database

	private CreditCardInfoDAO creditCardInfoDao;
	
	@Autowired
	public CreditCardInfoImpl(CreditCardInfoDAO creditCardInfoDao) {
		this.creditCardInfoDao = creditCardInfoDao;
	}

	@Override
	@Transactional
	public CreditCardInfo upDateCreditCardInfo(CreditCardInfo newCreditCardInfo) {
		if (creditCardInfoDao.existsById(newCreditCardInfo.getCcInfoId())) {
			creditCardInfoDao.save(newCreditCardInfo);
			newCreditCardInfo = creditCardInfoDao.findById(newCreditCardInfo.getCcInfoId()).get();
			return newCreditCardInfo;
		}
		return null;
	}
	
	@Override
	@Transactional
	public CreditCardInfo getCreditCardInfoById(int id) {
		Optional<CreditCardInfo> ship =creditCardInfoDao.findById(id);
		if (ship.isPresent())
			return ship.get();
		else return null;
	}

	@Override
	@Transactional
	public int addCreditCardInfo(CreditCardInfo newCreditCardInfo) {
		CreditCardInfo creditCardInfo = creditCardInfoDao.save(newCreditCardInfo);
		if(creditCardInfo != null)
		return creditCardInfo.getCcInfoId();
		else return 0;
	}

	@Override
	@Transactional
	public List<CreditCardInfo> getCreditCardInfoByUser(int UserId) {
			List<CreditCardInfo> ship = creditCardInfoDao.findByUser(UserId);
			return ship;
	}



	@Override
	@Transactional
	public void deleteCreditCardInfo(CreditCardInfo creditCardInfoToDelete) {
		creditCardInfoDao.delete(creditCardInfoToDelete);
		
	}






	
}
