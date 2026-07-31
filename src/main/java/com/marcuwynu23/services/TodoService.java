package com.marcuwynu23.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.marcuwynu23.exceptions.TodoNotFoundException;
import com.marcuwynu23.models.TodoItem;
import com.marcuwynu23.repositories.TodoItemRepository;

// Business logic layer. Kept thin here but isolates persistence from the web layer.
@Service
public class TodoService {
    private final TodoItemRepository todoItemRepository;

    public TodoService(TodoItemRepository todoItemRepository) {
        this.todoItemRepository = todoItemRepository;
    }

    public List<TodoItem> findAll() {
        return todoItemRepository.findAll();
    }

    public TodoItem findById(Long id) {
        return todoItemRepository.findById(id)
                .orElseThrow(() -> new TodoNotFoundException(id));
    }

    public TodoItem create(String title, boolean completed) {
        return todoItemRepository.save(new TodoItem(title, completed));
    }

    public TodoItem update(Long id, String title, boolean completed) {
        TodoItem existing = findById(id);
        existing.setTitle(title);
        existing.setCompleted(completed);
        return todoItemRepository.save(existing);
    }

    public TodoItem toggle(Long id) {
        TodoItem existing = findById(id);
        existing.setCompleted(!existing.isCompleted());
        return todoItemRepository.save(existing);
    }

    public void delete(Long id) {
        if (!todoItemRepository.existsById(id)) {
            throw new TodoNotFoundException(id);
        }
        todoItemRepository.deleteById(id);
    }
}
