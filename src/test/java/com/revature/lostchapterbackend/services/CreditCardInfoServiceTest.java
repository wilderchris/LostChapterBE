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
import com.revature.lostchapterbackend.dao.CreditCardInfoDAO;
import com.revature.lostchapterbackend.exceptions.BookNotFoundException;
import com.revature.lostchapterbackend.model.Book;
import com.revature.lostchapterbackend.model.CreditCardInfo;
import com.revature.lostchapterbackend.model.Review;
import com.revature.lostchapterbackend.model.ShippingInformation;
import com.revature.lostchapterbackend.model.User;
import com.revature.lostchapterbackend.service.CreditCardInfoServ;

@SpringBootTest(classes=LostChapterBackendApplication.class)
public class CreditCardInfoServiceTest {
	@MockBean
	private CreditCardInfoDAO creditDao;
	@Autowired
	private CreditCardInfoServ creditServ;
	
	private static List<CreditCardInfo> mockCredit;
	
	@BeforeAll
	public static void mockCreditSetUp() {
			mockCredit = new ArrayList<>();
			User user = new User();
		
		for (int i=1; i<=5; i++) {
			CreditCardInfo credit = new CreditCardInfo();
			credit.setCcInfoId(i);
			if (i<3)
				credit.setUser(user);
		}
	}
	
	@Test
	public void getCreditCardInfoByIdExists() {
		CreditCardInfo CreditCardInfo = new CreditCardInfo();
		CreditCardInfo.setCcInfoId(1);
		
		when(creditDao.findById(1)).thenReturn(Optional.of(CreditCardInfo));
		
		CreditCardInfo actualCreditCardInfo = creditServ.getCreditCardInfoById(1);
		assertEquals(CreditCardInfo, actualCreditCardInfo);
	}
	
	@Test
	public void getCreditCardInfoByIdDoesNotExist() {
		when(creditDao.findById(1)).thenReturn(Optional.empty());
		
		CreditCardInfo actualCreditCardInfo = creditServ.getCreditCardInfoById(1);
		assertNull(actualCreditCardInfo);
	}
	
	@Test 
	public void getCreditCardInfoByUserExists() throws BookNotFoundException  {
		User user = new User();
		user.setUserId(1);
		
		when(creditDao.findByUser(1)).thenReturn(mockCredit);
		
		List<CreditCardInfo> actualCreditCardInfo = creditServ.getCreditCardInfoByUser(1);
		boolean onlyBook = true;
		for (CreditCardInfo CreditCardInfo : actualCreditCardInfo) {
			if (!CreditCardInfo.getUser().equals(user))
				onlyBook = false;
		}
		assertTrue(onlyBook);
	}
	
	@Test 
	public void getCreditCardInfoByUserDoesNotExist() throws BookNotFoundException  {
		
		when(creditDao.findByUser(2)).thenReturn(mockCredit);
		
		List<CreditCardInfo> actualCreditCardInfo = creditServ.getCreditCardInfoByUser(0);
		assertTrue(actualCreditCardInfo.isEmpty());
	}
	
	@Test
	public void addCreditCardInfoSuccessfully() {
		CreditCardInfo newCreditCardInfo = new CreditCardInfo();
		CreditCardInfo mockCreditCardInfo = new CreditCardInfo();
		mockCreditCardInfo.setCcInfoId(69);
		
		when(creditDao.save(newCreditCardInfo)).thenReturn(mockCreditCardInfo);
		
		int newId = creditServ.addCreditCardInfo(newCreditCardInfo);
		
		assertNotEquals(0, newId);
	}
	
	@Test
	public void addCreditCardInfoUnSuccessfully() {
		CreditCardInfo CreditCardInfo = new CreditCardInfo();
		
		when(creditDao.save(CreditCardInfo)).thenReturn(CreditCardInfo);
		
		int newId = creditServ.addCreditCardInfo(CreditCardInfo);
		
		assertEquals(0,newId);
	}
	
	@Test
	public void updateCreditCardInfoSuccessfully() {
		CreditCardInfo editedCreditCardInfo = new CreditCardInfo();
		editedCreditCardInfo.setCcInfoId(1);
		editedCreditCardInfo.setCardNumber(12345);
		
		when(creditDao.findById(1)).thenReturn(Optional.of(editedCreditCardInfo));
		when(creditDao.save(Mockito.any(CreditCardInfo.class))).thenReturn(editedCreditCardInfo);
		
		CreditCardInfo actualCreditCardInfo = creditServ.upDateCreditCardInfo(editedCreditCardInfo);
		
		assertEquals(editedCreditCardInfo, actualCreditCardInfo);

	}
	
	@Test
	public void updateCreditCardInfoUnsuccessfully() {
		when(creditDao.findById(2)).thenReturn(Optional.empty());
		
		CreditCardInfo editedCreditCardInfo = new CreditCardInfo();
		editedCreditCardInfo.setCcInfoId(2);
		editedCreditCardInfo.setCardNumber(12345);
		
		CreditCardInfo actualCreditCardInfo = creditServ.upDateCreditCardInfo(editedCreditCardInfo);
		
		assertNull(actualCreditCardInfo);
		verify(creditDao, times(0)).save(Mockito.any(CreditCardInfo.class));
	}

}
