package com.spookzie.database.services;

import com.spookzie.database.domain.entities.BookEntity;


/*  Service Layer Interface for Book    */
public interface BookService
{
    BookEntity createBook(String isbn, BookEntity book_entity);
}