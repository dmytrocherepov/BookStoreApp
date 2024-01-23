package com.example.bookstoreapp.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.bookstoreapp.dto.category.CategoryRequestDto;
import com.example.bookstoreapp.dto.category.CategoryResponseDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jdk.jfr.Description;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlMergeMode;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = {
        "classpath:databases/categories/delete-categories.sql"},
        executionPhase = AFTER_TEST_METHOD)
@SqlMergeMode(SqlMergeMode.MergeMode.MERGE)
public class CategoryControllerTest {
    protected static MockMvc mockMvc;
    private static ObjectMapper objectMapper;

    @BeforeAll
    @SneakyThrows
    static void beforeAll(@Autowired WebApplicationContext applicationContext) {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(applicationContext)
                .apply(springSecurity())
                .build();
        objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
    }

    @Test
    @Description("Creates a category")
    @WithMockUser(username = "admin", roles = "ADMIN")
    void createCategory_validRequestDto_Success() throws Exception {
        CategoryRequestDto requestDto = new CategoryRequestDto(
                "test",
                "test"
        );

        MvcResult result = mockMvc.perform(post("/categories")
                        .content(objectMapper.writeValueAsString(requestDto))
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();

        CategoryResponseDto responseDto = objectMapper.readValue(
                result.getResponse().getContentAsString(),
                CategoryResponseDto.class);

        assertThat(responseDto).isNotNull()
                .hasFieldOrPropertyWithValue("name", requestDto.name())
                .hasFieldOrPropertyWithValue("description", requestDto.description());
    }

    @Test
    @Description("Get a category with existing id")
    @Sql(scripts = "classpath:databases/categories/add-categories.sql")
    @WithMockUser(username = "user", roles = "USER")
    void getCategoryById_validId_Success() throws Exception {
        Long id = 1L;

        MvcResult result = mockMvc.perform(get("/categories/" + id)
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        CategoryResponseDto actual = objectMapper.readValue(
                result.getResponse().getContentAsString(),
                CategoryResponseDto.class);

        assertThat(actual).isNotNull()
                .hasFieldOrPropertyWithValue("id", id)
                .hasFieldOrPropertyWithValue("name", "test1");
    }

    @Test
    @Description("Updates a category")
    @Sql(scripts = "classpath:databases/categories/add-categories.sql")
    @WithMockUser(username = "admin", roles = "ADMIN")
    void updateCategory_validRequestDtoAndId_Success() throws Exception {
        Long id = 1L;
        CategoryRequestDto requestDto = new CategoryRequestDto(
                "testName",
                "testDesc"
        );
        MvcResult result = mockMvc.perform(put("/categories/" + id)
                        .content(objectMapper.writeValueAsString(requestDto))
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        CategoryResponseDto dto = objectMapper.readValue(
                result.getResponse().getContentAsString(),
                CategoryResponseDto.class
        );

        assertThat(dto).isNotNull()
                .hasFieldOrPropertyWithValue("id", id)
                .hasFieldOrPropertyWithValue("name", requestDto.name())
                .hasFieldOrPropertyWithValue("description", requestDto.description());
    }

    @Test
    @Description("Delete a category with existing id")
    @WithMockUser(username = "admin", roles = "ADMIN")
    void deleteCategory_validId_Success() throws Exception {
        Long id = 1L;
        MvcResult result = mockMvc.perform(delete("/categories/" + id)
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andReturn();
    }
}
