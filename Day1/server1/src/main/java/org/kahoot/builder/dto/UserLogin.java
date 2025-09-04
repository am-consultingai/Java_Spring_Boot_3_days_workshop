package org.kahoot.builder.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

// DTO for user login with validation annotations
public class UserLogin {
    
    // Bean validation - required username or email
    @Schema(example = "johndoe", description = "Username or email")
    @NotBlank(message = "Username or email is required")
    private String usernameOrEmail;
    
    // Bean validation - required password
    @Schema(example = "password123", description = "User's password")
    @NotBlank(message = "Password is required")
    private String password;
    
    public UserLogin() {}
    
    public String getUsernameOrEmail() {
        return usernameOrEmail;
    }
    
    public void setUsernameOrEmail(String usernameOrEmail) {
        this.usernameOrEmail = usernameOrEmail;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
} 