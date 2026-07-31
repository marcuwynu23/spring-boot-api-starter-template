package com.marcuwynu23.exceptions;

import java.time.Instant;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

// Centralized error handling so controllers and services stay free of status-code concerns.
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(TodoNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, Object> handleTodoNotFound(TodoNotFoundException ex) {
        return Map.of(
                "status", HttpStatus.NOT_FOUND.value(),
                "error", "Not Found",
                "message", ex.getMessage(),
                "timestamp", Instant.now().toString());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, Object> handleValidation(MethodArgumentNotValidException ex) {
        String message = ex.getBindingResult().getFieldError() != null
                ? ex.getBindingResult().getFieldError().getDefaultMessage()
                : "Validation failed";
        return Map.of(
                "status", HttpStatus.BAD_REQUEST.value(),
                "error", "Bad Request",
                "message", message,
                "timestamp", Instant.now().toString());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, Object> handleUnreadableBody(HttpMessageNotReadableException ex) {
        return Map.of(
                "status", HttpStatus.BAD_REQUEST.value(),
                "error", "Bad Request",
                "message", "Malformed JSON request body",
                "timestamp", Instant.now().toString());
    }
}
