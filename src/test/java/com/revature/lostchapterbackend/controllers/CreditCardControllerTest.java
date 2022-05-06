package com.revature.lostchapterbackend.controllers;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.GetMapping;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.lostchapterbackend.LostChapterBackendApplication;
import com.revature.lostchapterbackend.controller.BookController;
import com.revature.lostchapterbackend.controller.CreditCardInfoController;
import com.revature.lostchapterbackend.model.Book;
import com.revature.lostchapterbackend.model.CreditCardInfo;
import com.revature.lostchapterbackend.model.User;
import com.revature.lostchapterbackend.service.BookService;
import com.revature.lostchapterbackend.service.CreditCardInfoServ;

//@AutoConfigureMockMvc
//@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest(classes=LostChapterBackendApplication.class)
public class CreditCardControllerTest {
	@MockBean
	private CreditCardInfoServ cardServ;
	
	@Autowired
	private CreditCardInfoController cardController;
	
	private static MockMvc mockMvc;
	private ObjectMapper objMapper = new ObjectMapper();
	private final String jwtToken = "BookMark eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJhdWQiOiJVc2VyIE1hbmFnZW1lbnQgUG9ydGFsIiwic3ViIjoicmlja3kyM2kiLCJpc3MiOiJTSUVSUkEgLSBMT1NUIENIQVBURVIgMiIsImV4cCI6MTY0NTMyNTAyNSwiaWF0IjoxNjQ0ODkzMDI1LCJhdXRob3JpdGllcyI6WyJSRUFEIl19.5HKK08thMWDNq4QaREVNOvcv9nKatrSd-ZH8dH2XexVM7RND2YrsgKrkygGQCtXL5WUOp4amWjeqIY_Vh53LrQ.";
	
	@BeforeAll
	public static void setUp() {
		// this initializes the Spring Web/MVC architecture for just one controller
		// so that we can isolate and unit test it
		mockMvc = MockMvcBuilders.standaloneSetup(CreditCardInfoController.class).build();
	}
	
	@Test
	public void getCCByUserIfExists() throws Exception {
		when(cardServ.getCreditCardInfoByUser(1)).thenReturn(Collections.emptyList());
		
		mockMvc.perform(get("/ccinfo/user/{userId}", 1).header(HttpHeaders.AUTHORIZATION, jwtToken )).andExpect(status().isOk()).andReturn();
	
	}
	
	@Test
	public void getCCByUserIfDoesNotExist() throws Exception{
		when(cardServ.getCreditCardInfoByUser(1)).thenReturn(null);
		mockMvc.perform(get("/ccinfo/user/{userId}", 1).header(HttpHeaders.AUTHORIZATION, jwtToken )).andExpect(status().isNotFound()).andReturn();
	}
	
	@Test
	public void addCreditCardInfoSuccessfully() throws Exception{
		CreditCardInfo ccAdd = new CreditCardInfo();
		when(cardServ.addCreditCardInfo(ccAdd)).thenReturn(1);
		
		String jsonCreditCard = objMapper.writeValueAsString(ccAdd);
		
		mockMvc.perform(post("/ccinfo/add").content(jsonCreditCard).contentType(MediaType.APPLICATION_JSON).header(HttpHeaders.AUTHORIZATION, jwtToken ))
			.andExpect(status().isCreated())
			.andReturn();
		
	}
	
	@Test
	public void addCreditCardInfoUnsuccessfully() throws Exception{
		String jsonCreditCard = objMapper.writeValueAsString(null);
		mockMvc.perform(post("/ccinfo/add").content(jsonCreditCard).contentType(MediaType.APPLICATION_JSON).header(HttpHeaders.AUTHORIZATION, jwtToken ))
		.andExpect(status().isBadRequest())
		.andReturn();
	}
	
	@Test
	public void updateCreditCardInfoSuccessfully () throws Exception {
		CreditCardInfo newCreditCardInfo = new CreditCardInfo();
		//newCreditCardInfo.se
		when(cardServ.upDateCreditCardInfo(newCreditCardInfo)).thenReturn(newCreditCardInfo);
		
		String jsonCreditCard = objMapper.writeValueAsString(newCreditCardInfo);
		
		mockMvc.perform(post("/ccinfo/update").content(jsonCreditCard).contentType(MediaType.APPLICATION_JSON).header(HttpHeaders.AUTHORIZATION, jwtToken ))
		.andExpect(status().isCreated())
		.andReturn();
	}
}