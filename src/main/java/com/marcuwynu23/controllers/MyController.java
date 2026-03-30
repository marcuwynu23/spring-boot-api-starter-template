package com.marcuwynu23.controllers;

import java.time.Instant;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyController {
    @GetMapping("/")
    public Map<String, Object> root() {
        return Map.of(
            "name", "spring-boot-api-starter",
            "status", "running",
            "health", "/api/health"
        );
    }

    @GetMapping("/api/health")
    public Map<String, Object> health() {
        return Map.of(
            "status", "ok",
            "timestamp", Instant.now().toString()
        );
    }
}
