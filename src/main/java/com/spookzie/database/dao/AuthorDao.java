package com.spookzie.database.dao;

import com.spookzie.database.domain.Author;
import java.util.Optional;


public interface AuthorDao
{
    void create(Author author);

    Optional<Author> FindOne(long l);
}
