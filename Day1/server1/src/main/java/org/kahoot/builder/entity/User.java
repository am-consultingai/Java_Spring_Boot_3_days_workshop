package org.kahoot.builder.entity;

import jakarta.persistence.*;

// JPA entity representing user table
@Entity
@Table(name = "users")
public class User {
    
    // Primary key with auto-generation
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    // Unique and required username column
    @Column(unique = true, nullable = false)
    private String username;
    
    // Unique and required email column
    @Column(unique = true, nullable = false)
    private String email;
    
    // Required password column
    @Column(nullable = false)
    private String password;
    
    private String firstName;
    private String lastName;
    
    // Score tracking with custom column name
    @Column(name = "total_score")
    private int totalScore = 0;
    
    // Games played counter with custom column name
    @Column(name = "games_played")
    private int gamesPlayed = 0;
    
    public User() {}
    
    public User(String username, String email, String password, String firstName, String lastName) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
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
    
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", totalScore=" + totalScore +
                ", gamesPlayed=" + gamesPlayed +
                '}';
    }
} 