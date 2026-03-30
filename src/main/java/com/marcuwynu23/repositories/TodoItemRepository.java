package com.marcuwynu23.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.marcuwynu23.models.TodoItem;

// Spring Data repository for CRUD operations on TodoItem entities.
public interface TodoItemRepository extends JpaRepository<TodoItem, Long> {
}
