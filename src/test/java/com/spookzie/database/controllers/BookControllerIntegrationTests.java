package com.spookzie.database.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spookzie.database.TestDataUtil;
import com.spookzie.database.domain.dto.AuthorDto;
import com.spookzie.database.domain.dto.BookDto;
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
public class BookControllerIntegrationTests
{
    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;


    @Autowired
    public BookControllerIntegrationTests(MockMvc mock_mvc, ObjectMapper object_mapper)
    {
        this.mockMvc = mock_mvc;
        this.objectMapper = new ObjectMapper();
    }


    @Test
    public void testThatCreateBookSuccessfullyReturnsHttp201Created() throws Exception
    {
        BookDto testBookDto = TestDataUtil.createTestBookDtoA(null);
        String testBookJson = this.objectMapper.writeValueAsString(testBookDto);  // Converting BookDto to JSON string

        this.mockMvc.perform(
                MockMvcRequestBuilders.put("/books/" + testBookDto.getIsbn())
                        .contentType(MediaType.APPLICATION_JSON)    // Tells Spring to treat the request body as JSON
                        .content(testBookJson)  // Adds JSON string as the body of request
        ).andExpect(
                MockMvcResultMatchers.status().isCreated()
        );
    }


    /*  Testing that the author is posted with correct values   */
    @Test
    public void testThatCreateBookSuccessfullyReturnsSavedBook() throws Exception
    {
        BookDto testBookDto = TestDataUtil.createTestBookDtoA(null);
        String bookJson = this.objectMapper.writeValueAsString(testBookDto);

        this.mockMvc.perform(
                MockMvcRequestBuilders.put("/books/" + testBookDto.getIsbn())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.isbn").value(testBookDto.getIsbn())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.title").value(testBookDto.getTitle())
        );
    }
}