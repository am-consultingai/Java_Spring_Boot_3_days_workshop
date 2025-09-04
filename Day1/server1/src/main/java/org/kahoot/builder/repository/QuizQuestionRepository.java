package org.kahoot.builder.repository;

import org.kahoot.builder.entity.QuizQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface QuizQuestionRepository extends JpaRepository<QuizQuestion, Long> {

    List<QuizQuestion> findByQuizIdOrderByQuestionOrder(@Param("quizId") Long quizId);
    
    Integer countByQuizId(@Param("quizId") Long quizId);
}