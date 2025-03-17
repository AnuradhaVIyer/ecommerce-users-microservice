package com.ecommerce.ms.users;

import java.util.Optional;

//User Repository
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Users, Long> {
	Optional<Users> findByName(String name);
	Optional<Users> findByEmail(String email);
	boolean existsByUserIdAndRole(Long userId, Users.Role role);
}