package com.revature.lostchapterbackend.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.revature.lostchapterbackend.model.Purchase;
import com.revature.lostchapterbackend.model.Review;
import com.revature.lostchapterbackend.model.ShippingInformation;

@Service
public interface ShippingService {
	
	public List<ShippingInformation> getAllShippingInfos();
	public ShippingInformation getShippingInformationById(int id);
	public int addShippingInformation(ShippingInformation newShippingInformation); 
	public List<ShippingInformation> getShippingInformationByUser(int UserId);
	public ShippingInformation upDateShippingInformation(ShippingInformation newShippingInformation); 
	public void deleteShipping(ShippingInformation shipToDelete);

}
