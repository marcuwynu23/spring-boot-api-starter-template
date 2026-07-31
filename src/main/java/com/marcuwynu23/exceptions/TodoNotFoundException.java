package com.marcuwynu23.exceptions;

public class TodoNotFoundException extends RuntimeException {
    public TodoNotFoundException(Long id) {
        super("Todo not found: " + id);
    }
}
