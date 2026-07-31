package com.marcuwynu23.controllers;

import java.time.Instant;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.marcuwynu23.models.TodoItem;
import com.marcuwynu23.services.TodoService;

// REST controller exposing starter metadata, health, and Todo CRUD endpoints.
@RestController
public class TodoController {
    private final TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    // Root endpoint to quickly verify the API is running.
    @GetMapping("/")
    public Map<String, Object> root() {
        return Map.of(
                "name", "spring-boot-api-starter",
                "status", "running",
                "health", "/api/health",
                "todos", "/api/todos");
    }

    // Simple liveness check endpoint for local/dev monitoring.
    @GetMapping("/api/health")
    public Map<String, Object> health() {
        return Map.of(
                "status", "ok",
                "timestamp", Instant.now().toString());
    }

    @GetMapping("/api/todos")
    public List<TodoItem> listTodos() {
        return todoService.findAll();
    }

    @GetMapping("/api/todos/{id}")
    public TodoItem getTodo(@PathVariable Long id) {
        return todoService.findById(id);
    }

    @PostMapping("/api/todos")
    @ResponseStatus(HttpStatus.CREATED)
    public TodoItem createTodo(@RequestBody TodoRequest request) {
        return todoService.create(request.getTitle(), request.isCompleted());
    }

    @PutMapping("/api/todos/{id}")
    public TodoItem updateTodo(@PathVariable Long id, @RequestBody TodoRequest request) {
        return todoService.update(id, request.getTitle(), request.isCompleted());
    }

    @PatchMapping("/api/todos/{id}/toggle")
    public TodoItem toggleTodo(@PathVariable Long id) {
        return todoService.toggle(id);
    }

    @DeleteMapping("/api/todos/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTodo(@PathVariable Long id) {
        todoService.delete(id);
    }

    public static class TodoRequest {
        private String title;

        private boolean completed;

        public TodoRequest() {
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public boolean isCompleted() {
            return completed;
        }

        public void setCompleted(boolean completed) {
            this.completed = completed;
        }
    }
}
