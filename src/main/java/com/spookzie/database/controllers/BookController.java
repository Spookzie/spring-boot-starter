package com.spookzie.database.controllers;

import com.spookzie.database.domain.dto.BookDto;
import com.spookzie.database.domain.entities.BookEntity;
import com.spookzie.database.mappers.Mapper;
import com.spookzie.database.services.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class BookController
{
    private final BookService bookService;
    private final Mapper<BookEntity, BookDto> bookMapper;


    public BookController(BookService book_service, Mapper<BookEntity, BookDto> book_mapper)
    {
        this.bookService = book_service;
        this.bookMapper = book_mapper;
    }


    /*
    * PUT - Create
    * We use PUT instead of POST because we want to specify the id ourselves
    ************************************************/
    @PutMapping("/books/{isbn}")
    public ResponseEntity<BookDto> createBook(
            @PathVariable("isbn") String isbn,
            @RequestBody BookDto book_dto)
    {
        BookEntity bookEntity = this.bookMapper.mapFrom(book_dto);
        BookEntity savedBookEntity = this.bookService.createBook(isbn, bookEntity); // Saving (Creating) the Entity
        BookDto savedBookDto = this.bookMapper.mapTo(savedBookEntity);

        return new ResponseEntity<>(savedBookDto, HttpStatus.CREATED);  // Returning the updated info to client with status 201
    }
}