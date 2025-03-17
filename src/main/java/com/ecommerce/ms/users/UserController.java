package com.ecommerce.ms.users;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//User Controller
import org.springframework.web.bind.annotation.*;

import com.ecommerce.ms.dto.RegisterRequest;

import jakarta.validation.Valid;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/v1/users")
public class UserController {

	private final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping
	public List<Users> getAllUsers() {
		return userService.getAllUsers();
	}

	@GetMapping("/{id}")
	public Users getUserById(@PathVariable Long id) {
		return userService.getUserById(id);
	}

	@PostMapping
	public Users createUser(@RequestBody Users user) {
		return userService.saveUser(user);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Map<String, String>> deleteUser(@PathVariable Long id) {
		userService.deleteUser(id);
		Map<String, String> response = new HashMap<String, String>();
		response.put("message", "User with ID " + id + " deleted successfully.");
		return ResponseEntity.ok(response);
	}

	@PostMapping("/register")
	public ResponseEntity<String> registerUser(@Valid @RequestBody RegisterRequest request) {
		// Convert RegisterRequest to Users entity
	    Users user = new Users(
	        request.getName(),
	        request.getEmail(),
	        request.getPassword(),
	        request.getAddress(),
	        request.getPhoneNo(),
	        request.getRole()
	    );

	    // Call service layer
	    userService.registerUser(user);
	   
	    return ResponseEntity.ok("User registered successfully!");
	}


	@PostMapping("/login")
	public ResponseEntity<?> loginUser(@RequestParam String email, @RequestParam String password) {
		try {
			Users user = userService.loginUser(email, password);
			return ResponseEntity.ok(user);
		} catch (IllegalArgumentException ex) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
		}
	}

	@GetMapping("/logout")
	public String logout() {
		return "redirect:/index";
	}
}