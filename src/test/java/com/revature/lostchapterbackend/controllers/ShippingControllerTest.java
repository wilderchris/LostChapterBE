package com.revature.lostchapterbackend.controllers;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Collections;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.lostchapterbackend.LostChapterBackendApplication;
import com.revature.lostchapterbackend.controller.ReviewController;
import com.revature.lostchapterbackend.controller.ShippingController;
import com.revature.lostchapterbackend.model.ShippingInformation;
import com.revature.lostchapterbackend.model.User;
import com.revature.lostchapterbackend.service.ShippingService;

@SpringBootTest(classes=LostChapterBackendApplication.class)
public class ShippingControllerTest {
	
	@MockBean
	private ShippingService shipServ;
	
	@Autowired
	private ShippingController shipController;
	
	private static MockMvc mockMvc;
	
	private ObjectMapper objMapper = new ObjectMapper();
	private final String jwtToken = "BookMark eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJhdWQiOiJVc2VyIE1hbmFnZW1lbnQgUG9ydGFsIiwic3ViIjoicmlja3kyM2kiLCJpc3MiOiJTSUVSUkEgLSBMT1NUIENIQVBURVIgMiIsImV4cCI6MTY0NTMyNTAyNSwiaWF0IjoxNjQ0ODkzMDI1LCJhdXRob3JpdGllcyI6WyJSRUFEIl19.5HKK08thMWDNq4QaREVNOvcv9nKatrSd-ZH8dH2XexVM7RND2YrsgKrkygGQCtXL5WUOp4amWjeqIY_Vh53LrQ.";
	
	
	@BeforeAll
	public static void setUp() {
		// this initializes the Spring Web/MVC architecture for just one controller
		// so that we can isolate and unit test it
		mockMvc = MockMvcBuilders.standaloneSetup(ShippingController.class).build();
	}
	
	@Test
	public void getShippingbyIdExists() throws Exception {
		ShippingInformation ShippingInformation = new ShippingInformation();
		when(shipServ.getShippingInformationById(1)).thenReturn(ShippingInformation);
		
		mockMvc.perform(get("/shipping//{shippingId}", 1).header(HttpHeaders.AUTHORIZATION, jwtToken )).andExpect(status().isOk()).andReturn();
	}
	
	@Test
	public void getShippingByIdDoesNotExist() throws Exception {
		when(shipServ.getShippingInformationById(1)).thenReturn(null);
			
		mockMvc.perform(get("/shipping//{shippingId}", 1).header(HttpHeaders.AUTHORIZATION, jwtToken )).andExpect(status().isNotFound()).andReturn();
	}
	
	@Test
	public void updateShippingSuccessfully() throws Exception {
		ShippingInformation ShippingInformationToEdit = new ShippingInformation();
		ShippingInformationToEdit.setShippingInfoId(1);
		
		when(shipServ.upDateShippingInformation(ShippingInformationToEdit)).thenReturn(ShippingInformationToEdit);
		
		String jsonShippingInformation = objMapper.writeValueAsString(ShippingInformationToEdit);
		mockMvc.perform(put("/shipping/update", 1).content(jsonShippingInformation).contentType(MediaType.APPLICATION_JSON).header(HttpHeaders.AUTHORIZATION, jwtToken ))
				.andExpect(status().isAccepted());
//				.andExpect(status().isOk())
//				.andExpect(content().json(jsonShippingInformation))
//				.andReturn();
	}
	
	@Test
	public void updateShippingIdDoesNotMatch() throws Exception {
		ShippingInformation ShippingInformationToEdit = new ShippingInformation();
		ShippingInformationToEdit.setShippingInfoId(1);
	
	String jsonShippingInformation = objMapper.writeValueAsString(ShippingInformationToEdit);
	mockMvc.perform(put("/shipping/update", 5).content(jsonShippingInformation).contentType(MediaType.APPLICATION_JSON).header(HttpHeaders.AUTHORIZATION, jwtToken ))
			.andExpect(status().isAccepted());
//			.andExpect(status().isBadRequest())
//			.andReturn();
	}
	
	@Test
	public void getAllShippingByUser() throws Exception {
		User user = new User();
		user.setUserId(1);
		when(shipServ.getShippingInformationByUser(1)).thenReturn(Collections.emptyList());
		
		String jsonSet = objMapper.writeValueAsString(Collections.emptyList());
		
		mockMvc.perform(get("/shipping/user/{userId}", 1))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(content().json(jsonSet))
			.andReturn();
		
	}
	
	@Test
	public void addShippingSuccessfully() throws Exception {
		ShippingInformation newShippingInformation = new ShippingInformation();
		when(shipServ.addShippingInformation(newShippingInformation)).thenReturn(1);
		
		String jsonShippingInformation = objMapper.writeValueAsString(newShippingInformation);
		
		mockMvc.perform(post("/shipping/add").content(jsonShippingInformation).contentType(MediaType.APPLICATION_JSON).header(HttpHeaders.AUTHORIZATION, jwtToken ))
			.andExpect(status().isCreated())
			.andReturn();
	}
	
	@Test
	public void addShippingUnsuccessfully() throws Exception {
		String jsonShippingInformation = objMapper.writeValueAsString(null);
		
		mockMvc.perform(post("/shipping/add").content(jsonShippingInformation).contentType(MediaType.APPLICATION_JSON).header(HttpHeaders.AUTHORIZATION, jwtToken ))
		.andExpect(status().isBadRequest())
		.andReturn();
	}
	
	@Test
	public void deleteShipping() throws Exception {
		ShippingInformation mockShippingInformation = new ShippingInformation();
		mockShippingInformation.setShippingInfoId(1);
		
		when(shipServ.addShippingInformation(mockShippingInformation)).thenReturn(1);
		
		String jsonShippingInformation = objMapper.writeValueAsString(mockShippingInformation);
		mockMvc.perform(put("/shipping/{shippingId}", 1).content(jsonShippingInformation).contentType(MediaType.APPLICATION_JSON).header(HttpHeaders.AUTHORIZATION, jwtToken ))
				.andExpect(status().isNotFound())
				.andReturn();
	}

}
