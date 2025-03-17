package com.ecommerce.ms.unit.user;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.ecommerce.ms.dto.RegisterRequest;
import com.ecommerce.ms.users.UserController;
import com.ecommerce.ms.users.UserService;
import com.ecommerce.ms.users.Users;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

class UserControllerTest {

    private MockMvc mockMvc;

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    void testRegisterUser() throws Exception {
        RegisterRequest request = new RegisterRequest(
            "Anna K", "anna@example.com", "password123", "Address1", "1234567890", "USER"
        );

        // Convert RegisterRequest to Users
        Users mockUser = new Users(
            request.getName(),
            request.getEmail(),
            request.getPassword(),
            request.getAddress(),
            request.getPhoneNo(),
            request.getRole()
        );

        // Mock the userService to return a successful response
        when(userService.registerUser(any(Users.class)))
        .thenReturn(mockUser);

        mockMvc.perform(post("/v1/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))) // Send RegisterRequest as JSON
                .andExpect(status().isOk())
                .andExpect(content().string("User registered successfully!"));

        verify(userService).registerUser(any(Users.class));
    }


    @Test
    void testLogin() throws Exception {
        Users mockUser = new Users("Anna K", "anna@example.com", "password123", "Address1", "1234567890", "USER");
;

        when(userService.loginUser("anna@example.com", "password123"))
                .thenReturn(mockUser);

        mockMvc.perform(post("/v1/users/login")
        		.param("name", "Anna K")
        		.param("username", "AnnaK")
                .param("email", "anna@example.com")
                .param("password", "password123"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Anna K"))
                .andExpect(jsonPath("$.email").value("anna@example.com"));

        verify(userService).loginUser("anna@example.com", "password123");
    }

    

    
    @Test
    void testLogin_UserNotFound() throws Exception {
        when(userService.loginUser("invalid@example.com", "wrongPass"))
                .thenThrow(new IllegalArgumentException("Invalid email or password"));

        mockMvc.perform(post("/v1/users/login")
                .param("email", "invalid@example.com")
                .param("password", "wrongPass")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)) // Ensure correct content type
                .andExpect(status().isBadRequest()) // Expecting 400 Bad Request
                .andExpect(content().string("Invalid email or password"));

        verify(userService).loginUser("invalid@example.com", "wrongPass");
    }

}

