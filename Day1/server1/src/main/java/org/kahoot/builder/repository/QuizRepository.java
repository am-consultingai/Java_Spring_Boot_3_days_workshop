package org.kahoot.builder.repository;

import org.kahoot.builder.entity.Quiz;
import org.kahoot.builder.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuizRepository extends JpaRepository<Quiz, Long> {
    List<Quiz> findByCreator(User creator);
    List<Quiz> findByIsActiveTrue();
}