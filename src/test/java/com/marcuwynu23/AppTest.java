package com.marcuwynu23;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.Matchers.emptyOrNullString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AppTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void contextLoads() {
    }

    @Test
    void healthEndpointReturnsExpectedContract() throws Exception {
        mockMvc.perform(get("/api/health"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith("application/json"))
                .andExpect(jsonPath("$.status").value("ok"))
                .andExpect(jsonPath("$.timestamp").value(not(emptyOrNullString())));
    }

    @Test
    void rootEndpointReturnsStarterInfo() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith("application/json"))
                .andExpect(jsonPath("$.name").value("spring-boot-api-starter"))
                .andExpect(jsonPath("$.status").value("running"))
                .andExpect(jsonPath("$.health").value("/api/health"))
                .andExpect(jsonPath("$.todos").value("/api/todos"));
    }

    @Test
    void todosEndpointReturnsArray() throws Exception {
        mockMvc.perform(get("/api/todos"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith("application/json"))
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void todoCrudFlowWorks() throws Exception {
        String createPayload = "{\"title\":\"Write integration test\",\"completed\":false}";
        String created = mockMvc.perform(post("/api/todos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(createPayload))
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith("application/json"))
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.title").value("Write integration test"))
                .andExpect(jsonPath("$.completed").value(false))
                .andReturn()
                .getResponse()
                .getContentAsString();

        Long id = objectMapper.readTree(created).get("id").asLong();

        mockMvc.perform(get("/api/todos/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.title").value("Write integration test"))
                .andExpect(jsonPath("$.completed").value(false));

        String updatePayload = "{\"title\":\"Write integration test (updated)\",\"completed\":true}";
        mockMvc.perform(put("/api/todos/" + id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(updatePayload))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Write integration test (updated)"))
                .andExpect(jsonPath("$.completed").value(true));

        mockMvc.perform(patch("/api/todos/" + id + "/toggle"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.completed", is(false)));

        mockMvc.perform(delete("/api/todos/" + id))
                .andExpect(status().isNoContent())
                .andExpect(header().string("Content-Length", nullValue()));

        mockMvc.perform(get("/api/todos/" + id))
                .andExpect(status().isNotFound());
    }
}
