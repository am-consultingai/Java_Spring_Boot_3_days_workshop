package org.kahoot.builder.repository;

import org.kahoot.builder.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

// Spring Data JPA repository for User entity
public interface UserRepository extends JpaRepository<User, Long> {
    
    // Derived query method - find by username
    Optional<User> findByUsername(String username);
    
    // Derived query method - find by email
    Optional<User> findByEmail(String email);
    
    // Derived query method - check if username exists
    boolean existsByUsername(String username);
    
    // Derived query method - check if email exists
    boolean existsByEmail(String email);
}