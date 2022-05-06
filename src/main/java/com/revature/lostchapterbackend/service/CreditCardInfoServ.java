package com.revature.lostchapterbackend.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.revature.lostchapterbackend.model.CreditCardInfo;




@Service
public interface CreditCardInfoServ {

	public CreditCardInfo getCreditCardInfoById(int id); 
	public List<CreditCardInfo> getCreditCardInfoByUser(int UserId);
	public int addCreditCardInfo(CreditCardInfo newCreditCardInfo);
	public CreditCardInfo upDateCreditCardInfo(CreditCardInfo newCreditCardInfo); 
	public void deleteCreditCardInfo(CreditCardInfo creditCardInfoToDelete);
}
