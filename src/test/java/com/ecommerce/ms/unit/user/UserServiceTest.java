package com.ecommerce.ms.unit.user;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.ecommerce.ms.users.UserRepository;
import com.ecommerce.ms.users.UserService;
import com.ecommerce.ms.users.Users;

class UserServiceTest {

	@Mock
	private UserRepository userRepository;
	
	@Mock
	private BCryptPasswordEncoder passwordEncoder;

	@InjectMocks
	private UserService userService;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testRegisterUser() {
		Users mockUser = new Users("Anna K", "anna@example.com", "password123", "Address1", "1234567890" , "USER"  );;
		// Mock the save() method to return the same user
		when(userRepository.save(mockUser)).thenReturn(mockUser);

		// Act: Call the service method
		Users savedUser = userService.registerUser(mockUser);

		// Assert: Verify the saved user details
		assertEquals("Anna K", savedUser.getName());
		assertEquals("anna@example.com", savedUser.getEmail());
	}

	

	@Test
	void testLogin_Success() {
	    // Mock a hashed password (replace with actual hashed value from BCrypt)
	    String hashedPassword = new BCryptPasswordEncoder().encode("jane123");

	    Users mockUser = new Users("Jane Smith", "jane@ecommerce.com", hashedPassword, "789 Elm St", "5551234567", "USER");

	    when(userRepository.findByEmail("jane@ecommerce.com")).thenReturn(Optional.of(mockUser));
	    when(passwordEncoder.matches("jane123", hashedPassword)).thenReturn(true); // Mock the password match

	    // Act
	    Users loggedInUser = userService.loginUser("jane@ecommerce.com", "jane123");

	    // Assert
	    assertNotNull(loggedInUser);
	    assertEquals("jane@ecommerce.com", loggedInUser.getEmail());
	    verify(userRepository).findByEmail("jane@ecommerce.com");
	}

	@Test
	void testLogin_InvalidEmail() {
		// Arrange: No user found
		when(userRepository.findByEmail("wrong@example.com")).thenReturn(Optional.empty());

		// Act & Assert: Should throw an exception
		Exception exception = assertThrows(IllegalArgumentException.class, () -> {
			userService.loginUser("wrong@example.com", "password123");
		});

		assertEquals("Invalid email or password", exception.getMessage());
		verify(userRepository).findByEmail("wrong@example.com");
	}

	@Test
	void testLogin_InvalidPassword() {
		// Arrange: User exists but password is wrong
		Users mockUser = new Users("Anna K", "anna@example.com", "password123", "Address1", "1234567890" , "USER"  );;
		when(userRepository.findByEmail("anna@example.com")).thenReturn(Optional.of(mockUser));

		// Act & Assert: Should throw an exception
		Exception exception = assertThrows(IllegalArgumentException.class, () -> {
			userService.loginUser("anna@example.com", "wrongPassword");
		});

		assertEquals("Invalid email or password", exception.getMessage());
		verify(userRepository).findByEmail("anna@example.com");
	}

	@Test
	void testLogin_NullInputs() {
		// Act & Assert: Null email or password should throw an exception
		assertThrows(IllegalArgumentException.class, () -> userService.loginUser(null, "password123"));
		assertThrows(IllegalArgumentException.class, () -> userService.loginUser("anna@example.com", null));
	}

	@Test
	void testFindByUsername() {
		Users mockUser = new Users("Anna K", "anna@example.com", "password123", "Address1", "1234567890" , "USER"  );;

		when(userRepository.findByName("annak")).thenReturn(Optional.of(mockUser));

		Users user = userService.getUserByName("annak");

		assertEquals("Anna K", user.getName());
		assertEquals("anna@example.com", user.getEmail());

	}

	@Test
	void testFindByEmail() {
		Users mockUser = new Users("Anna K", "anna@example.com", "password123", "Address1", "1234567890", "USER"   );;

		when(userRepository.findByEmail("anna@example.com")).thenReturn(Optional.of(mockUser));

		Users user = userService.getUserByEmail("anna@example.com");

		assertEquals("Anna K", user.getName());
		assertEquals("anna@example.com", user.getEmail());
	}

}
