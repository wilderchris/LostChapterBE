package com.revature.lostchapterbackend.service;


import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.lostchapterbackend.dao.ShippingInfoDAO;
import com.revature.lostchapterbackend.model.ShippingInformation;

@Service
public class ShippingServiceImpl implements ShippingService {
	//This service is used to handle all aspects of shipping and has the below methods
		//getAllShippingInfos: gets all of the shipping information from the database
		//getShippingInformationById:  gets a specific shipping information by its unique id
		//addShippingInformation: creates and adds a shipping information to the database
		//getShippingInformationByUser: gets the shipping information of the user by passing in a userid
		//upDateShippingInformation: allows the user to update and change their shipping information
		//deleteShipping: deletes a shipping information from the database
		
	private Logger logger = LoggerFactory.getLogger(ShippingService.class);

	private ShippingInfoDAO shipDao;
	
	@Autowired
	public ShippingServiceImpl(ShippingInfoDAO shipDao) {
		this.shipDao = shipDao;
	}

	@Override
	@Transactional
	public List<ShippingInformation> getAllShippingInfos() {
		return shipDao.findAll();
	}

	@Override
	@Transactional
	public ShippingInformation getShippingInformationById(int id) {
		Optional<ShippingInformation> ship = shipDao.findById(id);
		if (ship.isPresent())
			return ship.get();
		else return null;
	}

	@Override
	@Transactional
	public int addShippingInformation(ShippingInformation newShippingInformation) {
		ShippingInformation ship = shipDao.save(newShippingInformation);
		if(ship != null)
		return ship.getShippingInfoId();
		else return 0;
	}

	@Override
	@Transactional
	public List<ShippingInformation> getShippingInformationByUser(int UserId) {
			List<ShippingInformation> ship = shipDao.findShippingInformationByUser(UserId);
			return ship;
	}

	@Override
	@Transactional
	public ShippingInformation upDateShippingInformation(ShippingInformation newShippingInformation) {
		
			if (shipDao.existsById(newShippingInformation.getShippingInfoId())) {
				shipDao.save(newShippingInformation);
				newShippingInformation = shipDao.findById(newShippingInformation.getShippingInfoId()).get();
				return newShippingInformation;
			}
			return null;
	}

	@Override
	@Transactional
	public void deleteShipping(ShippingInformation shipToDelete) {
		shipDao.delete(shipToDelete);
		
	}

}