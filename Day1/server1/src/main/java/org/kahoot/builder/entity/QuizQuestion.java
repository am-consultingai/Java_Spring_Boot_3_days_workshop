package org.kahoot.builder.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

// JPA entity representing quiz question table
@Entity
@Table(name = "quiz_questions")
public class QuizQuestion {
    
    // Primary key with auto-generation
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    // Foreign key to quiz with custom column name
    @Column(name = "quiz_id", nullable = false)
    private Long quizId;
    
    // Question text with TEXT column type
    @Column(nullable = false, columnDefinition = "TEXT")
    private String questionText;
    
    // Element collection for multiple choice options
    @ElementCollection
    @CollectionTable(name = "question_options", joinColumns = @JoinColumn(name = "question_id"))
    @Column(name = "option_text")
    private List<String> options;
    
    @Column(name = "correct_answer_index", nullable = false)
    private int correctAnswerIndex;
    
    @Column(name = "question_order")
    private int questionOrder;
    
    @Column(name = "points")
    private int points = 10;
    
    @Column(name = "time_limit")
    private int timeLimit = 30; // seconds
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    protected QuizQuestion() {}
    
    public QuizQuestion(Long quizId, String questionText, List<String> options, int correctAnswerIndex) {
        this.quizId = quizId;
        this.questionText = questionText;
        this.options = options;
        this.correctAnswerIndex = correctAnswerIndex;
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
    
    public Long getQuizId() {
        return quizId;
    }
    
    public void setQuizId(Long quizId) {
        this.quizId = quizId;
    }
    
    public String getQuestionText() {
        return questionText;
    }
    
    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }
    
    public List<String> getOptions() {
        return options;
    }
    
    public void setOptions(List<String> options) {
        this.options = options;
    }
    
    public int getCorrectAnswerIndex() {
        return correctAnswerIndex;
    }
    
    public void setCorrectAnswerIndex(int correctAnswerIndex) {
        this.correctAnswerIndex = correctAnswerIndex;
    }
    
    public int getQuestionOrder() {
        return questionOrder;
    }
    
    public void setQuestionOrder(int questionOrder) {
        this.questionOrder = questionOrder;
    }
    
    public int getPoints() {
        return points;
    }
    
    public void setPoints(int points) {
        this.points = points;
    }
    
    public int getTimeLimit() {
        return timeLimit;
    }
    
    public void setTimeLimit(int timeLimit) {
        this.timeLimit = timeLimit;
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
    
    @Override
    public String toString() {
        return "QuizQuestion{" +
                "id=" + id +
                ", quizId=" + quizId +
                ", questionText='" + questionText + '\'' +
                ", correctAnswerIndex=" + correctAnswerIndex +
                ", questionOrder=" + questionOrder +
                ", points=" + points +
                '}';
    }
} 