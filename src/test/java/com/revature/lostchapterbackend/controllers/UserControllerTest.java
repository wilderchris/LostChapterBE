package com.revature.lostchapterbackend.controllers;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.lostchapterbackend.LostChapterBackendApplication;
import com.revature.lostchapterbackend.JWT.AccessDeniedHandler;
import com.revature.lostchapterbackend.JWT.AuthenticationEntryPoint;
import com.revature.lostchapterbackend.JWT.AuthorizationFilter;
import com.revature.lostchapterbackend.JWT.HttpResponse;
import com.revature.lostchapterbackend.JWT.SecurityConfiguration;
import com.revature.lostchapterbackend.JWT.TokenProvider;
import com.revature.lostchapterbackend.JWT.UserDetail;
import com.revature.lostchapterbackend.controller.UserController;
import com.revature.lostchapterbackend.exceptions.InvalidLoginException;
import com.revature.lostchapterbackend.exceptions.UsernameAlreadyExists;
import com.revature.lostchapterbackend.model.User;
import com.revature.lostchapterbackend.service.UserService;

@EnableWebMvc
@SpringBootTest(classes=LostChapterBackendApplication.class)
public class UserControllerTest {

	@MockBean
	private UserService userServ;
	@MockBean
	private AccessDeniedHandler accessDeniedHandler;
	@MockBean
	private AuthenticationEntryPoint authenticationEntryPoint;
	@MockBean
	private SecurityConfiguration securityConfiguration;
	@MockBean
	private TokenProvider tokenProvider;
	@MockBean
	private HttpResponse httpResponse;
	@MockBean
	private UserDetail userDetail;
	@MockBean
	private AuthenticationManager authenticationManagerBean;
	
	@Autowired
	private UserController userController;
	@Autowired
	private AuthorizationFilter authorizationFilter;
	
	private static MockMvc mockMvc;
	private ObjectMapper objMapper = new ObjectMapper();
	
	@BeforeAll
	public static void setUp() {
		// this initializes the Spring Web/MVC architecture for just one controller
		// so that we can isolate and unit test it
		mockMvc = MockMvcBuilders.standaloneSetup(UserController.class).build();
	}
	
	@Test
	public void registerSuccessfully() throws Exception {
		User newUser = new User();
		
		when(userServ.register(newUser)).thenReturn(newUser);
		Map<String,Integer> idMap = new HashMap<>();
		idMap.put("generatedId", 0);
		
		String jsonUser = objMapper.writeValueAsString(newUser);
		String jsonIdMap = objMapper.writeValueAsString(idMap);
		mockMvc.perform(post("/users").content(jsonUser).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated())
				.andExpect(content().json(jsonIdMap))
				.andReturn();
	}
	
	@Test
	public void registerUsernameAlreadyExists() throws Exception {
		User newUser = new User();
		
		when(userServ.register(newUser)).thenThrow(UsernameAlreadyExists.class);
		
		String jsonUser = objMapper.writeValueAsString(newUser);
		mockMvc.perform(post("/users").content(jsonUser).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isConflict())
				.andReturn();
	}
	
	@Test
	public void logInCorrectly() throws Exception {
		when(userServ.login("test", "test")).thenReturn(new User());
		
		String jsonCredentials = "{"
				+ "\"username\":\"test\","
				+ "\"password\":\"test\""
				+ "}";
		mockMvc
			.perform(post("/users/auth").content(jsonCredentials).contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().string("0"))
			.andReturn();
	}
	
	@Test
	public void logInIncorrectCredentials() throws Exception {
		Map<String, String> credentials = new HashMap<>();
		credentials.put("username", "test");
		credentials.put("password", "test");
		
		when(userServ.login(credentials.get("username"), credentials.get("password")))
			.thenThrow(InvalidLoginException.class);
		
		String jsonCredentials = objMapper.writeValueAsString(credentials);
		
		mockMvc
			.perform(post("/users/auth").content(jsonCredentials).contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isNotFound())
			.andReturn();
	}
	
	@Test
	public void checkLoginUserExists() throws Exception {
		when(userServ.getUserById(1)).thenReturn(new User());
		
		mockMvc.perform(get("/users/{userId}/auth", 1))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andReturn();
	}
	
	@Test
	public void checkLoginUnauthorized() throws Exception {
		when(userServ.getUserById(0)).thenReturn(null);
		
		mockMvc.perform(get("/users/{userId}/auth", 0))
			.andExpect(status().isUnauthorized())
			.andReturn();
	}
	
	@Test
	public void getUserByIdUserExists() throws Exception {
		when(userServ.getUserById(1)).thenReturn(new User());
		
		mockMvc.perform(get("/users/{userId}", 1))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andReturn();
	}
	
	@Test
	public void getUserByIdUserDoesNotExist() throws Exception {
		when(userServ.getUserById(0)).thenReturn(null);
		
		mockMvc.perform(get("/users/{userId}", 0))
			.andExpect(status().isNotFound())
			.andReturn();
	}
	
	@Test
	public void getByEmailUserExists () throws Exception {
		when(userServ.getUserByEmail("email")).thenReturn(new User());
		
		mockMvc.perform(get("/users/email/{email}", "email" ))
		.andExpect(status().isOk()).andReturn();
		
	}
	
	@Test
	public void getByEmailUserDoesNotExist() throws Exception {
		when(userServ.getUserByEmail("email")).thenReturn(null);
		mockMvc.perform(get("/users/email/{email}", "email" ))
		.andExpect(status().isNotFound()).andReturn();
	}
	
	@Test
	public void getByUsernameUserExists() throws Exception {
		when(userServ.getUserByUsername("username")).thenReturn(new User());
		
		mockMvc.perform(get("/users/username/{username}", "username" ))
		.andExpect(status().isOk()).andReturn();

	}
	
	@Test
	public void getByUsernameUserDoesNotExist() throws Exception {
		when(userServ.getUserByUsername("username")).thenReturn(null);
		
		mockMvc.perform(get("/users/username/{username}", "username" ))
		.andExpect(status().isNotFound()).andReturn();
	}
	
	@Test
	public void updateUserSuccessfully() throws Exception {
		User userToEdit = new User();
		userToEdit.setUserId(1);
		
		when(userServ.update(userToEdit)).thenReturn(userToEdit);
		
		String jsonUser = objMapper.writeValueAsString(userToEdit);
		mockMvc.perform(put("/users/{userId}", 1).content(jsonUser).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().json(jsonUser))
				.andReturn();
	}
	
	@Test
	public void updateUserIdDoesNotMatch() throws Exception {
		User userToEdit = new User();
		userToEdit.setUserId(1);
		
		String jsonUser = objMapper.writeValueAsString(userToEdit);
		mockMvc.perform(put("/users/{userId}", 5).content(jsonUser).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isConflict())
				.andReturn();
	}

	@Test
	public void updateUserIsNull() throws Exception {
		User userToEdit = null;
		
		String jsonUser = objMapper.writeValueAsString(userToEdit);
		mockMvc.perform(put("/users/{userId}", 5).content(jsonUser).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest())
				.andReturn();
	}
	
	@Test
	public void updateUserDoesNotExistInDB() throws Exception {
		User userToEdit = new User();
		userToEdit.setUserId(1);
		
		when(userServ.update(userToEdit)).thenReturn(null);
		
		String jsonUser = objMapper.writeValueAsString(userToEdit);
		mockMvc.perform(put("/users/{userId}", 1).content(jsonUser).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound())
				.andReturn();
	}
	
	@Test
	public void deleteUserSuccessfully() throws Exception {
		User mockUser = new User();
		mockUser.setUserId(1);
		
		when(userServ.deleteUser(mockUser)).thenReturn(mockUser);
		
		String jsonUser = objMapper.writeValueAsString(mockUser);
		mockMvc.perform(put("/users/{userId}", 1).content(jsonUser).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound())
				.andReturn();
		
		
		
	}

	



	



	



	
	
	
}
