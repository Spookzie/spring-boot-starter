package com.spookzie.database.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spookzie.database.TestDataUtil;
import com.spookzie.database.domain.dto.BookDto;
import com.spookzie.database.domain.entities.AuthorEntity;
import com.spookzie.database.domain.entities.BookEntity;
import com.spookzie.database.services.BookService;
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
    private final BookService bookService;
    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;


    @Autowired
    public BookControllerIntegrationTests(BookService book_service, MockMvc mock_mvc)
    {
        this.bookService = book_service;
        this.mockMvc = mock_mvc;
        this.objectMapper = new ObjectMapper();
    }


    //  PUT - Create Tests  //
    @Test
    public void testThatCreateBookReturnsHttp201() throws Exception
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

    /*  Testing that the book is posted with correct values */
    @Test
    public void testThatCreateBookReturnsSavedBook() throws Exception
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


    //  GET - Read Many Tests   //
    @Test
    public void testThatListBooksReturnsHttp200() throws Exception
    {
        this.mockMvc.perform(
                MockMvcRequestBuilders.get("/books")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    /*  Testing that multiple books are read with correct values    */
    @Test
    public void testThatListBooksReturnsListOfBooks() throws Exception
    {
        BookEntity testBookEntity = TestDataUtil.createTestBookEntityA(null);
        this.bookService.createBook(testBookEntity.getIsbn(), testBookEntity);

        this.mockMvc.perform(
                MockMvcRequestBuilders.get("/books")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].isbn").value(testBookEntity.getIsbn())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].title").value(testBookEntity.getTitle())
        );
    }


    //  GET - Read One Tests    //
    @Test
    public void testThatGetBookReturnsHttp200WhenBookExists() throws Exception
    {
        BookEntity testBookEntity = TestDataUtil.createTestBookEntityA(null);
        this.bookService.createBook(testBookEntity.getIsbn(), testBookEntity);

        this.mockMvc.perform(
                MockMvcRequestBuilders.get("/books/" + testBookEntity.getIsbn())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testThatGetBookReturnsHttp404WhenNoBookExists() throws Exception
    {
        BookEntity testBookEntity = TestDataUtil.createTestBookEntityA(null);

        this.mockMvc.perform(
                MockMvcRequestBuilders.get("/books/" + testBookEntity.getIsbn())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isNotFound()
        );
    }

    /*  Testing that a single book is read with correct values  */
    @Test
    public void testThatGetBookReturnsBookWhenExists() throws Exception
    {
        BookEntity testBookEntity = TestDataUtil.createTestBookEntityA(null);
        this.bookService.createBook(testBookEntity.getIsbn(), testBookEntity);


        this.mockMvc.perform(
                MockMvcRequestBuilders.get("/books/" + testBookEntity.getIsbn())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.isbn").value(testBookEntity.getIsbn())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.title").value(testBookEntity.getTitle())
        );
    }
}