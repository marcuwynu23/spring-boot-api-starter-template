package com.marcuwynu23.controllers;

import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.marcuwynu23.models.TodoItem;
import com.marcuwynu23.services.TodoService;

@WebMvcTest(TodoController.class)
class TodoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TodoService todoService;

    @Test
    void listTodosReturnsServiceResult() throws Exception {
        when(todoService.findAll()).thenReturn(List.of(new TodoItem("Test", false)));

        mockMvc.perform(get("/api/todos"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.length()").value(1))
            .andExpect(jsonPath("$[0].title").value("Test"));
    }

    @Test
    void getTodoReturnsTodoWhenFound() throws Exception {
        when(todoService.findById(1L)).thenReturn(new TodoItem("Read docs", true));

        mockMvc.perform(get("/api/todos/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.title").value("Read docs"))
            .andExpect(jsonPath("$.completed").value(true));
    }

    @Test
    void createTodoReturnsCreated() throws Exception {
        when(todoService.create(anyString(), anyBoolean()))
                .thenReturn(new TodoItem("New todo", false));

        mockMvc.perform(post("/api/todos")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"title\":\"New todo\",\"completed\":false}"))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.title").value("New todo"));
    }

    @Test
    void createTodoWithBlankTitleReturnsBadRequest() throws Exception {
        mockMvc.perform(post("/api/todos")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"title\":\"\",\"completed\":false}"))
            .andExpect(status().isBadRequest());
    }

    @Test
    void updateTodoReturnsUpdated() throws Exception {
        when(todoService.update(anyLong(), anyString(), anyBoolean()))
                .thenReturn(new TodoItem("Updated", true));

        mockMvc.perform(put("/api/todos/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"title\":\"Updated\",\"completed\":true}"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.title").value("Updated"))
            .andExpect(jsonPath("$.completed").value(true));
    }

    @Test
    void deleteTodoReturnsNoContent() throws Exception {
        doNothing().when(todoService).delete(1L);

        mockMvc.perform(delete("/api/todos/1"))
            .andExpect(status().isNoContent());

        verify(todoService).delete(1L);
    }
}
