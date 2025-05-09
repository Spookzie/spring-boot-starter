package com.spookzie.database.dao;

import com.spookzie.database.domain.Book;

import java.util.List;
import java.util.Optional;


public interface BookDao
{
    void Create(Book book); // C

    Optional<Book> FindOne(String isbn);    // R
    List<Book> Find();  // R

    void Update(String isbn, Book book);    // U
}
