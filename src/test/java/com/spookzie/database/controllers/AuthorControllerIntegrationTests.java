package com.spookzie.database.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spookzie.database.TestDataUtil;
import com.spookzie.database.domain.dto.AuthorDto;
import com.spookzie.database.domain.entities.AuthorEntity;
import com.spookzie.database.services.AuthorService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class AuthorControllerIntegrationTests
{
    private final AuthorService authorService;
    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;


    @Autowired
    public AuthorControllerIntegrationTests(AuthorService author_service, MockMvc mock_mvc)
    {
        this.authorService = author_service;
        this.mockMvc = mock_mvc;
        this.objectMapper = new ObjectMapper();
    }


    @Test
    public void testThatCreateAuthorReturnsHttp201() throws Exception
     {
        AuthorDto testAuthorDto = TestDataUtil.createTestAuthorDtoA();
        String testAuthorJson = this.objectMapper.writeValueAsString(testAuthorDto);  // Converting AuthorEntity object to JSON string

        this.mockMvc.perform(
                MockMvcRequestBuilders.post("/authors")
                        .contentType(MediaType.APPLICATION_JSON)    // Tells Spring to treat the request body as JSON
                        .content(testAuthorJson)    // Adds JSON string as the body of request
        ).andExpect(
                MockMvcResultMatchers.status().isCreated()
        );
    }

    /*  Testing that the author is posted with correct values   */
    @Test
    public void testThatCreateAuthorReturnsSavedAuthor() throws Exception
     {
        AuthorDto testAuthorDto = TestDataUtil.createTestAuthorDtoA();
        String authorJson = this.objectMapper.writeValueAsString(testAuthorDto);

        this.mockMvc.perform(
                MockMvcRequestBuilders.post("/authors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(authorJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").value("Abigail Rose")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.age").value(80)
        );
    }


    @Test
    public void testThatListAuthorsReturnsHttp200() throws Exception
    {
        this.mockMvc.perform(
                MockMvcRequestBuilders.get("/authors")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }


    /*  Testing that multiple authors are read with correct values  */
    @Test
    public void testThatListAuthorsReturnsListOfAuthors() throws Exception
    {
        AuthorEntity testAuthorEntityA = TestDataUtil.createTestAuthorEntityA();
        this.authorService.createAuthor(testAuthorEntityA);

        this.mockMvc.perform(
                MockMvcRequestBuilders.get("/authors")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].id").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].name").value("Abigail Rose")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].age").value(80)
        );
    }
}