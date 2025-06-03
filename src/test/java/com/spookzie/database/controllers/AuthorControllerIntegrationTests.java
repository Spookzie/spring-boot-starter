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


    //  POST - Create Tests //
    @Test
    public void testThatCreateAuthorReturnsHttp201() throws Exception
     {
        AuthorDto testAuthorDto = TestDataUtil.createTestAuthorDtoA();
        String testAuthorJson = this.objectMapper.writeValueAsString(testAuthorDto);  // Converting AuthorDto to JSON string

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


    //  GET - Read Many Tests   //
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
        this.authorService.saveAuthor(testAuthorEntityA);

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


    //  GET - Read One Tests    //
    @Test
    public void testThatGetAuthorReturnsHttp200WhenAuthorExists() throws Exception
    {
        AuthorEntity testAuthorEntityA = TestDataUtil.createTestAuthorEntityA();
        this.authorService.saveAuthor(testAuthorEntityA);

        this.mockMvc.perform(
                MockMvcRequestBuilders.get("/authors/1")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testThatGetAuthorReturnsHttp404WhenNoAuthorExist() throws Exception
    {
        this.mockMvc.perform(
                MockMvcRequestBuilders.get("/authors/99")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isNotFound()
        );
    }

    /*  Testing that a single author is read with correct values  */
    @Test
    public void testThatGetAuthorReturnsAuthorWhenExists() throws Exception
    {
        AuthorEntity testAuthorEntityA = TestDataUtil.createTestAuthorEntityA();
        this.authorService.saveAuthor(testAuthorEntityA);

        this.mockMvc.perform(
                MockMvcRequestBuilders.get("/authors/1")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").value("Abigail Rose")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.age").value(80)
        );
    }


    //  PUT - Full Update Tests //
    @Test
    public void testThatFullUpdateAuthorReturnsHttp404WhenNoAuthorExist() throws Exception
    {
        AuthorDto testAuthorDto = TestDataUtil.createTestAuthorDtoA();
        String testAuthorJson = this.objectMapper.writeValueAsString(testAuthorDto);

        this.mockMvc.perform(
                MockMvcRequestBuilders.put("/authors/99")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(testAuthorJson)
        ).andExpect(
                MockMvcResultMatchers.status().isNotFound()
        );
    }

    @Test
    public void testThatFullUpdateAuthorReturnsHttp200WhenAuthorExist() throws Exception
    {
        AuthorEntity testAuthorEntity = TestDataUtil.createTestAuthorEntityA();
        AuthorEntity savedTestAuthorEntity = this.authorService.saveAuthor(testAuthorEntity);

        AuthorDto testAuthorDto = TestDataUtil.createTestAuthorDtoA();
        String testAuthorJson = this.objectMapper.writeValueAsString(testAuthorDto);

        this.mockMvc.perform(
                MockMvcRequestBuilders.put("/authors/" + savedTestAuthorEntity.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(testAuthorJson)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    /*  Testing that the author is fully updated with correct values  */
    @Test
    public void testThatFullUpdateAuthorUpdatesExistingAuthor() throws Exception
    {
        AuthorEntity testAuthorEntityA = TestDataUtil.createTestAuthorEntityA();
        AuthorEntity savedTestAuthorA = this.authorService.saveAuthor(testAuthorEntityA);

        AuthorEntity testAuthorEntityB = TestDataUtil.createTestAuthorB();
        testAuthorEntityB.setId(savedTestAuthorA.getId());
        String testAuthorBJson = this.objectMapper.writeValueAsString(testAuthorEntityB);


        this.mockMvc.perform(
                MockMvcRequestBuilders.put("/authors/" + savedTestAuthorA.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(testAuthorBJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").value(savedTestAuthorA.getId())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").value(testAuthorEntityB.getName())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.age").value(testAuthorEntityB.getAge())
        );
    }


    //  PATCH - Partial Update Tests    //
    @Test
    public void testThatPartialUpdateAuthorReturnsHttp200() throws Exception
    {
        AuthorEntity testAuthorEntity = TestDataUtil.createTestAuthorEntityA();
        AuthorEntity savedTestAuthorEntity = this.authorService.saveAuthor(testAuthorEntity);

        AuthorDto testAuthorDto = TestDataUtil.createTestAuthorDtoA();
        testAuthorDto.setName("UPDATED");
        String testAuthorJson = this.objectMapper.writeValueAsString(testAuthorDto);

        this.mockMvc.perform(
                MockMvcRequestBuilders.patch("/authors/" + savedTestAuthorEntity.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(testAuthorJson)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    /*  Testing that the author is fully updated with correct values  */
    @Test
    public void testThatPartialUpdateAuthorUpdatesExistingAuthor() throws Exception
    {
        AuthorEntity testAuthorEntity = TestDataUtil.createTestAuthorEntityA();
        AuthorEntity savedTestAuthorEntity = this.authorService.saveAuthor(testAuthorEntity);

        AuthorDto testAuthorDto = TestDataUtil.createTestAuthorDtoA();
        testAuthorDto.setName("UPDATED");
        String authorDtoJson = this.objectMapper.writeValueAsString(testAuthorDto);


        this.mockMvc.perform(
                MockMvcRequestBuilders.patch("/authors/" + savedTestAuthorEntity.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(authorDtoJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").value(savedTestAuthorEntity.getId())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").value(testAuthorDto.getName())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.age").value(savedTestAuthorEntity.getAge())
        );
    }


    //  DELETE - Delete Tests   //
    @Test
    public void testThatDeleteAuthorReturnsHttp204() throws Exception
    {
        /* No need to save an author, as deleting an existing author or deleting a non-existent author is same  */

        this.mockMvc.perform(
                MockMvcRequestBuilders.delete("/authors/777")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isNoContent()
        );
    }
}