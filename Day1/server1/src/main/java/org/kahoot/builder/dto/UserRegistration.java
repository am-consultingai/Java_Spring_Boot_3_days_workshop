package org.kahoot.builder.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

// DTO for user registration with validation annotations
public class UserRegistration {
    
    // Bean validation - required username with size constraints
    @Schema(example = "johndoe", description = "Username must be unique")
    @NotBlank(message = "Username is required")
    @Size(min = 3, max = 20, message = "Username must be between 3 and 20 characters")
    private String username;
    
    // Bean validation - required email with format validation
    @Schema(example = "john.doe@email.com", description = "Email must be unique")
    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    private String email;
    
    // Bean validation - required password
    @Schema(example = "password123", description = "Password for the account")
    @NotBlank(message = "Password is required")
//    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;
    
    @Schema(example = "John", description = "User's first name")
    private String firstName;
    
    @Schema(example = "Doe", description = "User's last name")
    private String lastName;
    
    public UserRegistration() {}

    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getFirstName() {
        return firstName;
    }
    
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    
    public String getLastName() {
        return lastName;
    }
    
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
} 