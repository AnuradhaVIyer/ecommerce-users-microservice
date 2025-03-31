package com.ecommerce.ms.integration.user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.ecommerce.ms.users.UserRepository;
import com.ecommerce.ms.users.Users;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class UserRepositoryIT {

    @Autowired
    private UserRepository userRepository;

    @Test
    void shouldSaveAndFindUserByEmail() {
        // Arrange - Create and save a new user
        Users user = new Users("test user", "test@example.com", "password123", "Address1", "1234567890", "USER");
        userRepository.save(user);

        // Act - Retrieve the user by email
        Optional<Users> foundUser = userRepository.findByEmail("test@example.com");

        // Assert - Verify the user is found and correct
        assertThat(foundUser).isPresent();
        assertThat(foundUser.get().getName()).isEqualTo("test user");
        assertThat(foundUser.get().getEmail()).isEqualTo("test@example.com");
    }

    @Test
    void shouldReturnEmptyWhenUserNotFound() {
        // Act - Try to find a non-existent user
        Optional<Users> foundUser = userRepository.findByEmail("unknown");

        // Assert - Ensure the user is not found
        assertThat(foundUser).isEmpty();
    }

    @Test
    void shouldDeleteUser() {
        // Arrange
        Users user = new Users("Anna K", "anna@example.com", "password123", "Address1", "1234567890" , "USER"  );
        Users savedUser = userRepository.save(user);

        // Act
        userRepository.deleteById(savedUser.getUserId());

        // Assert
        Optional<Users> deletedUser = userRepository.findById(savedUser.getUserId());
        assertThat(deletedUser).isEmpty();
    }
}


