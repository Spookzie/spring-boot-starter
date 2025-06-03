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
    * PUT - Create & Full Update
    * We use PUT for creating instead of POST because we want to specify the id (isbn in this case) ourselves
    ************************************************/
    @PutMapping(path = "/books/{isbn}")
    public ResponseEntity<BookDto> createUpdateBook(@PathVariable("isbn") String isbn, @RequestBody BookDto book_dto)
    {
        BookEntity bookEntity = this.bookMapper.mapFrom(book_dto);

        boolean bookExists = this.bookService.doesExist(isbn);  // We check before saving the book to make sure what HTTP Status to output

        BookEntity savedBookEntity = this.bookService.createUpdateBook(isbn, bookEntity); // Saving (Creating) the Entity
        BookDto savedBookDto = this.bookMapper.mapTo(savedBookEntity);

        if(bookExists)
            return new ResponseEntity<>(savedBookDto, HttpStatus.OK);
        else
            return new ResponseEntity<>(savedBookDto, HttpStatus.CREATED);  // Returning the updated info to client with status 20
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


    // PATCH - Partial Update
    @PatchMapping(path = "/books/{isbn}")
    public ResponseEntity<BookDto> partialUpdateBook(@PathVariable("isbn") String isbn, @RequestBody BookDto book_dto)
    {
        if(!this.bookService.doesExist(isbn))
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        BookEntity bookEntity = this.bookMapper.mapFrom(book_dto);
        BookEntity updatedBookEntity = this.bookService.partialUpdate(isbn, bookEntity);
        BookDto updatedBookDto = this.bookMapper.mapTo(updatedBookEntity);

        return new ResponseEntity<>(updatedBookDto, HttpStatus.OK);
    }


    // DELETE
    @DeleteMapping(path = "/books/{isbn}")
    public ResponseEntity deleteBook(@PathVariable("isbn") String isbn)
    {
        this.bookService.deleteBook(isbn);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}