package com.revature.lostchapterbackend.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.revature.lostchapterbackend.LostChapterBackendApplication;
import com.revature.lostchapterbackend.dao.ReviewDAO;
import com.revature.lostchapterbackend.dao.ShippingInfoDAO;
import com.revature.lostchapterbackend.model.ShippingInformation;
import com.revature.lostchapterbackend.model.User;
import com.revature.lostchapterbackend.model.Order;
import com.revature.lostchapterbackend.model.Review;
import com.revature.lostchapterbackend.service.ReviewService;
import com.revature.lostchapterbackend.service.ShippingService;

@SpringBootTest(classes=LostChapterBackendApplication.class)
public class ShippingServiceTest {
	
	@MockBean
	private ShippingInfoDAO shipDao;
	@Autowired
	private ShippingService shipServ;
	
	private static List<ShippingInformation> mockShip;
	
	@BeforeAll
	public static void mockShippingInformationSetUp() {
			mockShip = new ArrayList<>();
			User user = new User();
		
		for (int i=1; i<=5; i++) {
			ShippingInformation ShippingInformation = new ShippingInformation();
			ShippingInformation.setShippingInfoId(i);
			if (i<3)
				ShippingInformation.setUser(user);
			mockShip.add(ShippingInformation);
		}
	}
	
	@Test
	public void getAllShippingInfos() {
		when(shipDao.findAll()).thenReturn(mockShip);
		
		List <ShippingInformation> actualShippingInformation = shipServ.getAllShippingInfos();
		
		assertEquals(mockShip, actualShippingInformation);
	}
	
	@Test
	public void getShippingInformationByIdExists() {
		ShippingInformation ship = new ShippingInformation();
		ship.setShippingInfoId(1);
		
		when(shipDao.findById(1)).thenReturn(Optional.of(ship));
		
		ShippingInformation actualShippingInformation = shipServ.getShippingInformationById(1);
		assertEquals(ship, actualShippingInformation);
	}
	
	@Test
	public void getShippingInformationByIdDoesNotExist() {
		when(shipDao.findById(1)).thenReturn(Optional.empty());
		
		ShippingInformation actualShippingInformation = shipServ.getShippingInformationById(1);
		assertNull(actualShippingInformation);
	}
	
	@Test
	public void addShippingInformationSuccessfully() {
		ShippingInformation newShippingInformation = new ShippingInformation();
		ShippingInformation mockShippingInformation = new ShippingInformation();
		mockShippingInformation.setShippingInfoId(69);
		
		when(shipDao.save(newShippingInformation)).thenReturn(mockShippingInformation);
		
		int newId = shipServ.addShippingInformation(newShippingInformation);
		
		assertNotEquals(0, newId);
	}
	
	@Test
	public void addShippingInformationUnsuccessfully() {
		ShippingInformation ShippingInformation = new ShippingInformation();
		
		when(shipDao.save(ShippingInformation)).thenReturn(ShippingInformation);
		
		int newId = shipServ.addShippingInformation(ShippingInformation);
		
		assertEquals(0,newId);
	}
	
	@Test
	public void  getShippingInformationByUserExists() {
		User user = new User();
		user.setUserId(1);
		
		when(shipDao.findShippingInformationByUser(2)).thenReturn(mockShip);
		
		List<ShippingInformation> actualShippingInformations = shipServ.getShippingInformationByUser(1);
		boolean onlyUser = true;
		for (ShippingInformation ship : actualShippingInformations) {
			if (!ship.getUser().equals(ship))
				onlyUser = false;
		}
		assertTrue(onlyUser);
	}
	
	@Test
	public void  getShippingInformationByUserDoesNotExist() {
		List<ShippingInformation> mockShips = new ArrayList<>();
		
		when(shipDao.findShippingInformationByUser(0)).thenReturn(mockShips);
		
		List<ShippingInformation> actualShippingInformations = shipServ.getShippingInformationByUser(0);
		assertTrue(actualShippingInformations.isEmpty());
	}

}
