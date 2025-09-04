package org.kahoot.builder.repository;

import org.kahoot.builder.entity.Quiz;
import org.kahoot.builder.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

// Spring Data JPA repository for Quiz entity
public interface QuizRepository extends JpaRepository<Quiz, Long> {
    // Derived query method - find quizzes by creator
    List<Quiz> findByCreator(User creator);
    // Derived query method - find active quizzes
    List<Quiz> findByIsActiveTrue();
}