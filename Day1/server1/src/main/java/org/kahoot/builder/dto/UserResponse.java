package org.kahoot.builder.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public class UserResponse {
    
    @Schema(example = "1")
    private Long id;
    
    @Schema(example = "johndoe")
    private String username;
    
    @Schema(example = "john.doe@email.com")
    private String email;
    
    @Schema(example = "John")
    private String firstName;
    
    @Schema(example = "Doe")
    private String lastName;
    
    @Schema(example = "1250")
    private int totalScore;
    
    @Schema(example = "5")
    private int gamesPlayed;
    
    public UserResponse() {}
    
    public UserResponse(Long id, String username, String email, String firstName, String lastName, 
                       int totalScore, int gamesPlayed) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.totalScore = totalScore;
        this.gamesPlayed = gamesPlayed;
    }
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
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
    
    public int getTotalScore() {
        return totalScore;
    }
    
    public void setTotalScore(int totalScore) {
        this.totalScore = totalScore;
    }
    
    public int getGamesPlayed() {
        return gamesPlayed;
    }
    
    public void setGamesPlayed(int gamesPlayed) {
        this.gamesPlayed = gamesPlayed;
    }
} 