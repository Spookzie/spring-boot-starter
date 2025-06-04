package com.spookzie.database.services;

import com.spookzie.database.domain.entities.BookEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;


/*  Service Layer Interface for Book    */
public interface BookService
{
    BookEntity createUpdateBook(String isbn, BookEntity book_entity);

    List<BookEntity> findAll();
    Page<BookEntity> findAll(Pageable pageable);

    Optional<BookEntity> findOne(String isbn);

    boolean doesExist(String isbn);

    BookEntity partialUpdate(String isbn, BookEntity book_entity);

    void deleteBook(String isbn);
}