package org.kahoot.builder.controllers;

import io.swagger.v3.oas.annotations.Operation;
import org.kahoot.builder.dto.CreateQuestionRequest;
import org.kahoot.builder.dto.CreateQuizRequest;
import org.kahoot.builder.repository.*;
import org.kahoot.builder.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// REST controller for quiz and question management
@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*") // Enable CORS for frontend
public class QuizRestController {

    private static final Logger log = LoggerFactory.getLogger(QuizRestController.class);

    // Dependency injection of repositories
    @Autowired
    private QuizRepository quizRepository;
    
    @Autowired
    private QuizQuestionRepository questionRepository;
    
    @Autowired
    private UserRepository userRepository;

    // QUIZ ENDPOINTS
    
    @Operation(summary = "Get all quizzes", description = "Retrieve all active quizzes")
    // GET endpoint with request parameter
    @GetMapping("/quiz")
    public ResponseEntity<List<Quiz>> getAllQuizzes(@RequestParam("owner") String owner) {
        log.info("📥 REQUEST: GET /api/quiz - Get all quizzes");
        User user=new User();
        user.setId(Long.parseLong(owner));
        List<Quiz> quizzes = quizRepository.findByCreator(user);
        
        // Populate questions for each quiz
        for (Quiz quiz : quizzes) {
            List<QuizQuestion> questions = questionRepository.findByQuizIdOrderByQuestionOrder(quiz.getId());
            quiz.setQuestions(questions);
        }
        
        log.info("✅ RESPONSE: Found {} quizzes", quizzes.size());
        return ResponseEntity.ok(quizzes);
    }
    
    @Operation(summary = "Get quiz by ID", description = "Retrieve a specific quiz by ID")
    // GET endpoint with path variable
    @GetMapping("/quiz/{quizId}")
    public ResponseEntity<Quiz> getQuiz(@PathVariable Long quizId) {
        log.info("📥 REQUEST: GET /api/quiz/{} - Get quiz by ID", quizId);
        Optional<Quiz> quiz = quizRepository.findById(quizId);
        if (quiz.isPresent()) {
            Quiz foundQuiz = quiz.get();
            
            // Populate questions for this quiz
            List<QuizQuestion> questions = questionRepository.findByQuizIdOrderByQuestionOrder(quizId);
            foundQuiz.setQuestions(questions);
            
            log.info("✅ RESPONSE: Found quiz: {} with {} questions", foundQuiz.getTitle(), questions.size());
            return ResponseEntity.ok(foundQuiz);
        } else {
            log.info("❌ RESPONSE: Quiz not found for ID: {}", quizId);
            return ResponseEntity.notFound().build();
        }
    }
    
    @Operation(summary = "Create new quiz", description = "Create a new quiz")
    // POST endpoint for creating new quiz with validation
    @PostMapping("/quiz")
    public ResponseEntity<?> createQuiz(@Valid @RequestBody CreateQuizRequest request) {
        log.info("📥 REQUEST: POST /api/quiz - Create new quiz: title='{}', description='{}'", 
                request.getTitle(), request.getDescription());
        try {
            // For now, use the first user as creator (in real app, get from JWT token)
            Optional<User> creator = userRepository.findById((long)request.getOwnerId());
            if (creator.isEmpty()) {
                log.error("❌ Creator not found for ID: "+request.getOwnerId());
                return ResponseEntity.badRequest().body(new ApiError("Creator not found"));
            }
            
            Quiz quiz = new Quiz(request.getTitle(), request.getDescription(), creator.get());
            Quiz savedQuiz = quizRepository.save(quiz);
            log.info("💾 Quiz saved: {}", savedQuiz);
            
            // Log all quizzes in database after save
            List<Quiz> allQuizzes = quizRepository.findAll();
            log.info("📊 ALL QUIZZES IN DB AFTER SAVE ({} total):", allQuizzes.size());
            for (Quiz q : allQuizzes) {
                log.info("  - {}", q);
            }
            
            return ResponseEntity.ok(savedQuiz);
        } catch (Exception e) {
            log.error("❌ Error creating quiz: {}", e.getMessage(), e);
            return ResponseEntity.badRequest().body(new ApiError("Failed to create quiz: " + e.getMessage()));
        }
    }
    
    @Operation(summary = "Update quiz", description = "Update an existing quiz")
    @PostMapping("/quiz/{quizId}")
    public ResponseEntity<?> updateQuiz(@PathVariable Long quizId, @Valid @RequestBody CreateQuizRequest request) {
        log.info("📥 REQUEST: POST /api/quiz/{} - Update quiz: title='{}', description='{}'", 
                quizId, request.getTitle(), request.getDescription());
        try {
            Optional<Quiz> quizOpt = quizRepository.findById(quizId);
            if (quizOpt.isEmpty()) {
                log.info("❌ Quiz not found for ID: {}", quizId);
                return ResponseEntity.notFound().build();
            }
            
            Quiz quiz = quizOpt.get();
            quiz.setTitle(request.getTitle());
            quiz.setDescription(request.getDescription());
            
            Quiz savedQuiz = quizRepository.save(quiz);
            log.info("💾 Quiz updated: {}", savedQuiz);
            
            // Log all quizzes in database after save
            List<Quiz> allQuizzes = quizRepository.findAll();
            log.info("📊 ALL QUIZZES IN DB AFTER UPDATE ({} total):", allQuizzes.size());
            for (Quiz q : allQuizzes) {
                log.info("  - {}", q);
            }
            
            return ResponseEntity.ok(savedQuiz);
        } catch (Exception e) {
            log.error("❌ Error updating quiz: {}", e.getMessage(), e);
            return ResponseEntity.badRequest().body(new ApiError("Failed to update quiz: " + e.getMessage()));
        }
    }

    // QUESTION ENDPOINTS
    @Operation(summary = "Get all questions for a quiz", description = "Retrieve all questions for a specific quiz")
    @GetMapping("/quiz/{quizId}/question")
    public ResponseEntity<List<QuizQuestion>> getQuizQuestions(@PathVariable Long quizId) {
        log.info("📥 REQUEST: GET /api/quiz/{}/question - Get all questions for quiz", quizId);
        if (!quizRepository.existsById(quizId)) {
            log.info("❌ Quiz not found for ID: {}", quizId);
            return ResponseEntity.notFound().build();
        }
        
        List<QuizQuestion> questions = questionRepository.findByQuizIdOrderByQuestionOrder(quizId);
        log.info("✅ RESPONSE: Found {} questions for quiz {}", questions.size(), quizId);
        return ResponseEntity.ok(questions);
    }
    
    @Operation(summary = "Get specific question", description = "Retrieve a specific question by quiz ID and question ID")
    @GetMapping("/quiz/{quizId}/question/{questionId}")
    public ResponseEntity<QuizQuestion> getQuestion(@PathVariable Long quizId, @PathVariable Long questionId) {
        log.info("📥 REQUEST: GET /api/quiz/{}/question/{} - Get specific question", quizId, questionId);
        Optional<QuizQuestion> question = questionRepository.findById(questionId);
        
        if (question.isEmpty() || !question.get().getQuizId().equals(quizId)) {
            log.info("❌ Question not found for quiz {} and question {}", quizId, questionId);
            return ResponseEntity.notFound().build();
        }
        
        log.info("✅ RESPONSE: Found question: {}", question.get());
        return ResponseEntity.ok(question.get());
    }
    
    @Operation(summary = "Create or update question", description = "Create a new question or update existing one")
    @PostMapping("/quiz/{quizId}/question/{questionId}")
    public ResponseEntity<?> createOrUpdateQuestion(@PathVariable Long quizId, 
                                                   @PathVariable Long questionId,
                                                   @Valid @RequestBody CreateQuestionRequest request) {
        log.info("🚀 createOrUpdateQuestion called with quizId={}, questionId={}, request={}", 
                quizId, questionId, request.getQuestion());
        try {
            // Verify quiz exists
            if (!quizRepository.existsById(quizId)) {
                return ResponseEntity.notFound().build();
            }
            QuizQuestion question;
            // Check if question exists
            Optional<QuizQuestion> existingQuestion = questionRepository.findById(questionId);
            
            if (existingQuestion.isPresent() && existingQuestion.get().getQuizId().equals(quizId)) {
                // Update existing question
                question = existingQuestion.get();
                question.setQuestionText(request.getQuestion());
                question.setOptions(request.getOptions());
                question.setCorrectAnswerIndex(request.getCorrectAnswer());
                question.setPoints(request.getPoints() != null ? request.getPoints() : 10);
                question.setTimeLimit(request.getTimeLimit() != null ? request.getTimeLimit() : 30);
            } else {
                // Create new question
                question = new QuizQuestion(quizId, request.getQuestion(), request.getOptions(), request.getCorrectAnswer());
                question.setId(questionId);
                question.setPoints(request.getPoints() != null ? request.getPoints() : 10);
                question.setTimeLimit(request.getTimeLimit() != null ? request.getTimeLimit() : 30);
                
                // Set question order
                Integer maxOrder = questionRepository.countByQuizId(quizId);
                question.setQuestionOrder(maxOrder != null ? maxOrder + 1 : 1);
            }
            
            QuizQuestion savedQuestion = questionRepository.save(question);
            log.info("💾 Question saved: {}", savedQuestion);
            
            // Log all questions in database after save
            List<QuizQuestion> allQuestions = questionRepository.findAll();
            log.info("📊 ALL QUESTIONS IN DB AFTER SAVE ({} total):", allQuestions.size());
            for (QuizQuestion q : allQuestions) {
                log.info("  - {}", q);
            }
            
            return ResponseEntity.ok(savedQuestion);
            
        } catch (Exception e) {
            log.error("❌ Error in createOrUpdateQuestion: {}", e.getMessage(), e);
            return ResponseEntity.badRequest().body(new ApiError("Failed to save question: " + e.getMessage()));
        }
    }
    
    @Operation(summary = "Create new question", description = "Create a new question for a quiz")
    @PostMapping("/quiz/{quizId}/question")
    public ResponseEntity<?> createQuestion(@PathVariable Long quizId, @Valid @RequestBody CreateQuestionRequest request) {
        log.info("🚀 createQuestion called with quizId={}, request={}", quizId, request.getQuestion());
        try {
            // Verify quiz exists
            if (!quizRepository.existsById(quizId)) {
                return ResponseEntity.notFound().build();
            }
            
            QuizQuestion question = new QuizQuestion(quizId, request.getQuestion(), request.getOptions(), request.getCorrectAnswer());
            question.setPoints(request.getPoints() != null ? request.getPoints() : 10);
            question.setTimeLimit(request.getTimeLimit() != null ? request.getTimeLimit() : 30);
            
            // Set question order
            Integer maxOrder = questionRepository.countByQuizId(quizId);
            question.setQuestionOrder(maxOrder != null ? maxOrder + 1 : 1);
            
            QuizQuestion savedQuestion = questionRepository.save(question);
            log.info("💾 Question created: {}", savedQuestion);
            
            // Log all questions in database after save
            List<QuizQuestion> allQuestions = questionRepository.findAll();
            log.info("📊 ALL QUESTIONS IN DB AFTER CREATE ({} total):", allQuestions.size());
            for (QuizQuestion q : allQuestions) {
                log.info("  - {}", q);
            }
            
            return ResponseEntity.ok(savedQuestion);
            
        } catch (Exception e) {
            log.error("❌ Error in createQuestion: {}", e.getMessage(), e);
            return ResponseEntity.badRequest().body(new ApiError("Failed to create question: " + e.getMessage()));
        }
    }

    public static class ApiError {
        private String message;

        public ApiError(String message) {
            this.message = message;
        }

        public String getMessage() { return message; }
        public void setMessage(String message) { this.message = message; }
    }
}
