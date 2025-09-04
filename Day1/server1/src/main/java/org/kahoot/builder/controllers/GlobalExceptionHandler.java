package org.kahoot.builder.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.HashMap;
import java.util.Map;

// Global exception handler for all controllers
@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    // Exception handler for type mismatch errors
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Map<String, Object>> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
        log.error("🔥 MethodArgumentTypeMismatchException caught:");
        log.error("   Parameter name: {}", ex.getName());
        log.error("   Parameter value: '{}'", ex.getValue());
        log.error("   Required type: {}", ex.getRequiredType());
        log.error("   Message: {}", ex.getMessage());

        // Get the request URL from the exception if possible
        String requestUrl = "unknown";
        if (ex.getParameter().getMethod() != null) {
            requestUrl = ex.getParameter().getMethod().getDeclaringClass().getSimpleName() + "." +
                        ex.getParameter().getMethod().getName();
        }
        log.error("   Request method: {}", requestUrl);

        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("error", "Invalid parameter type");
        errorResponse.put("message", String.format(
            "Parameter '%s' has invalid value '%s'. Expected type: %s",
            ex.getName(), ex.getValue(), ex.getRequiredType()
        ));
        errorResponse.put("parameterName", ex.getName());
        errorResponse.put("parameterValue", ex.getValue());
        errorResponse.put("expectedType", ex.getRequiredType().getSimpleName());
        errorResponse.put("timestamp", System.currentTimeMillis());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }
}