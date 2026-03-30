package com.marcuwynu23.controllers;

import java.time.Instant;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.marcuwynu23.models.SampleItem;
import com.marcuwynu23.repositories.SampleItemRepository;

@RestController
public class Controller {
    private final SampleItemRepository sampleItemRepository;

    public Controller(SampleItemRepository sampleItemRepository) {
        this.sampleItemRepository = sampleItemRepository;
    }

    @GetMapping("/")
    public Map<String, Object> root() {
        return Map.of(
                "name", "spring-boot-api-starter",
                "status", "running",
                "health", "/api/health",
                "sampleItems", "/api/items");
    }

    @GetMapping("/api/health")
    public Map<String, Object> health() {
        return Map.of(
                "status", "ok",
                "timestamp", Instant.now().toString());
    }

    @GetMapping("/api/items")
    public List<SampleItem> listItems() {
        return sampleItemRepository.findAll();
    }
}
