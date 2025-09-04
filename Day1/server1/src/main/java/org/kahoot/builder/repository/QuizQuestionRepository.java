package org.kahoot.builder.repository;

import org.kahoot.builder.entity.QuizQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

// Spring Data JPA repository for QuizQuestion entity
public interface QuizQuestionRepository extends JpaRepository<QuizQuestion, Long> {

    // Derived query method with ordering - find questions by quiz ID
    List<QuizQuestion> findByQuizIdOrderByQuestionOrder(@Param("quizId") Long quizId);
    
    // Derived query method - count questions by quiz ID
    Integer countByQuizId(@Param("quizId") Long quizId);
}