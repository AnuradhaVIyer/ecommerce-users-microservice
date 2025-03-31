package com.ecommerce.ms.users;

//User Service
import org.springframework.stereotype.Service;

import com.ecommerce.ms.exception.ResourceNotFoundException;

import java.util.List;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;



@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<Users> getAllUsers() {
        return userRepository.findAll();
    }

    public Users getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }
    
    public Users getUserByName(String name) {
        return userRepository.findByName(name).orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }
    
    public Users getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    public Users saveUser(Users user) {
        return userRepository.save(user);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    
    // Registration Method
    public Users registerUser(Users user) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email already in use");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword())); // Hashing password
        return userRepository.save(user);
    }

    // Login Method
    public Users loginUser(String email, String password) {
    	Users user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password"));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new IllegalArgumentException("Invalid email or password");
        }
        return user;
    }
    
 // UserService - Admin Check
	/*
	 * public boolean isAdmin(Long userId) { return userRepository.findById(userId)
	 * .map(user -> user.getRole() == Users.Role.ADMIN) .orElse(false); }
	 */
    
    public boolean isAdmin(Long userId) {
        return userRepository.existsByUserIdAndRole(userId, Users.Role.ADMIN);
    }

}