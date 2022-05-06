package com.revature.lostchapterbackend.controllers;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.lostchapterbackend.LostChapterBackendApplication;
import com.revature.lostchapterbackend.controller.PurchaseController;
import com.revature.lostchapterbackend.model.Book;
import com.revature.lostchapterbackend.model.Purchase;
import com.revature.lostchapterbackend.service.PurchaseService;


@SpringBootTest(classes=LostChapterBackendApplication.class)
public class PurchaseControllerTest {
	@MockBean
	private PurchaseService purchaseServ;
	
	@Autowired
	private static PurchaseController purchaseController;
	
	// this object basically represents a mock of the Spring Web architecture
	private static MockMvc mockMvc;
	
	// this is a Jackson object mapper for JSON marshalling
	// (turning objects to JSON strings and vice versa
	private ObjectMapper objMapper = new ObjectMapper();
	private final String jwtToken = "BookMark eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJhdWQiOiJVc2VyIE1hbmFnZW1lbnQgUG9ydGFsIiwic3ViIjoicmlja3kyM2kiLCJpc3MiOiJTSUVSUkEgLSBMT1NUIENIQVBURVIgMiIsImV4cCI6MTY0NTMyNTAyNSwiaWF0IjoxNjQ0ODkzMDI1LCJhdXRob3JpdGllcyI6WyJSRUFEIl19.5HKK08thMWDNq4QaREVNOvcv9nKatrSd-ZH8dH2XexVM7RND2YrsgKrkygGQCtXL5WUOp4amWjeqIY_Vh53LrQ.";
	@BeforeAll
	public static void setUp () {
		// sets up the minimum architecture to test our controller
		mockMvc = MockMvcBuilders.standaloneSetup(PurchaseController.class).build();
	}
	
	@Test
	public void createPurchase() throws Exception {
		Purchase newPurchase = new Purchase();
		
		when(purchaseServ.createPurchase(newPurchase)).thenReturn(1);
		
		
		String jsonPurchase = objMapper.writeValueAsString(newPurchase);
		mockMvc.perform(post("/Purchase/add").content(jsonPurchase).contentType(MediaType.APPLICATION_JSON).header(HttpHeaders.AUTHORIZATION, jwtToken ))
				.andExpect(status().isCreated())
				.andReturn();
		}
	
	@Test
	public void getPurchaseById ()throws Exception {
		when(purchaseServ.getPurchaseById(1)).thenReturn(new Purchase());
		
		mockMvc.perform(get("/Purchase/{PurchaseId}",1
				).header(HttpHeaders.AUTHORIZATION, jwtToken )).andExpect(status().isOk()).andReturn();
	}
	
	@Test
	public void getPurchaseByIdNotFound() throws Exception {
		when(purchaseServ.getPurchaseById(1)).thenReturn(null);
		mockMvc.perform(get("/Purchase/{PurchaseId}", 1).header(HttpHeaders.AUTHORIZATION, jwtToken )).andExpect(status().isNotFound()).andReturn();
	}
	
	
//	@Test
//	public void addBookToPurchase(@RequestBody Book bookToAdd, @PathVariable int userId) throws Exception {
//		Book newBook = new Book ();
//		doNothing().when(PurchaseServ).addBooksToPurchase(newBook, userId);
//		
//		
//		String jsonBook = objMapper.writeValueAsString(newBook);
//		
//		mockMvc.perform(post("/Purchase/add/{bookToBuyId}/{userId}").content(jsonBook).contentType(MediaType.APPLICATION_JSON))
//		.andExpect(status().isBadRequest()).andReturn();
//	}
	
//	@Test 
//	public void addBookToPurchaseNoUser(@RequestBody Book bookToAdd, @PathVariable int PurchaseId) throws Exception {
//		String jsonBook = objMapper.writeValueAsString(null);
//		mockMvc.perform(post("/Purchase/delete/{bookToBuyId}/{userId}").content(jsonBook).contentType(MediaType.APPLICATION_JSON))
//		.andExpect(status().isBadRequest()).andReturn();
//	}
}
