package com.spookzie.database.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spookzie.database.TestDataUtil;
import com.spookzie.database.domain.dto.BookDto;
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

import java.awt.print.Book;


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


    //  PUT - Create Tests (Update Tests are later) //
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
    public void testThatCreateBookReturnsSavedUpdateBook() throws Exception
    {
        BookDto testBookDto = TestDataUtil.createTestBookDtoA(null);
        String bookJson = this.objectMapper.writeValueAsString(testBookDto);

        this.mockMvc.perform(
                MockMvcRequestBuilders.put("/books/" + testBookDto.getIsbn())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.isbn").value("978-1-2345-6789-0")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.title").value("The Shadow in the Attic")
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
        this.bookService.createUpdateBook(testBookEntity.getIsbn(), testBookEntity);

        this.mockMvc.perform(
                MockMvcRequestBuilders.get("/books")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].isbn").value("978-1-2345-6789-0")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].title").value("The Shadow in the Attic")
        );
    }


    //  GET - Read One Tests    //
    @Test
    public void testThatGetBookReturnsHttp200WhenBookExists() throws Exception
    {
        BookEntity testBookEntity = TestDataUtil.createTestBookEntityA(null);
        this.bookService.createUpdateBook(testBookEntity.getIsbn(), testBookEntity);

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
        this.bookService.createUpdateBook(testBookEntity.getIsbn(), testBookEntity);


        this.mockMvc.perform(
                MockMvcRequestBuilders.get("/books/" + testBookEntity.getIsbn())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.isbn").value("978-1-2345-6789-0")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.title").value("The Shadow in the Attic")
        );
    }


    //  PUT - Update Tests  //
    @Test
    public void testThatUpdateBookReturnsHttp200() throws Exception
    {
        BookEntity testBookEntity = TestDataUtil.createTestBookEntityA(null);
        BookEntity savedTestBookEntity = this.bookService.createUpdateBook(testBookEntity.getIsbn(), testBookEntity);

        BookDto testBookDto = TestDataUtil.createTestBookDtoA(null);
        testBookDto.setIsbn(savedTestBookEntity.getIsbn());
        String testBookJson = this.objectMapper.writeValueAsString(testBookDto);

        this.mockMvc.perform(
                MockMvcRequestBuilders.put("/books/" + testBookDto.getIsbn())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(testBookJson)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    /*  Testing that the book is updated with correct values */
    @Test
    public void testThatUpdateBookReturnsSavedUpdateBook() throws Exception
    {
        BookEntity testBookEntity = TestDataUtil.createTestBookEntityA(null);
        BookEntity savedTestBookEntity = this.bookService.createUpdateBook(testBookEntity.getIsbn(), testBookEntity);

        BookDto testBookDto = TestDataUtil.createTestBookDtoA(null);
        testBookDto.setIsbn(savedTestBookEntity.getIsbn());
        testBookDto.setTitle("UPDATED");
        String testBookJson = this.objectMapper.writeValueAsString(testBookDto);

        this.mockMvc.perform(
                MockMvcRequestBuilders.put("/books/" + testBookDto.getIsbn())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(testBookJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.isbn").value("978-1-2345-6789-0")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.title").value("UPDATED")
        );
    }


    //  PATCH - Partial Update Tests    //
    @Test
    public void testThatPartialUpdateBookReturnsHttp200() throws Exception
    {
        BookEntity testBookEntity = TestDataUtil.createTestBookEntityA(null);
        BookEntity savedTestBook = this.bookService.createUpdateBook(testBookEntity.getIsbn(), testBookEntity);

        BookDto testBookDto = TestDataUtil.createTestBookDtoA(null);
        testBookDto.setTitle("UPDATED");
        String testBookJson = this.objectMapper.writeValueAsString(testBookDto);

        this.mockMvc.perform(
                MockMvcRequestBuilders.patch("/books/" + savedTestBook.getIsbn())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(testBookJson)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testThatPartialUpdateBookUpdatesWithCorrectValues() throws Exception
    {
        BookEntity testBookEntity = TestDataUtil.createTestBookEntityA(null);
        BookEntity savedTestBook = this.bookService.createUpdateBook(testBookEntity.getIsbn(), testBookEntity);

        BookDto testBookDto = TestDataUtil.createTestBookDtoA(null);
        testBookDto.setTitle("UPDATED");
        String testBookJson = this.objectMapper.writeValueAsString(testBookDto);

        this.mockMvc.perform(
                MockMvcRequestBuilders.patch("/books/" + savedTestBook.getIsbn())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(testBookJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.isbn").value(savedTestBook.getIsbn())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.title").value(testBookDto.getTitle())
        );
    }


    // DELETE - Delete Tests    //
    @Test
    public void testThatDeleteBookReturnsHttp204() throws Exception
     {
        this.mockMvc.perform(MockMvcRequestBuilders.delete("/books/777")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isNoContent()
        );
    }
}