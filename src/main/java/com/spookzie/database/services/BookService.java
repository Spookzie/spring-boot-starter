package com.spookzie.database.services;

import com.spookzie.database.domain.entities.BookEntity;

import java.util.List;
import java.util.Optional;


/*  Service Layer Interface for Book    */
public interface BookService
{
    BookEntity createBook(String isbn, BookEntity book_entity);

    List<BookEntity> findAll();

    Optional<BookEntity> findOne(String isbn);
}