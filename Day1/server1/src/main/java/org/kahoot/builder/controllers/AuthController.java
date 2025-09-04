package org.kahoot.builder.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.kahoot.builder.entity.*;
import org.kahoot.builder.repository.*;
import org.kahoot.builder.dto.LoginResponse;
import org.kahoot.builder.dto.UserLogin;
import org.kahoot.builder.dto.UserRegistration;
import org.kahoot.builder.dto.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.Optional;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// REST controller for authentication endpoints
@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*") // Enable CORS for frontend
public class AuthController {

    private static final Logger log = LoggerFactory.getLogger(AuthController.class);

    // Dependency injection of repository
    @Autowired
    private UserRepository userRepository;

    @Operation(summary = "User signup", description = "Register a new user account")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User registered successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input or user already exists")
    })
    // POST endpoint for user registration with validation
    @PostMapping("/signup")
    public ResponseEntity<?> signup(@Valid @RequestBody UserRegistration registration) {
        log.info("📥 REQUEST: POST /api/auth/signup - User signup: username='{}', email='{}'", 
                registration.getUsername(), registration.getEmail());
        try {
            // Check if username already exists
            if (userRepository.existsByUsername(registration.getUsername())) {
                log.info("❌ Username already exists: {}", registration.getUsername());
                return ResponseEntity.badRequest()
                    .body(new ApiError("Username already exists"));
            }

            // Check if email already exists
            if (userRepository.existsByEmail(registration.getEmail())) {
                log.info("❌ Email already exists: {}", registration.getEmail());
                return ResponseEntity.badRequest()
                    .body(new ApiError("Email already exists"));
            }

            // Create new user
            User newUser = new User(
                registration.getUsername(),
                registration.getEmail(),
                registration.getPassword(), // In production, this should be hashed
                registration.getFirstName(),
                registration.getLastName()
            );

            User savedUser = userRepository.save(newUser);
            log.info("💾 User saved: {}", savedUser);
            
            // Log all users in database after save
            List<User> allUsers = userRepository.findAll();
            log.info("📊 ALL USERS IN DB AFTER SIGNUP ({} total):", allUsers.size());
            for (User u : allUsers) {
                log.info("  - {}", u);
            }

            // Convert to response DTO (without password)
            UserResponse userResponse = new UserResponse(
                savedUser.getId(),
                savedUser.getUsername(),
                savedUser.getEmail(),
                savedUser.getFirstName(),
                savedUser.getLastName(),
                savedUser.getTotalScore(),
                savedUser.getGamesPlayed()
            );

            return ResponseEntity.ok(userResponse);

        } catch (Exception e) {
            log.error("❌ Error in signup: {}", e.getMessage(), e);
            return ResponseEntity.badRequest()
                .body(new ApiError("Registration failed: " + e.getMessage()));
        }
    }

    @Operation(summary = "User login", description = "Authenticate user with username/email and password")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login successful"),
            @ApiResponse(responseCode = "401", description = "Invalid credentials")
    })
    // POST endpoint for user authentication
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody UserLogin loginRequest) {
        log.info("📥 REQUEST: POST /api/auth/login - User login: usernameOrEmail='{}'", 
                loginRequest.getUsernameOrEmail());
        try {
            // Find user by username or email
            Optional<User> userOptional = findUserByUsernameOrEmail(loginRequest.getUsernameOrEmail());

            if (userOptional.isEmpty()) {
                log.info("❌ User not found: {}", loginRequest.getUsernameOrEmail());
                return ResponseEntity.ok(LoginResponse.failure("User not found"));
            }

            User user = userOptional.get();
            log.info("✅ User found: {}", user);

            // Check password (in production, compare with hashed password)
            if (!user.getPassword().equals(loginRequest.getPassword())) {
                log.info("❌ Invalid password for user: {}", user.getUsername());
                return ResponseEntity.ok(LoginResponse.failure("Invalid password"));
            }

            // Create user response
            UserResponse userResponse = new UserResponse(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getFirstName(),
                user.getLastName(),
                user.getTotalScore(),
                user.getGamesPlayed()
            );

            log.info("✅ Login successful for user: {}", user.getUsername());
            return ResponseEntity.ok(LoginResponse.success(userResponse));

        } catch (Exception e) {
            log.error("❌ Error in login: {}", e.getMessage(), e);
            return ResponseEntity.ok(LoginResponse.failure("Login failed: " + e.getMessage()));
        }
    }

    @Operation(summary = "Get user profile", description = "Get current user's profile information")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Profile retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    // GET endpoint with path variable for user profile
    @GetMapping("/profile/{userId}")
    public ResponseEntity<?> getProfile(@PathVariable Long userId) {
        log.info("📥 REQUEST: GET /api/auth/profile/{} - Get user profile", userId);
        try {
            Optional<User> userOptional = userRepository.findById(userId);
            
            if (userOptional.isEmpty()) {
                log.info("❌ User not found for ID: {}", userId);
                return ResponseEntity.notFound().build();
            }

            User user = userOptional.get();
            log.info("✅ User profile found: {}", user);
            UserResponse userResponse = new UserResponse(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getFirstName(),
                user.getLastName(),
                user.getTotalScore(),
                user.getGamesPlayed()
            );

            return ResponseEntity.ok(userResponse);

        } catch (Exception e) {
            log.error("❌ Error in getProfile: {}", e.getMessage(), e);
            return ResponseEntity.badRequest()
                .body(new ApiError("Failed to get profile: " + e.getMessage()));
        }
    }

    @Operation(summary = "Update user score", description = "Update user's total score and games played")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Score updated successfully"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    // POST endpoint with path variable and request parameter
    @PostMapping("/update-score/{userId}")
    public ResponseEntity<?> updateScore(@PathVariable Long userId, @RequestParam int scoreToAdd) {
        try {
            Optional<User> userOptional = userRepository.findById(userId);
            
            if (userOptional.isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            User user = userOptional.get();
            user.setTotalScore(user.getTotalScore() + scoreToAdd);
            user.setGamesPlayed(user.getGamesPlayed() + 1);
            
            User savedUser = userRepository.save(user);

            UserResponse userResponse = new UserResponse(
                savedUser.getId(),
                savedUser.getUsername(),
                savedUser.getEmail(),
                savedUser.getFirstName(),
                savedUser.getLastName(),
                savedUser.getTotalScore(),
                savedUser.getGamesPlayed()
            );

            return ResponseEntity.ok(userResponse);

        } catch (Exception e) {
            return ResponseEntity.badRequest()
                .body(new ApiError("Failed to update score: " + e.getMessage()));
        }
    }

    private Optional<User> findUserByUsernameOrEmail(String usernameOrEmail) {
        // Check if it's an email (contains @)
        if (usernameOrEmail.contains("@")) {
            return userRepository.findByEmail(usernameOrEmail);
        } else {
            return userRepository.findByUsername(usernameOrEmail);
        }
    }

    // Simple error response class
    public static class ApiError {
        private String message;

        public ApiError(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
} 