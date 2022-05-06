package com.revature.lostchapterbackend.services;
//
//import static org.mockito.Mockito.mock;
//
//import java.security.NoSuchAlgorithmException;
//import java.time.LocalDate;
//
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;
//
//import com.revature.lostchapterbackend.dao.UserDao;
//import com.revature.lostchapterbackend.dto.SignUpDto;
//import com.revature.lostchapterbackend.exceptions.InvalidLoginException;
//import com.revature.lostchapterbackend.exceptions.InvalidParameterException;
//import com.revature.lostchapterbackend.exceptions.UserNotFoundException;
//import com.revature.lostchapterbackend.model.Carts;
//import com.revature.lostchapterbackend.model.Users;
//import com.revature.lostchapterbackend.service.UserService;
//import com.revature.lostchapterbackend.utility.HashUtil;
//
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.security.NoSuchAlgorithmException;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.revature.lostchapterbackend.LostChapterBackendApplication;
import com.revature.lostchapterbackend.dao.UserDAO;
import com.revature.lostchapterbackend.exceptions.InvalidLoginException;
import com.revature.lostchapterbackend.exceptions.UserNotFoundException;
import com.revature.lostchapterbackend.exceptions.UsernameAlreadyExists;
import com.revature.lostchapterbackend.model.User;
import com.revature.lostchapterbackend.service.UserService;

@SpringBootTest(classes=LostChapterBackendApplication.class)
public class UserServiceTest {
	@MockBean
	private UserDAO userDao;
	
	@Autowired
	private UserService userServ;
	
	@Test
	public void registerPersonSuccessfully() throws UsernameAlreadyExists {
		User user = new User ();
		user.setUserId(10);
		
		when (userDao.save(user)).thenReturn(user);
		User actualUser = userServ.register(user);
		assertEquals(10, actualUser.getUserId());
	}
	
	@Test
	public void registerPersonSomethingWrong() throws UsernameAlreadyExists {
		User user = new User ();
		when(userDao.save(user)).thenThrow(new RuntimeException());
		User actualUser = userServ.register(user);
		assertNull(actualUser);
		
	}
	
	@Test
	public void registerPersonUsernameAlreadyExists() {
		User user = new User ();
		when(userDao.save(user)).thenThrow(new RuntimeException("unique constraint violation"));
		assertThrows(UsernameAlreadyExists.class, () ->{
			userServ.register(user);
		});
	}
	
	@Test
	public void logInSuccesfully() throws UserNotFoundException, InvalidLoginException {
		// input setup
		String username="qwertyuiop";
		String password="pass";
		
		//set up mocking
		User mockUser = new User();
		mockUser.setUsername(username);
		mockUser.setPassword(password);
		when(userDao.findByUsername(username)).thenReturn(mockUser);
		
		//call method we're testing
		User actualUser = userServ.login(username, password);
		
		//assert expected behavior/output
		assertEquals(mockUser,actualUser);
	}
	
	@Test
	public void logInIncorrectPassword() {
		String username="qwertyuiop";
		String password="12345";
		
		User mockUser = new User();
		mockUser.setUsername(username);
		mockUser.setPassword("pass");
		when(userDao.findByUsername(username)).thenReturn(mockUser);
		
		assertThrows(InvalidLoginException.class, () -> {
			userServ.login(username, password);
		});
	}
	
	@Test
	public void logInUsernameDoesNotExist() {
		String username="qwertyuiop";
		String password="pass";
		
		when(userDao.findByUsername(username)).thenReturn(null);
		
		assertThrows(UserNotFoundException.class, () -> {
			userServ.login(username, password);
		});
	}
	
	@Test
	public void getByIdUserExists() throws UserNotFoundException {
		User user = new User();
		user.setUserId(2);
		
		when(userDao.findById(2)).thenReturn(Optional.of(user));
		
		User actualUser = userServ.getUserById(2);
		assertEquals(user, actualUser);
	}
	
	@Test
	public void getByIdUserDoesNotExist() throws UserNotFoundException {
		when(userDao.findById(2)).thenReturn(Optional.empty());
		
		User actualUser = userServ.getUserById(2);
		assertNull(actualUser);
	}
	
	@Test
	public void getByEmailUserExists() throws UserNotFoundException {
		User user = new User();
		user.setEmail("fakeemail.com");
		
		when(userDao.findByEmail("fakeemail.com")).thenReturn(user);
		User actualUser = userServ.getUserByEmail("fakeemail.com");
		assertEquals(user, actualUser);
		
	}
	
	@Test
	public void getByEmailUserDoesNotExist() throws UserNotFoundException {
		when(userDao.findByEmail("fakeemail.com")).thenReturn(null);
		
		User actualUser = userServ.getUserByEmail("fakeemail.com");
		assertNull(actualUser);
	}
	
	@Test
	public void getByUsernameExists() throws UserNotFoundException {
		User user = new User();
		user.setUsername("lost_chapter");
		
		when(userDao.findByUsername("lost_chapter")).thenReturn(user);
		User actualUser = userServ.getUserByUsername("lost_chapter");
		assertEquals(user, actualUser);
	}
	
	@Test
	public void getByUsernameDoesNotExist () throws UserNotFoundException {
		when(userDao.findByUsername("lost_chapter")).thenReturn(null);
		
		User actualUser = userServ.getUserByUsername("lost_chapter");
		assertNull(actualUser);
		
	}
	
	@Test
	public void updateSuccessfully() throws UserNotFoundException {
		User mockUser = new User();
		mockUser.setUserId(1);
		
		when(userDao.existsById(1)).thenReturn(true);
		when(userDao.save(Mockito.any(User.class))).thenReturn(mockUser);
		when(userDao.findById(1)).thenReturn(Optional.of(mockUser));
		
		User updatedUser = userServ.update(mockUser);
		assertNotNull(updatedUser);
	}
	
	@Test
	public void updateSomethingWrong() throws UserNotFoundException {
		User mockUser = new User();
		mockUser.setUserId(1);
		
		when(userDao.existsById(1)).thenReturn(false);
		User updatedUser = userServ.update(mockUser);
		assertNull(updatedUser);
	}
	
	@Test
	public void deleteUserSuccessfully() throws UserNotFoundException {
		User mockUser = new User();
		mockUser.setUserId(1);
		
		when(userDao.existsById(1)).thenReturn(true);
		when(userDao.save(Mockito.any(User.class))).thenReturn(mockUser);
		when(userDao.findById(1)).thenReturn(Optional.of(mockUser));
		
		User deletedUser = userServ.deleteUser(mockUser);
		assertNotNull(deletedUser);
	}
	
	@Test
	public void passwordHasherSuccessful() throws NoSuchAlgorithmException {
		String algorithm = new String();
		String hashedPassword = new String();
		
		algorithm = "SHA-256";
		hashedPassword = "BBD07C4FC02C99B97124FEBF42C7B63B5011C0DF28D409FBB486B5A9D2E615EA";
		
		String actualPassword = userServ.passwordHasher(algorithm);
		assertEquals(hashedPassword, actualPassword);
		
		

	}
	
}
//
//	private UserService us;
//
//	private UserDao ud;
//	
//	@BeforeEach
//	public void setUp() {
//		this.ud = mock(UserDao.class);
//		this.us = new UserService(ud);
//	}
//	
//	@Test
//	public void testCreateUser_positive() throws NoSuchAlgorithmException, InvalidLoginException, InvalidParameterException {
//		LocalDate dt = LocalDate.parse("2000-11-01");
//		Users user = new Users("JDoe", "password1", "John", "Doe",  "jdoe@gmail.com", dt, "22nd Ave", "Customer");
//		user.setId(1);
//		
//		SignUpDto createdUser = new SignUpDto("JDoe", "password1", "John", "Doe",  "jdoe@gmail.com", dt, "22nd Ave", "Customer");
//		Carts c = null;
//		Mockito.when(ud.addUser(createdUser, c)).thenReturn(user);
//		
//		Users actual = us.createUser(createdUser);
//		
//		Users expected = us.createUser(new SignUpDto("JDoe", "password1", "John", "Doe",  "jdoe@gmail.com", dt, "22nd Ave", "Customer"));
//		expected.setId(1);
//		
//		Assertions.assertEquals(expected, actual);
//	}
//	
//	@Test
//	public void testCreateUserBlankInput_Username_negative1() {
//		LocalDate dt = LocalDate.parse("2000-11-01");
//		SignUpDto createdUser = new SignUpDto("  ", "password1", "John", "Doe", "jdoe@gmail.com", dt, "22nd Ave", "Customer");
//		
//		Assertions.assertThrows(InvalidParameterException.class, () -> {
//			us.createUser(createdUser);
//		});
//	}
//	
//	@Test
//	public void testCreateUserBlankInput_Password_negative2() {
//		LocalDate dt = LocalDate.parse("2000-11-01");
//		SignUpDto createdUser = new SignUpDto("JDoe", "  ", "John", "Doe",  "jdoe@gmail.com", dt, "22nd Ave", "Customer");
//		
//		Assertions.assertThrows(InvalidParameterException.class, () -> {
//			us.createUser(createdUser);
//		});
//	}
//	
//	@Test
//	public void testCreateUserBlankInput_FirstName_negative3() {
//		LocalDate dt = LocalDate.parse("2000-11-01");
//		SignUpDto createdUser = new SignUpDto("JDoe", "password1", "  ", "Doe",  "jdoe@gmail.com", dt, "22nd Ave", "Customer");
//		
//		Assertions.assertThrows(InvalidParameterException.class, () -> {
//			us.createUser(createdUser);
//		});
//	}
//	
//	@Test
//	public void testCreateUserBlankInput_LastName_negative4() {
//		LocalDate dt = LocalDate.parse("01/1/1997");
//		SignUpDto createdUser = new SignUpDto("JDoe", "password1", "John", "  ",  "jdoe@gmail.com", dt, "22nd Ave", "Customer");
//		
//		Assertions.assertThrows(InvalidParameterException.class, () -> {
//			us.createUser(createdUser);
//		});
//	}
//	
//	@Test
//	public void testCreateUserBlankInput_UnderAgeRange_negative5() {
//		LocalDate dt = LocalDate.parse("01/1/1997");
//		SignUpDto createdUser = new SignUpDto("JDoe", "password1", "John", "Doe",  "jdoe@gmail.com", dt, "22nd Ave", "Customer");
//		
//		Assertions.assertThrows(InvalidParameterException.class, () -> {
//			us.createUser(createdUser);
//		});
//	}
//	
//	@Test
//	public void testCreateUserBlankInput_OverAgeRange_negative6() {
//		LocalDate dt = LocalDate.parse("01/1/1997");
//		SignUpDto createdUser = new SignUpDto("JDoe", "password1", "John", "Doe",  "jdoe@gmail.com", dt, "22nd Ave", "Customer");
//		
//		Assertions.assertThrows(InvalidParameterException.class, () -> {
//			us.createUser(createdUser);
//		});
//	}
//	
//	@Test
//	public void testCreateUserBlankInput_Email_negative7() {
//		LocalDate dt = LocalDate.parse("01/1/1997");
//		SignUpDto createdUser = new SignUpDto("JDoe", "password1", "John", "Doe",  "  ", dt, "22nd Ave", "Customer");
//		
//		Assertions.assertThrows(InvalidParameterException.class, () -> {
//			us.createUser(createdUser);
//		});
//	}
//	
//	@Test
//	public void testCreateUserBlankInput_BirthDay_negative8() {
//		LocalDate dt = LocalDate.parse("  ");
//		SignUpDto createdUser = new SignUpDto("JDoe", "password1", "John", "Doe",  "jdoe@gmail.com", dt, "22nd Ave", "Customer");
//		
//		Assertions.assertThrows(InvalidParameterException.class, () -> {
//			us.createUser(createdUser);
//		});
//	}
//	
//	@Test
//	public void testCreateUserBlankInput_Address_negative9() {
//		LocalDate dt = LocalDate.parse("01/1/1997");
//		SignUpDto createdUser = new SignUpDto("JDoe", "password1", "John", "Doe",  "jdoe@gmail.com", dt, "  ", "Customer");
//		
//		Assertions.assertThrows(InvalidParameterException.class, () -> {
//			us.createUser(createdUser);
//		});
//	}
//	
//	@Test
//	public void testCreateUserBlankInput_Role_negative10() {
//		LocalDate dt = LocalDate.parse("01/1/1997");
//		SignUpDto createdUser = new SignUpDto("JDoe", "password1", "John", "Doe",  "jdoe@gmail.com", dt, "22nd Ave", "  ");
//		
//		Assertions.assertThrows(InvalidParameterException.class, () -> {
//			us.createUser(createdUser);
//		});
//	}
//	
//	@Test
//	public void testCreateUserUsernameHasMoreThan255Characters_negative() {
//		LocalDate dt = LocalDate.parse("01/1/1997");
//		SignUpDto createdUser = new SignUpDto("JDoeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee"
//				+ "eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee"
//				+ "eeeeeeeeeeeeeeeeeeeeeeeeeeeee", "password1", "John", "Doe",  "jdoe@gmail.com", dt, "22nd Ave", "  ");
//		
//		Assertions.assertThrows(InvalidParameterException.class, () -> {
//			us.createUser(createdUser);
//		});
//	}
//	
//	@Test
//	public void testCreateUserFirstNameHasMoreThan255Characters_negative() {
//		LocalDate dt = LocalDate.parse("01/1/1997");
//		SignUpDto createdUser = new SignUpDto("JDoe", "password1", "Johnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnn"
//				+ "nnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnn"
//				+ "nnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnn", "Doe",  "jdoe@gmail.com", dt, "22nd Ave", "  ");
//		
//		Assertions.assertThrows(InvalidParameterException.class, () -> {
//			us.createUser(createdUser);
//		});
//	}
//	
//	@Test
//	public void testCreateUserLastNameHasMoreThan255Characters_negative() {
//		LocalDate dt = LocalDate.parse("01/1/1997");
//		SignUpDto createdUser = new SignUpDto("JDoe", "password1", "John", "Doeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee"
//				+ "eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee"
//				+ "eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee", "jdoe@gmail.com", dt, "22nd Ave", "  ");
//		
//		Assertions.assertThrows(InvalidParameterException.class, () -> {
//			us.createUser(createdUser);
//		});
//	}
//	
//	@Test
//	public void testGetUser_positive() throws NoSuchAlgorithmException, InvalidLoginException {
//		LocalDate dt = LocalDate.parse("01/1/1997");
//		Users user = new Users("JDoe", "password1", "John", "Doe",  "jdoe@gmail.com", dt, "22nd Ave", "Customer");
//		user.setId(1);
//		user.setPassword(HashUtil.hashPassword("password1", "SHA-256"));
//		
//		Mockito.when(ud.getUser("JDoe")).thenReturn(user);
//		
//		Users actual = us.getUser("JDoe", "password1");
//		actual.setId(1);
//		actual.setPassword(HashUtil.hashInputPassword("password1", "SHA-256"));
//		
//		Users expected = new Users("JDoe", "password1", "John", "Doe",  "jdoe@gmail.com", dt, "22nd Ave", "Customer");
//		expected.setId(1);
//		expected.setPassword(HashUtil.hashPassword("password1", "SHA-256"));
//		
//		Assertions.assertEquals(expected, actual);
//	}
//	
//	@Test
//	public void testDeleteUserByID_positive() throws UserNotFoundException {
//		LocalDate dt = LocalDate.parse("01/1/1997");
//		Users user = new Users("JDoe", "password1", "John", "Doe",  "jdoe@gmail.com", dt, "22nd Ave", "Customer");
//		user.setId(1);
//		
//		us.deleteUserById(user);
//	}
//	
//	@Test
//	public void testDeleteUserByID_IDIsNull_negative() throws UserNotFoundException {
//		LocalDate dt = LocalDate.parse("2000-11-01");
//		Assertions.assertThrows(UserNotFoundException.class, () -> {
//			us.deleteUserById(null);
//		});
//	}
//	
//	@Test
//	public void testGetUserByEmail_positive() throws InvalidParameterException {
//		LocalDate dt = LocalDate.parse("01/1/1997");
//		Users user = new Users("JDoe", "password1", "John", "Doe",  "jdoe@gmail.com", dt, "22nd Ave", "Customer");
//		user.setId(1);
//		
//		Mockito.when(ud.getUserByEmail("jdoe@gmail.com")).thenReturn(user);
//		
//		Users actual = us.getUserByEmail("jdoe@gmail.com");
//		
//		Users expected = new Users("JDoe", "password1", "John", "Doe",  "jdoe@gmail.com", dt, "22nd Ave", "Customer");
//		expected.setId(1);
//		
//		Assertions.assertEquals(expected, actual);
//	}
//	
//	@Test
//	public void testGetUserByEmail_EmailIsNull_negative() {
//		LocalDate dt = LocalDate.parse("01/1/1997");
//		Assertions.assertThrows(InvalidParameterException.class, () -> {
//			us.getUserByEmail(null);
//		});
//	}
//	
//	@Test
//	public void testUpdateUser_ChangeName_positive() throws InvalidParameterException {
//		LocalDate dt = LocalDate.parse("01/1/1997");
//		Users user1 = new Users("JDoe", "password1", "John", "Doe",  "jdoe@gmail.com", dt, "22nd Ave", "Customer");
//		user1.setId(1);
//		
//		Users user2 = new Users("JDoe", "password1", "Jane", "Doe",  "jdoe@gmail.com", dt, "22nd Ave", "Customer");
//		user2.setId(1);
//		
//		Mockito.when(ud.updateUser(1, user2)).thenReturn(user2);
//		
//		Users actual = us.updateUser(user1, user2);
//		
//		Users expected = new Users("JDoe", "password1", "Jane", "Doe", "jdoe@gmail.com", dt, "22nd Ave", "Customer");
//		expected.setId(1);
//		
//		Assertions.assertEquals(expected, actual);
//	}
//	
//	@Test
//	public void testUpdateUserCannotChange_Username_positive() throws InvalidParameterException {
//		LocalDate dt = LocalDate.parse("2000-11-01");
//		Users user1 = new Users("JDoe", "password1", "John", "Doe",  "jdoe@gmail.com", dt, "22nd Ave", "Customer");
//		user1.setId(1);
//		
//		Users user2 = new Users("JaneD", "password1", "John", "Doe",  "jdoe@gmail.com", dt, "22nd Ave", "Customer");
//		user2.setId(1);
//		
//		Mockito.when(ud.updateUser(1, user2)).thenReturn(user1);
//		
//		Users actual = us.updateUser(user1, user2);
//		
//		Users expected = new Users("JDoe", "password1", "John", "Doe", "jdoe@gmail.com", dt, "22nd Ave", "Customer");
//		expected.setId(1);
//		
//		Assertions.assertEquals(expected, actual);
//	}
//	
//	@Test
//	public void testUpdateUserCannotChange_Password_positive() throws InvalidParameterException {
//		LocalDate dt = LocalDate.parse("01/1/1997");
//		Users user1 = new Users("JDoe", "password1", "John", "Doe",  "jdoe@gmail.com", dt, "22nd Ave", "Customer");
//		user1.setId(1);
//		
//		Users user2 = new Users("JDoe", "pass", "John", "Doe",  "jdoe@gmail.com", dt, "22nd Ave", "Customer");
//		user2.setId(1);
//		
//		Mockito.when(ud.updateUser(1, user2)).thenReturn(user1);
//		
//		Users actual = us.updateUser(user1, user2);
//		
//		Users expected = new Users("JDoe", "password1", "John", "Doe",  "jdoe@gmail.com", dt, "22nd Ave", "Customer");
//		expected.setId(1);
//		
//		Assertions.assertEquals(expected, actual);
//	}
//	
//	@Test
//	public void testUpdateUserCannotChange_Role_positive() throws InvalidParameterException {
//		LocalDate dt = LocalDate.parse("01/1/1997");
//		Users user1 = new Users("JDoe", "password1", "John", "Doe",  "jdoe@gmail.com", dt, "22nd Ave", "Customer");
//		user1.setId(1);
//		
//		Users user2 = new Users("JDoe", "password1", "John", "Doe",  "jdoe@gmail.com", dt, "22nd Ave", "Admin");
//		user2.setId(1);
//		
//		Mockito.when(ud.updateUser(1, user2)).thenReturn(user1);
//		
//		Users actual = us.updateUser(user1, user2);
//		
//		Users expected = new Users("JDoe", "password1", "John", "Doe",  "jdoe@gmail.com", dt, "22nd Ave", "Customer");
//		expected.setId(1);
//		
//		Assertions.assertEquals(expected, actual);
//	}
//	
//	@Test
//	public void testUpdateUserBlankInput_FirstName_negative() throws InvalidParameterException {
//		LocalDate dt = LocalDate.parse("01/1/1997");
//		Users user1 = new Users("JDoe", "password1", "John", "Doe",  "jdoe@gmail.com", dt, "22nd Ave", "Customer");
//		user1.setId(1);
//		
//		Users user2 = new Users("JDoe", "password1", "   ", "Doe",  "jdoe@gmail.com", dt, "22nd Ave", "Customer");
//		user2.setId(1);
//		
//		Assertions.assertThrows(InvalidParameterException.class, () -> {
//			us.updateUser(user1, user2);
//		});
//	}
//	
//	@Test
//	public void testUpdateUserBlankInput_LastName_negative() throws InvalidParameterException {
//		LocalDate dt = LocalDate.parse("01/1/1997");
//		Users user1 = new Users("JDoe", "password1", "John", "Doe",  "jdoe@gmail.com", dt, "22nd Ave", "Customer");
//		user1.setId(1);
//		
//		Users user2 = new Users("JDoe", "password1", "John", "  ", "jdoe@gmail.com", dt, "22nd Ave", "Customer");
//		user2.setId(1);
//		
//		Assertions.assertThrows(InvalidParameterException.class, () -> {
//			us.updateUser(user1, user2);
//		});
//	}
//	
//	@Test
//	public void testUpdateUser_BellowAgeRange_negative() throws InvalidParameterException {
//		LocalDate dt = LocalDate.parse("01/1/1997");
//		Users user1 = new Users("JDoe", "password1", "John", "Doe",  "jdoe@gmail.com", dt, "22nd Ave", "Customer");
//		user1.setId(1);
//		
//		Users user2 = new Users("JDoe", "password1", "John", "Doe",  "jdoe@gmail.com", dt, "22nd Ave", "Customer");
//		user2.setId(1);
//		
//		Assertions.assertThrows(InvalidParameterException.class, () -> {
//			us.updateUser(user1, user2);
//		});
//	}
//	
//	@Test
//	public void testUpdateUser_AboveAgeRange_negative() throws InvalidParameterException {
//		LocalDate dt = LocalDate.parse("01/1/1997");
//		Users user1 = new Users("JDoe", "password1", "John", "Doe",  "jdoe@gmail.com", dt, "22nd Ave", "Customer");
//		user1.setId(1);
//		
//		Users user2 = new Users("JDoe", "password1", "John", "Doe",  "jdoe@gmail.com", dt, "22nd Ave", "Customer");
//		user2.setId(1);
//		
//		Assertions.assertThrows(InvalidParameterException.class, () -> {
//			us.updateUser(user1, user2);
//		});
//	}
//	
//	@Test
//	public void testUpdateUserBlankInput_Email_negative() throws InvalidParameterException {
//		LocalDate dt = LocalDate.parse("01/1/1997");
//		Users user1 = new Users("JDoe", "password1", "John", "Doe",  "jdoe@gmail.com", dt, "22nd Ave", "Customer");
//		user1.setId(1);
//		
//		Users user2 = new Users("JDoe", "password1", "John", "Doe",  "  ", dt, "22nd Ave", "Customer");
//		user2.setId(1);
//		
//		Assertions.assertThrows(InvalidParameterException.class, () -> {
//			us.updateUser(user1, user2);
//		});
//	}
//	
//	@Test
//	public void testUpdateUserBlankInput_BirthDay_negative() throws InvalidParameterException {
//		LocalDate dt = LocalDate.parse("01/1/1997");
//		Users user1 = new Users("JDoe", "password1", "John", "Doe",  "jdoe@gmail.com", dt, "22nd Ave", "Customer");
//		user1.setId(1);
//		
//		Users user2 = new Users("JDoe", "password1", "John", "Doe",  "jdoe@gmail.com", dt, "22nd Ave", "Customer");
//		user2.setId(1);
//		
//		Assertions.assertThrows(InvalidParameterException.class, () -> {
//			us.updateUser(user1, user2);
//		});
//	}
//	
//	@Test
//	public void testUpdateUserBlankInput_Address_negative() throws InvalidParameterException {
//		LocalDate dt = LocalDate.parse("01/1/1997");
//		Users user1 = new Users("JDoe", "password1", "John", "Doe",  "jdoe@gmail.com", dt, "22nd Ave", "Customer");
//		user1.setId(1);
//		
//		Users user2 = new Users("JDoe", "password1", "John", "Doe",  "jdoe@gmail.com", dt, "  ", "Customer");
//		user2.setId(1);
//		
//		Assertions.assertThrows(InvalidParameterException.class, () -> {
//			us.updateUser(user1, user2);
//		});
//	}
//	
//	@Test
//	public void testUpdateUserUsernameHasMoreThan255Characters_negative() throws InvalidParameterException {
//		LocalDate dt = LocalDate.parse("01/1/1997");
//		Users user1 = new Users("JDoe", "password1", "John", "Doe",  "jdoe@gmail.com", dt, "22nd Ave", "Customer");
//		user1.setId(1);
//		
//		Users user2 = new Users("JDoeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee"
//				+ "eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee"
//				+ "eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee"
//				+ "eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee"
//				+ "eeeeeeeeeeeeeeeeeeeeeeeeeeee", "password1", "John", "Doe",  "jdoe@gmail.com", dt, "22nd Ave", "Customer");
//		user2.setId(1);
//		
//		Assertions.assertThrows(InvalidParameterException.class, () -> {
//			us.updateUser(user1, user2);
//		});
//	}
//	
//	@Test
//	public void testUpdateUserFirstNameHasMoreThan255Characters_negative() throws InvalidParameterException {
//		LocalDate dt = LocalDate.parse("01/1/1997");
//		Users user1 = new Users("JDoe", "password1", "John", "Doe",  "jdoe@gmail.com", dt, "22nd Ave", "Customer");
//		user1.setId(1);
//		
//		Users user2 = new Users("JDoe", "password1", "Johnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnn"
//				+ "nnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnn"
//				+ "nnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnn"
//				+ "nnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnn", "Doe",  "jdoe@gmail.com", dt, "22nd Ave", "Customer");
//		user2.setId(1);
//		
//		Assertions.assertThrows(InvalidParameterException.class, () -> {
//			us.updateUser(user1, user2);
//		});
//	}
//	
//	@Test
//	public void testUpdateUserLastNameHasMoreThan255Characters_negative() throws InvalidParameterException {
//		LocalDate dt = LocalDate.parse("01/1/1997");
//		Users user1 = new Users("JDoe", "password1", "John", "Doe",  "jdoe@gmail.com", dt, "22nd Ave", "Customer");
//		user1.setId(1);
//		
//		Users user2 = new Users("JDoe", "password1", "John", "Doeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee"
//				+ "eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee"
//				+ "eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee"
//				+ "eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee"
//				+ "eeeeeeeeeeeeeeeeeeeeeeeeeeee",  "jdoe@gmail.com", dt, "22nd Ave", "Customer");
//		user2.setId(1);
//		
//		Assertions.assertThrows(InvalidParameterException.class, () -> {
//			us.updateUser(user1, user2);
//		});
//	}
//	
//	@Test
//	public void testGetUserByUsername_positive() throws InvalidParameterException {
//		LocalDate dt = LocalDate.parse("01/1/1997");
//		Users user = new Users("JDoe", "password1", "John", "Doe",  "jdoe@gmail.com", dt, "22nd Ave", "Customer");
//		user.setId(1);
//		
//		Mockito.when(ud.getUser("JDoe")).thenReturn(user);
//		
//		Users actual = us.getUserByUsername("JDoe");
//		
//		Users expected = new Users("JDoe", "password1", "John", "Doe",  "jdoe@gmail.com", dt, "22nd Ave", "Customer");
//		expected.setId(1);
//		
//		Assertions.assertEquals(expected, actual);
//	}
//	
//	@Test
//	public void testGetUserByUsername_negative() {
//		Assertions.assertThrows(InvalidParameterException.class, () -> {
//			us.getUserByUsername(null);
//		});
//	}
//	
//}
