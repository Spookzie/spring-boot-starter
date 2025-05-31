package com.spookzie.database.controllers;

import com.spookzie.database.domain.dto.BookDto;
import com.spookzie.database.domain.entities.BookEntity;
import com.spookzie.database.mappers.Mapper;
import com.spookzie.database.services.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


/*
 * Defining CRUD operations using REST API principles & design
 * Setting up HTTP methods
 *****************************************************/
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
    * We use PUT instead of POST because we want to specify the id (isbn in this case) ourselves
    ************************************************/
    @PutMapping(path = "/books/{isbn}")
    public ResponseEntity<BookDto> createBook(
            @PathVariable("isbn") String isbn,
            @RequestBody BookDto book_dto)
    {
        BookEntity bookEntity = this.bookMapper.mapFrom(book_dto);
        BookEntity savedBookEntity = this.bookService.createBook(isbn, bookEntity); // Saving (Creating) the Entity
        BookDto savedBookDto = this.bookMapper.mapTo(savedBookEntity);

        return new ResponseEntity<>(savedBookDto, HttpStatus.CREATED);  // Returning the updated info to client with status 201
    }


    // GET - Read Many
    @GetMapping(path = "/books")
    public List<BookDto> listBooks()
    {
        List<BookEntity> books = this.bookService.findAll();

        return books.stream()
                .map(this.bookMapper::mapTo)
                .collect(Collectors.toList());
    }


    // GET - Read One
    @GetMapping(path = "/books/{isbn}")
    public ResponseEntity<BookDto> getBook(@PathVariable("isbn") String isbn)
    {
        Optional<BookEntity> foundBook = this.bookService.findOne(isbn);

        return foundBook.map(
                bookEntity -> {
                    BookDto bookDto = this.bookMapper.mapTo(bookEntity);
                    return new ResponseEntity<>(bookDto, HttpStatus.OK);
                }
        ).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}