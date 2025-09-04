package org.kahoot.builder.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public class LoginResponse {
    
    @Schema(example = "true")
    private boolean success;
    
    @Schema(example = "Login successful")
    private String message;
    
    @Schema(description = "User information if login successful")
    private UserResponse user;
    
    @Schema(example = "abc123token", description = "Authentication token (for future use)")
    private String token;
    
    public LoginResponse() {}
    
    public LoginResponse(boolean success, String message, UserResponse user, String token) {
        this.success = success;
        this.message = message;
        this.user = user;
        this.token = token;
    }
    
    // Convenience constructors
    public static LoginResponse success(UserResponse user) {
        return new LoginResponse(true, "Login successful", user, null);
    }
    
    public static LoginResponse failure(String message) {
        return new LoginResponse(false, message, null, null);
    }
    
    public boolean isSuccess() {
        return success;
    }
    
    public void setSuccess(boolean success) {
        this.success = success;
    }
    
    public String getMessage() {
        return message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
    
    public UserResponse getUser() {
        return user;
    }
    
    public void setUser(UserResponse user) {
        this.user = user;
    }
    
    public String getToken() {
        return token;
    }
    
    public void setToken(String token) {
        this.token = token;
    }
} 