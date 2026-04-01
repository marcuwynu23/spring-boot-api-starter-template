package com.marcuwynu23.controllers;

import java.time.Instant;

import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {
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
}
