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
import org.springframework.web.server.ResponseStatusException;

import com.marcuwynu23.models.TodoItem;
import com.marcuwynu23.repositories.TodoItemRepository;

// REST controller exposing starter metadata, health, and Todo CRUD endpoints.
@RestController
public class TodoController {
    private final TodoItemRepository todoItemRepository;

    public TodoController(TodoItemRepository todoItemRepository) {
        this.todoItemRepository = todoItemRepository;
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
        return todoItemRepository.findAll();
    }

    @GetMapping("/api/todos/{id}")
    public TodoItem getTodo(@PathVariable Long id) {
        return todoItemRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Todo not found"));
    }

    @PostMapping("/api/todos")
    @ResponseStatus(HttpStatus.CREATED)
    public TodoItem createTodo(@RequestBody TodoRequest request) {
        TodoItem todo = new TodoItem(request.getTitle(), request.isCompleted());
        return todoItemRepository.save(todo);
    }

    @PutMapping("/api/todos/{id}")
    public TodoItem updateTodo(@PathVariable Long id, @RequestBody TodoRequest request) {
        TodoItem existing = todoItemRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Todo not found"));

        existing.setTitle(request.getTitle());
        existing.setCompleted(request.isCompleted());
        return todoItemRepository.save(existing);
    }

    @PatchMapping("/api/todos/{id}/toggle")
    public TodoItem toggleTodo(@PathVariable Long id) {
        TodoItem existing = todoItemRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Todo not found"));

        existing.setCompleted(!existing.isCompleted());
        return todoItemRepository.save(existing);
    }

    @DeleteMapping("/api/todos/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTodo(@PathVariable Long id) {
        if (!todoItemRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Todo not found");
        }
        todoItemRepository.deleteById(id);
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
