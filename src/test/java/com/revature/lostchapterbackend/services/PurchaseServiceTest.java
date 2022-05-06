package com.revature.lostchapterbackend.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.revature.lostchapterbackend.LostChapterBackendApplication;
import com.revature.lostchapterbackend.dao.PurchaseDAO;
import com.revature.lostchapterbackend.model.Book;
import com.revature.lostchapterbackend.model.Purchase;
import com.revature.lostchapterbackend.service.PurchaseService;

@SpringBootTest(classes=LostChapterBackendApplication.class)
public class PurchaseServiceTest {
	@MockBean
	private PurchaseDAO purchaseDao;
	@Autowired
	private PurchaseService purchaseServ;
	
	@BeforeAll
	public static void MockXSetUp() {
		
	}
	
	@Test
	public void getPurchaseByIdExists() {
		Purchase purchase = new Purchase();
		purchase.setPurchaseId(1);
		
		when(purchaseDao.findById(1)).thenReturn(Optional.of(purchase));
		
		Purchase actualPurchase = purchaseServ.getPurchaseById(1);
		assertEquals(purchase, actualPurchase);
	}
	
	@Test
	public void getPurchaseByIdDoesNotExist() {
		when(purchaseDao.findById(1)).thenReturn(Optional.empty());
		
		Purchase actualPurchase = purchaseServ.getPurchaseById(1);
		assertNull(actualPurchase);
	}
	
	@Test
	public void createPurchaSuccessfully() {
		Purchase newPurchase = new Purchase();
		Purchase mockPurchase = new Purchase();
		mockPurchase.setPurchaseId(69);
		
		when(purchaseDao.save(newPurchase)).thenReturn(mockPurchase);
		
		int newId = purchaseServ.createPurchase(newPurchase);
		
		assertNotEquals(0, newId);
	}
	
	@Test
	public void createPurchaseunsuccessfully() {
		Purchase purchase = new Purchase();
		
		when(purchaseDao.save(purchase)).thenReturn(purchase);
		
		int newId = purchaseServ.createPurchase(purchase);
		
		assertEquals(0,newId);
	}
	
}
