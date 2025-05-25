package com.spookzie.database.services;

import com.spookzie.database.domain.entities.BookEntity;


public interface BookService
{
    BookEntity createBook(String isbn, BookEntity book_entity);
}