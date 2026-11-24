package com.cesul.barber;

import com.cesul.barber.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
@ActiveProfiles("test")
class BarberApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void clearUsers() {
        userRepository.deleteAll();
    }

    @Test
    void userLifecycleEndpoints() throws Exception {
        String createdUser = mockMvc.perform(post("/users")
                        .contentType("application/json")
                        .content("""
                                {"name":"Juliano","email":"juliano@example.com","avatar":"https://example.com/old.png"}
                                """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is("Juliano")))
                .andReturn()
                .getResponse()
                .getContentAsString();
        String id = com.fasterxml.jackson.databind.json.JsonMapper.builder()
                .build()
                .readTree(createdUser)
                .get("id")
                .asText();

        mockMvc.perform(get("/users/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email", is("juliano@example.com")));

        mockMvc.perform(patch("/users/{id}/avatar", id)
                        .contentType("application/json")
                        .content("""
                                {"avatar":"https://example.com/new.png"}
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.avatar", is("https://example.com/new.png")));

        mockMvc.perform(put("/users/{id}", id)
                        .contentType("application/json")
                        .content("""
                                {"name":"Juliano Silva","email":"juliano.silva@example.com"}
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Juliano Silva")))
                .andExpect(jsonPath("$.avatar").doesNotExist());

        mockMvc.perform(delete("/users/{id}", id))
                .andExpect(status().isNoContent());
        mockMvc.perform(get("/users/{id}", id))
                .andExpect(status().isNotFound());

        mockMvc.perform(post("/users")
                        .contentType("application/json")
                        .content("""
                                {"name":"Outro usuário","email":"outro@example.com"}
                                """))
                .andExpect(status().isCreated());

        mockMvc.perform(delete("/users/clear"))
                .andExpect(status().isNoContent());
        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(0)));
    }

    @Test
    void rejectsInvalidUserData() throws Exception {
        mockMvc.perform(post("/users")
                        .contentType("application/json")
                        .content("""
                                {"name":"","email":"invalid"}
                                """))
                .andExpect(status().isBadRequest());
    }
}
