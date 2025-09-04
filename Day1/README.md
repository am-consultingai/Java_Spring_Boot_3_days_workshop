# 🎯 Kahoot Quiz Builder - AM Consulting Java Spring Boot Workshop

Welcome to the **AM Consulting Java Spring Boot Workshop**! This repository contains a complete Kahoot-like quiz application that demonstrates key Spring Boot concepts and best practices learned throughout the first day of our workshop.

## 📋 Table of Contents

- [Project Overview](#project-overview)
- [Architecture](#architecture)
- [File Structure](#file-structure)
- [Key Concepts Demonstrated](#key-concepts-demonstrated)
- [Getting Started](#getting-started)
- [API Documentation](#api-documentation)
- [Detailed File Analysis](#detailed-file-analysis)

## 🎯 Project Overview

This project implements a **Kahoot-like quiz application** with the following features:

- **User Authentication**: Registration and login system
- **Quiz Management**: Create, read, update quizzes
- **Question Management**: Add multiple-choice questions to quizzes
- **Modern UI**: React frontend with responsive design
- **RESTful API**: Spring Boot backend with comprehensive endpoints
- **Database Integration**: PostgreSQL with JPA/Hibernate

## 🏗️ Architecture

The application follows a **3-tier architecture**:

```
┌─────────────────┐    ┌─────────────────┐    ┌─────────────────┐
│   React Client  │◄──►│  Spring Boot    │◄──►│   PostgreSQL    │
│   (Frontend)    │    │   (Backend)     │    │   (Database)    │
└─────────────────┘    └─────────────────┘    └─────────────────┘
```

### Backend Architecture (Spring Boot)
- **Controllers**: Handle HTTP requests and responses
- **Services**: Business logic layer (implicit in this example)
- **Repositories**: Data access layer using Spring Data JPA
- **Entities**: JPA entities representing database tables
- **DTOs**: Data Transfer Objects for API communication

### Frontend Architecture (React)
- **Components**: Reusable UI components
- **Services**: API communication layer
- **Routing**: React Router for navigation
- **State Management**: React hooks for local state

## 📁 File Structure

```
JavaSpring1/
├── server1/                          # Spring Boot Backend
│   ├── pom.xml                       # Maven dependencies
│   └── src/main/
│       ├── java/org/kahoot/builder/
│       │   ├── Main.java             # Application entry point
│       │   ├── controllers/          # REST Controllers
│       │   │   ├── AuthController.java
│       │   │   ├── QuizRestController.java
│       │   │   └── GlobalExceptionHandler.java
│       │   ├── dto/                  # Data Transfer Objects
│       │   │   ├── UserRegistration.java
│       │   │   ├── UserLogin.java
│       │   │   ├── UserResponse.java
│       │   │   ├── LoginResponse.java
│       │   │   ├── CreateQuizRequest.java
│       │   │   └── CreateQuestionRequest.java
│       │   ├── entity/               # JPA Entities
│       │   │   ├── User.java
│       │   │   ├── Quiz.java
│       │   │   └── QuizQuestion.java
│       │   └── repository/           # Data Access Layer
│       │       ├── UserRepository.java
│       │       ├── QuizRepository.java
│       │       └── QuizQuestionRepository.java
│       └── resources/
│           └── application.properties # Configuration
└── client/                           # React Frontend
    ├── package.json                  # Node.js dependencies
    ├── src/
    │   ├── App.js                    # Main React component
    │   ├── components/               # React components
    │   │   ├── HomePage.js
    │   │   ├── Dashboard.js
    │   │   ├── QuestionsList.js
    │   │   ├── QuestionEditor.js
    │   │   └── QuestionCreator.js
    │   └── services/
    │       └── api.js                # API communication
    └── public/
        └── index.html
```

## 🎓 Key Concepts Demonstrated

### Spring Boot Concepts
- **Auto-configuration**: Automatic bean configuration
- **Starter Dependencies**: Pre-configured dependency sets
- **Embedded Server**: Tomcat server integration
- **Configuration Properties**: Externalized configuration
- **Actuator**: Production-ready features

### Spring Web Concepts
- **REST Controllers**: `@RestController` and `@RequestMapping`
- **Request Mapping**: `@GetMapping`, `@PostMapping`
- **Path Variables**: `@PathVariable`
- **Request Body**: `@RequestBody`
- **Cross-Origin**: `@CrossOrigin` for CORS

### Spring Data JPA Concepts
- **Repository Pattern**: Interface-based data access
- **Query Methods**: Derived query methods
- **Entity Relationships**: `@ManyToOne`, `@OneToMany`
- **JPA Annotations**: `@Entity`, `@Table`, `@Column`
- **Lifecycle Callbacks**: `@PreUpdate`

### Validation & Error Handling
- **Bean Validation**: `@Valid`, `@NotBlank`, `@Email`
- **Global Exception Handling**: `@ControllerAdvice`
- **Custom Error Responses**: Structured error handling

### Frontend Concepts
- **React Hooks**: `useState`, `useEffect`
- **React Router**: Client-side routing
- **Axios**: HTTP client for API calls
- **Component Composition**: Reusable UI components

## 🚀 Getting Started

### Prerequisites
- Java 17+
- Maven 3.6+
- Node.js 16+
- PostgreSQL 12+

### Backend Setup
1. **Start PostgreSQL** and create a database named `postgres`
2. **Navigate to server directory**:
   ```bash
   cd server1
   ```
3. **Run the application**:
   ```bash
   mvn spring-boot:run
   ```
4. **Access Swagger UI**: http://localhost:8080/swagger-ui.html

### Frontend Setup
1. **Navigate to client directory**:
   ```bash
   cd client
   ```
2. **Install dependencies**:
   ```bash
   npm install
   ```
3. **Start the development server**:
   ```bash
   npm start
   ```
4. **Access the application**: http://localhost:3000

## 📚 API Documentation

### Authentication Endpoints
- `POST /api/auth/signup` - User registration
- `POST /api/auth/login` - User login
- `GET /api/auth/profile/{userId}` - Get user profile
- `POST /api/auth/update-score/{userId}` - Update user score

### Quiz Endpoints
- `GET /api/quiz?owner={ownerId}` - Get all quizzes for owner
- `GET /api/quiz/{quizId}` - Get specific quiz
- `POST /api/quiz` - Create new quiz
- `POST /api/quiz/{quizId}` - Update quiz

### Question Endpoints
- `GET /api/quiz/{quizId}/question` - Get all questions for quiz
- `GET /api/quiz/{quizId}/question/{questionId}` - Get specific question
- `POST /api/quiz/{quizId}/question` - Create new question
- `POST /api/quiz/{quizId}/question/{questionId}` - Create or update question

## 📖 Detailed File Analysis

### Backend Files

#### `Main.java`
**Purpose**: Application entry point
**Key Concepts**: 
- `@SpringBootApplication` annotation enables auto-configuration
- `SpringApplication.run()` starts the embedded server

#### `AuthController.java`
**Purpose**: Handles user authentication and profile management
**Key Functions**:
- `signup()`: User registration with validation
- `login()`: User authentication
- `getProfile()`: Retrieve user information
- `updateScore()`: Update user statistics

**Key Concepts**:
- `@RestController` and `@RequestMapping`
- `@Valid` for request validation
- `ResponseEntity` for HTTP responses
- Exception handling with try-catch blocks

#### `QuizRestController.java`
**Purpose**: Manages quiz and question operations
**Key Functions**:
- `getAllQuizzes()`: Retrieve quizzes by owner
- `createQuiz()`: Create new quiz
- `createQuestion()`: Add questions to quizzes
- `createOrUpdateQuestion()`: Update existing questions

**Key Concepts**:
- RESTful API design
- Path variables and request parameters
- Entity relationships and data population

#### `GlobalExceptionHandler.java`
**Purpose**: Centralized exception handling
**Key Concepts**:
- `@ControllerAdvice` for global exception handling
- `@ExceptionHandler` for specific exception types
- Structured error responses

#### Entity Classes

##### `User.java`
**Purpose**: Represents user data in database
**Key Annotations**:
- `@Entity` and `@Table(name = "users")`
- `@Id` and `@GeneratedValue` for primary key
- `@Column` with constraints (unique, nullable)

##### `Quiz.java`
**Purpose**: Represents quiz data
**Key Concepts**:
- `@ManyToOne` relationship with User
- `@PreUpdate` lifecycle callback
- `@Transient` field for non-persistent data

##### `QuizQuestion.java`
**Purpose**: Represents quiz questions
**Key Concepts**:
- `@ElementCollection` for options list
- `@CollectionTable` for separate options table
- Complex data structure with multiple fields

#### Repository Interfaces

##### `UserRepository.java`
**Purpose**: Data access for User entity
**Key Methods**:
- `findByUsername()` and `findByEmail()`
- `existsByUsername()` and `existsByEmail()`
- Extends `JpaRepository<User, Long>`

##### `QuizRepository.java`
**Purpose**: Data access for Quiz entity
**Key Methods**:
- `findByCreator()`: Find quizzes by creator
- `findByIsActiveTrue()`: Find active quizzes

##### `QuizQuestionRepository.java`
**Purpose**: Data access for QuizQuestion entity
**Key Methods**:
- `findByQuizIdOrderByQuestionOrder()`: Get ordered questions
- `countByQuizId()`: Count questions per quiz

#### DTO Classes

##### `UserRegistration.java`
**Purpose**: Data transfer for user registration
**Key Concepts**:
- Bean validation annotations (`@NotBlank`, `@Email`, `@Size`)
- Swagger documentation (`@Schema`)

##### `UserLogin.java`
**Purpose**: Data transfer for user login
**Key Features**:
- Flexible login (username or email)
- Validation constraints

##### `LoginResponse.java`
**Purpose**: Structured login response
**Key Features**:
- Static factory methods (`success()`, `failure()`)
- Nested user information

### Frontend Files

#### `App.js`
**Purpose**: Main React application component
**Key Concepts**:
- React Router setup with `BrowserRouter`
- Route protection with authentication check
- Component composition

#### `HomePage.js`
**Purpose**: Authentication interface
**Key Features**:
- Toggle between login and signup modes
- Form validation and error handling
- API integration with authentication endpoints

#### `Dashboard.js`
**Purpose**: Main application dashboard
**Key Features**:
- Quiz listing and management
- Create new quiz functionality
- Navigation to question management

#### `api.js`
**Purpose**: API communication layer
**Key Features**:
- Axios instance configuration
- Request/response interceptors
- Organized API functions by domain
- Comprehensive logging for debugging

## 🎯 Workshop Learning Objectives

This project demonstrates:

1. **Spring Boot Fundamentals**: Auto-configuration, starters, embedded server
2. **RESTful API Design**: Proper HTTP methods, status codes, resource naming
3. **Data Persistence**: JPA entities, repositories, relationships
4. **Validation**: Input validation, error handling
5. **Frontend Integration**: React with REST APIs
6. **Project Structure**: Layered architecture, separation of concerns
7. **Development Tools**: Maven, npm, Swagger UI

## 🔧 Configuration

### Database Configuration (`application.properties`)
```properties
# PostgreSQL connection
spring.datasource.url=jdbc:postgresql://localhost:5432/postgres
spring.datasource.username=postgres
spring.datasource.password=postgres

# JPA settings
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=false

# Server settings
server.port=8080
```

### Frontend Configuration
- API base URL: `http://localhost:8080`
- Development server: `http://localhost:3000`

## 🎉 Next Steps

After exploring this codebase, consider implementing:

1. **Security**: JWT authentication, password hashing
2. **Testing**: Unit tests, integration tests
3. **Deployment**: Docker containers, cloud deployment
4. **Advanced Features**: Real-time quiz playing, leaderboards
5. **Performance**: Caching, database optimization

---

**Happy Learning! 🚀**

This project serves as a comprehensive example of modern Java Spring Boot development with React frontend integration. Each file demonstrates specific concepts and best practices that are essential for enterprise Java development.
