package org.kahoot.builder.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

// JPA entity representing quiz table
@Entity
@Table(name = "quizzes")
public class Quiz {
    
    // Primary key with auto-generation
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    // Required title column
    @Column(nullable = false)
    private String title;
    
    private String description;
    
    // Many-to-one relationship with User entity
    @ManyToOne
    @JoinColumn(name = "creator_id")
    private User creator;

    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    @Column(name = "is_active")
    private boolean isActive = true;
    
    protected Quiz() {}
    
    public Quiz(String title, String description, User creator) {
        this.title = title;
        this.description = description;
        this.creator = creator;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
    
    // JPA lifecycle callback - updates timestamp before save
    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
    
    // Getters and setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public User getCreator() {
        return creator;
    }
    
    public void setCreator(User creator) {
        this.creator = creator;
    }
    
    // Transient field for questions (not persisted, populated by service layer)
    @Transient
    private List<QuizQuestion> questions;
    
    public List<QuizQuestion> getQuestions() {
        return questions;
    }
    
    public void setQuestions(List<QuizQuestion> questions) {
        this.questions = questions;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
    
    public boolean isActive() {
        return isActive;
    }
    
    public void setActive(boolean active) {
        isActive = active;
    }
    
    @Override
    public String toString() {
        return "Quiz{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", createdAt=" + createdAt +
                ", isActive=" + isActive +
                '}';
    }
}
