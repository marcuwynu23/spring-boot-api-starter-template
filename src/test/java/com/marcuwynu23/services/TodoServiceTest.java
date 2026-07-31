package com.marcuwynu23.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.marcuwynu23.exceptions.TodoNotFoundException;
import com.marcuwynu23.models.TodoItem;
import com.marcuwynu23.repositories.TodoItemRepository;

@ExtendWith(MockitoExtension.class)
class TodoServiceTest {

    @Mock
    private TodoItemRepository todoItemRepository;

    @InjectMocks
    private TodoService todoService;

    @Test
    void findAllReturnsAllTodos() {
        when(todoItemRepository.findAll()).thenReturn(List.of(
                new TodoItem("First", false),
                new TodoItem("Second", true)));

        List<TodoItem> result = todoService.findAll();

        assertEquals(2, result.size());
        assertEquals("First", result.get(0).getTitle());
        assertTrue(result.get(1).isCompleted());
        verify(todoItemRepository).findAll();
    }

    @Test
    void findByIdReturnsTodoWhenPresent() {
        TodoItem todo = new TodoItem("Read docs", true);
        when(todoItemRepository.findById(1L)).thenReturn(Optional.of(todo));

        TodoItem result = todoService.findById(1L);

        assertSame(todo, result);
    }

    @Test
    void findByIdThrowsWhenAbsent() {
        when(todoItemRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(TodoNotFoundException.class, () -> todoService.findById(99L));
    }

    @Test
    void createSavesNewTodo() {
        when(todoItemRepository.save(any(TodoItem.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        TodoItem result = todoService.create("New task", true);

        assertEquals("New task", result.getTitle());
        assertTrue(result.isCompleted());
        verify(todoItemRepository).save(any(TodoItem.class));
    }

    @Test
    void updateModifiesExistingTodo() {
        TodoItem existing = new TodoItem("Old title", false);
        when(todoItemRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(todoItemRepository.save(any(TodoItem.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        TodoItem result = todoService.update(1L, "New title", true);

        assertEquals("New title", result.getTitle());
        assertTrue(result.isCompleted());
    }

    @Test
    void updateThrowsWhenAbsent() {
        when(todoItemRepository.findById(7L)).thenReturn(Optional.empty());

        assertThrows(TodoNotFoundException.class, () -> todoService.update(7L, "x", false));
        verify(todoItemRepository, never()).save(any(TodoItem.class));
    }

    @Test
    void toggleFlipsCompleted() {
        TodoItem existing = new TodoItem("Toggle me", false);
        when(todoItemRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(todoItemRepository.save(any(TodoItem.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        assertTrue(todoService.toggle(1L).isCompleted());
        assertFalse(todoService.toggle(1L).isCompleted());
    }

    @Test
    void toggleThrowsWhenAbsent() {
        when(todoItemRepository.findById(3L)).thenReturn(Optional.empty());

        assertThrows(TodoNotFoundException.class, () -> todoService.toggle(3L));
    }

    @Test
    void deleteRemovesExistingTodo() {
        when(todoItemRepository.existsById(1L)).thenReturn(true);

        todoService.delete(1L);

        verify(todoItemRepository).deleteById(1L);
    }

    @Test
    void deleteThrowsWhenAbsent() {
        when(todoItemRepository.existsById(5L)).thenReturn(false);

        assertThrows(TodoNotFoundException.class, () -> todoService.delete(5L));
        verify(todoItemRepository, never()).deleteById(anyLong());
    }
}
