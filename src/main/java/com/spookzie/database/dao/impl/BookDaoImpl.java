package com.spookzie.database.dao.impl;

import com.spookzie.database.dao.BookDao;
import com.spookzie.database.domain.Book;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;


@Component
public class BookDaoImpl implements BookDao
{
    private final JdbcTemplate jdbcTemplate;

    // Constructor
    public BookDaoImpl(final JdbcTemplate jdbc_template)
    {
        this.jdbcTemplate = jdbc_template;
    }

    // Create Operation (C)
    //---------------------------------
    @Override
    public void Create(Book book)
    {
        this.jdbcTemplate.update(
                "INSERT INTO books(isbn, title, author_id) VALUES(?, ?, ?)",
                book.getIsbn(), book.getTitle(), book.getAuthorId()
        );
    }
    //---------------------------------


    // Find Operation (R)
    //---------------------------------
    @Override
    public Optional<Book> FindOne(String isbn)
    {
        List<Book> results = this.jdbcTemplate.query(
                "SELECT isbn, title, author_id FROM books WHERE isbn = ? LIMIT 1",
                new BookRowMapper(), isbn
        );

        return results.stream().findFirst();
    }

    @Override
    public List<Book> Find()
    {
        return this.jdbcTemplate.query(
                "SELECT isbn, title, author_id FROM books",
                new BookRowMapper()
        );
    }
    //---------------------------------


    // Update Operation (U)
    //---------------------------------
    @Override
    public void Update(String isbn, Book book)
    {
        this.jdbcTemplate.update(
                "UPDATE books SET isbn = ?, title = ?, author_id = ? WHERE isbn = ?",
                book.getIsbn(), book.getTitle(), book.getAuthorId(), isbn
        );
    }
    //---------------------------------


    // Delete Operation (D)
    //---------------------------------
    @Override
    public void Delete(String isbn)
    {
        this.jdbcTemplate.update(
                "DELETE FROM books WHERE isbn = ?",
                isbn
        );
    }
    //---------------------------------


    // Converting row from ResultSet into Author object
    // Converting DB rows into Java objects
    public static class BookRowMapper implements RowMapper<Book>
    {
        @Override
        public Book mapRow(ResultSet rs, int row_num) throws SQLException
        {
            return Book.builder()
                    .isbn(rs.getString("isbn"))
                    .title(rs.getString("title"))
                    .authorId(rs.getLong("author_id"))
                    .build();
        }
    }
}
