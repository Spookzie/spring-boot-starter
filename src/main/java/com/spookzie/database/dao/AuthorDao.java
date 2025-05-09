package com.spookzie.database.dao;

import com.spookzie.database.domain.Author;

import java.util.List;
import java.util.Optional;


public interface AuthorDao
{
    void Create(Author author); // C

    Optional<Author> FindOne(long author_id);   // R
    List<Author> Find();                        // R

    void Update(Long author_id, Author author); // U
}