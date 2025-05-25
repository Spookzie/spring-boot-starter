package com.spookzie.database.services.impl;

import com.spookzie.database.domain.entities.BookEntity;
import com.spookzie.database.repositories.BookRepository;
import com.spookzie.database.services.BookService;
import org.springframework.stereotype.Service;


/*  Defines how book is created */
@Service
public class BookServiceImpl implements BookService
{
    private final BookRepository bookRepository;


    public BookServiceImpl(BookRepository book_repository)
    {
        this.bookRepository = book_repository;
    }


    @Override
    public BookEntity createBook(String isbn, BookEntity book_entity)
    {
        book_entity.setIsbn(isbn);
        return this.bookRepository.save(book_entity);
    }
}