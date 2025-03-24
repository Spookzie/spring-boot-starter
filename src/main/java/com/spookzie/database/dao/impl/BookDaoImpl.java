package com.spookzie.database.dao.impl;

import com.spookzie.database.dao.BookDao;
import com.spookzie.database.domain.Book;
import org.springframework.jdbc.core.JdbcTemplate;


public class BookDaoImpl implements BookDao
{
    private final JdbcTemplate jdbcTemplate;

    // Constructor
    public BookDaoImpl(final JdbcTemplate jdbc_template)    {   this.jdbcTemplate = jdbc_template;  }

    // Interface Implementation
    @Override
    public void create(Book book)
    {
        this.jdbcTemplate.update(
                "INSERT INTO books(isbn, title, author_id) VALUES(?, ?, ?)",
                book.getIsbn(), book.getTitle(), book.getAuthorId()
        );
    }
}
