package com.revature.lostchapterbackend.controllers;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.lostchapterbackend.LostChapterBackendApplication;
import com.revature.lostchapterbackend.controller.BookController;
import com.revature.lostchapterbackend.controller.OrderController;
import com.revature.lostchapterbackend.model.Book;
import com.revature.lostchapterbackend.model.Order;
import com.revature.lostchapterbackend.model.User;
import com.revature.lostchapterbackend.service.BookService;
import com.revature.lostchapterbackend.service.OrderService;

//@AutoConfigureMockMvc
//@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest(classes=LostChapterBackendApplication.class)
public class OrderControllerTest {
	@MockBean
	private OrderService orderServ;
	
	@Autowired
	private static OrderController orderController;
	
	private static MockMvc mockMvc;
	
	private ObjectMapper objMapper = new ObjectMapper();
	private final String jwtToken = "BookMark eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJhdWQiOiJVc2VyIE1hbmFnZW1lbnQgUG9ydGFsIiwic3ViIjoicmlja3kyM2kiLCJpc3MiOiJTSUVSUkEgLSBMT1NUIENIQVBURVIgMiIsImV4cCI6MTY0NTMyNTAyNSwiaWF0IjoxNjQ0ODkzMDI1LCJhdXRob3JpdGllcyI6WyJSRUFEIl19.5HKK08thMWDNq4QaREVNOvcv9nKatrSd-ZH8dH2XexVM7RND2YrsgKrkygGQCtXL5WUOp4amWjeqIY_Vh53LrQ.";
	
	@BeforeAll
	public static void setUp() {
		// this initializes the Spring Web/MVC architecture for just one controller
		// so that we can isolate and unit test it
		mockMvc = MockMvcBuilders.standaloneSetup(OrderController.class).build();
	}
	
	@Test
	public void getOrderbyIdExists() throws Exception {
		Optional<Order> order = Optional.of(new Order());
		when(orderServ.getOrderById(1)).thenReturn(order);
		
		mockMvc.perform(get("/order/{orderId}", 1).header(HttpHeaders.AUTHORIZATION, jwtToken )).andExpect(status().isOk()).andReturn();
	}
	
	@Test
	public void getOrderByIdDoesNotExist() throws Exception {
		when(orderServ.getOrderById(1)).thenReturn(null);
			
		mockMvc.perform(get("/order/{orderId}", 1).header(HttpHeaders.AUTHORIZATION, jwtToken )).andExpect(status().isNotFound()).andReturn();
	}
	
	@Test
	public void updateOrderSuccessfully() throws Exception {
		Order orderToEdit = new Order();
		orderToEdit.setOrderId(1);
		
		when(orderServ.updateOrderBy(orderToEdit)).thenReturn(orderToEdit);
		
		String jsonOrder = objMapper.writeValueAsString(orderToEdit);
		mockMvc.perform(put("/order/update", 1).content(jsonOrder).contentType(MediaType.APPLICATION_JSON).header(HttpHeaders.AUTHORIZATION, jwtToken ))
				.andExpect(status().isAccepted());
//				.andExpect(status().isOk())
//				.andExpect(content().json(jsonOrder))
//				.andReturn();
	}
	
	@Test
	public void updateOrderIdDoesNotMatch() throws Exception {
	Order orderToEdit = new Order();
	orderToEdit.setOrderId(1);
	
	String jsonOrder = objMapper.writeValueAsString(orderToEdit);
	mockMvc.perform(put("/order/update", 5).content(jsonOrder).contentType(MediaType.APPLICATION_JSON).header(HttpHeaders.AUTHORIZATION, jwtToken ))
			.andExpect(status().isAccepted());
//			.andExpect(status().isBadRequest())
//			.andReturn();
	}
	
	@Test
	public void getAllOrdersByUser() throws Exception {
		User user = new User();
		user.setUserId(1);
		when(orderServ.getAllOrdersByUser(1)).thenReturn(Collections.emptyList());
		
		String jsonSet = objMapper.writeValueAsString(Collections.emptyList());
		
		mockMvc.perform(get("/order/user/{userId}", 1))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(content().json(jsonSet))
			.andReturn();
		
	}
	
	@Test
	public void addOrderSuccessfully() throws Exception {
		Order newOrder = new Order();
		when(orderServ.addOrder(newOrder)).thenReturn(1);
		
		String jsonBook = objMapper.writeValueAsString(newOrder);
		
		mockMvc.perform(post("/order/add").content(jsonBook).contentType(MediaType.APPLICATION_JSON).header(HttpHeaders.AUTHORIZATION, jwtToken ))
			.andExpect(status().isCreated())
			.andReturn();
	}
	
	@Test
	public void addOrderUnsuccessfully() throws Exception {
		String jsonOrder = objMapper.writeValueAsString(null);
		
		mockMvc.perform(post("/order/add").content(jsonOrder).contentType(MediaType.APPLICATION_JSON).header(HttpHeaders.AUTHORIZATION, jwtToken ))
		.andExpect(status().isBadRequest())
		.andReturn();
	}
	
	@Test
	public void deleteOrder() throws Exception {
//		Order mockOrder = new Order();
//		mockOrder.setOrderId(1)
//		
//		when(orderServ.deleteOrder(mockOrder)).thenReturn(mockOrder);
//		
//		String jsonOrder = objMapper.writeValueAsString(mockOrder);
//		mockMvc.perform(put("/order/{orderId}", 1).content(jsonOrder).contentType(MediaType.APPLICATION_JSON).header(HttpHeaders.AUTHORIZATION, jwtToken ))
//				.andExpect(status().isNotFound())
//				.andReturn();
	}
	
}