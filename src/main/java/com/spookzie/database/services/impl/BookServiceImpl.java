package com.spookzie.database.services.impl;

import com.spookzie.database.domain.entities.BookEntity;
import com.spookzie.database.repositories.BookRepository;
import com.spookzie.database.services.BookService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


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
    public BookEntity createUpdateBook(String isbn, BookEntity book_entity)
    {
        book_entity.setIsbn(isbn);
        return this.bookRepository.save(book_entity);
    }


    @Override
    public List<BookEntity> findAll()
    {
        /*
         * this.bookRepo.findAll() - Returns an Iterable<BookEntity>
         * spliterator() - Converts the Iterable into a Spliterator, which is used to create Java Stream
         * StreamSupport.stream(...false) - Converts Spliterator into a sequential stream (cuz false indicates not parallel)
         *****************************************/
        return StreamSupport
                .stream(
                        this.bookRepository.findAll().spliterator(),
                        false)
                .collect(Collectors.toList());
    }


    @Override
    public Optional<BookEntity> findOne(String isbn)
    {
        return this.bookRepository.findById(isbn);
    }


    @Override
    public boolean doesExist(String isbn)
    {
        return this.bookRepository.existsById(isbn);
    }


    @Override
    public BookEntity partialUpdate(String isbn, BookEntity book_entity)
    {
        book_entity.setIsbn(isbn);

        return this.bookRepository.findById(isbn).map(
                existingBook -> {
                    Optional.ofNullable(book_entity.getTitle()).ifPresent(existingBook::setTitle);
                    return this.bookRepository.save(existingBook);
                }
        ).orElseThrow(() -> new RuntimeException("Book does not exist"));
    }
}