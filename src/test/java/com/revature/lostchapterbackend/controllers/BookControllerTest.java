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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.lostchapterbackend.LostChapterBackendApplication;
import com.revature.lostchapterbackend.controller.BookController;

import com.revature.lostchapterbackend.model.Book;
import com.revature.lostchapterbackend.model.User;
import com.revature.lostchapterbackend.service.BookService;

@SpringBootTest(classes=LostChapterBackendApplication.class)
public class BookControllerTest {
	@MockBean
	private BookService bookServ;
	
	@Autowired
	private BookController bookController;
	
	private static MockMvc mockMvc;
	private ObjectMapper objMapper = new ObjectMapper();
	private final String jwtToken = "BookMark eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJhdWQiOiJVc2VyIE1hbmFnZW1lbnQgUG9ydGFsIiwic3ViIjoicmlja3kyM2kiLCJpc3MiOiJTSUVSUkEgLSBMT1NUIENIQVBURVIgMiIsImV4cCI6MTY0NTMyNTAyNSwiaWF0IjoxNjQ0ODkzMDI1LCJhdXRob3JpdGllcyI6WyJSRUFEIl19.5HKK08thMWDNq4QaREVNOvcv9nKatrSd-ZH8dH2XexVM7RND2YrsgKrkygGQCtXL5WUOp4amWjeqIY_Vh53LrQ.";
	@BeforeAll
	public static void setUp() {
		// this initializes the Spring Web/MVC architecture for just one controller
		// so that we can isolate and unit test it
		mockMvc = MockMvcBuilders.standaloneSetup(BookController.class).build();
	}
	
	@Test
	public void getAvailableBooks() throws Exception {
		when(bookServ.getAllBooks()).thenReturn(Collections.emptyList());
		
		String jsonSet = objMapper.writeValueAsString(Collections.emptyList());
		
		mockMvc.perform(get("/book"))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(content().json(jsonSet))
			.andReturn();

	}
	
	@Test
	public void getFeaturedBooks() throws Exception {
		when(bookServ.getFeaturedBooks()).thenReturn(Collections.emptyList());
		
		String jsonSet = objMapper.writeValueAsString(Collections.emptyList());
		
		mockMvc.perform(get("/book"))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(content().json(jsonSet))
			.andReturn();

	}
	
	@Test
	public void getBookByIdIfExists() throws Exception {
		when(bookServ.getBookById(1)).thenReturn(new Book());
			
		mockMvc.perform(get("/book/{bookId}", 1)).andExpect(status().isOk()).andReturn();
		}
		
	@Test
	public void getPetByIdDoesNotExist() throws Exception {
		when(bookServ.getBookById(1)).thenReturn(null);
			
		mockMvc.perform(get("/book/{bookId}", 1)).andExpect(status().isNotFound()).andReturn();
		
		}
	
	@Test
	public void getBookByGenre () throws Exception {
		when(bookServ.getBookByGenre("fiction")).thenReturn(Collections.emptyList());
		
		String jsonSet = objMapper.writeValueAsString(Collections.emptyList());
		
		mockMvc.perform(get("/book/genre/{name}", "fiction"))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(content().json(jsonSet))
			.andReturn();
		}
	
	@Test
	public void getBookByKeyword() throws Exception {
		when(bookServ.getByKeyWord("key")).thenReturn(Collections.emptyList());
		
		String jsonSet = objMapper.writeValueAsString(Collections.emptyList());
		
		mockMvc.perform(get("/book/search/{key}", "key"))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(content().json(jsonSet))
			.andReturn();
		}
	
	@Test
	public void getBookBySale() throws Exception {
	when(bookServ.getBooksBySale()).thenReturn(Collections.emptyList());
	
	String jsonSet = objMapper.writeValueAsString(Collections.emptyList());
	
	mockMvc.perform(get("/book/books/sales"))
		.andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		.andExpect(content().json(jsonSet))
		.andReturn();
	
	}
		
	@Test
	public void addNewBookSuccessfully() throws Exception {
		Book newBook = new Book();
		when(bookServ.addBook(newBook)).thenReturn(1);
		
		String jsonBook = objMapper.writeValueAsString(newBook);
		
		mockMvc.perform(post("/book").content(jsonBook).contentType(MediaType.APPLICATION_JSON).header(HttpHeaders.AUTHORIZATION, jwtToken ))
			.andExpect(status().isCreated())
			.andReturn();
	}
	
	@Test 
	public void addNewBookWithoutBook() throws Exception {
		String jsonBook = objMapper.writeValueAsString(null);
		
		mockMvc.perform(post("/book").content(jsonBook).contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isBadRequest())
		.andReturn();
	}
	
}		
