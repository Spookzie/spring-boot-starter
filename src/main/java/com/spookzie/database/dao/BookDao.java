package com.spookzie.database.dao;

import com.spookzie.database.domain.Book;

import java.util.List;
import java.util.Optional;


public interface BookDao
{
    void Create(Book book);

    Optional<Book> FindOne(String isbn);

    List<Book> Find();
}
