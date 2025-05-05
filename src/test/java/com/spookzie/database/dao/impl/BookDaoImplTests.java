package com.spookzie.database.dao.impl;

import com.spookzie.database.TestDataUtil;
import com.spookzie.database.domain.Book;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;


@ExtendWith(MockitoExtension.class)
public class BookDaoImplTests
{
    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private BookDaoImpl bookDao;


    // Create Test
    @Test
    public void TestThatCreateBookGeneratesCorrectSql()
    {
        // Creating Book
        Book book = TestDataUtil.CreateTestBookA();
        this.bookDao.Create(book);  // Inserting into DB

        verify(this.jdbcTemplate).update(
                eq("INSERT INTO books(isbn, title, author_id) VALUES(?, ?, ?)"),
                eq("978-1-2345-6789-0"), eq("The Shadow in the Attic"), eq(1L)
        );  // Verifying that the jdbcTemplate.update() was called with the expected SQL query and values
    }


    // Read One Test
    @Test
    public void TestThatFindOneOneBookGeneratesTheCorrectSql()
    {
        this.bookDao.FindOne("978-1-2345-6789-0");

        verify(this.jdbcTemplate).query(
                eq("SELECT isbn, title, author_id FROM books WHERE isbn = ? LIMIT 1"),
                ArgumentMatchers.<BookDaoImpl.BookRowMapper>any(),
                eq("978-1-2345-6789-0")
        );
    }

    // Read Many Test
    @Test
    public void TestThatFindOneManyBookGeneratesTheCorrectSql()
    {
        this.bookDao.Find();
        verify(this.jdbcTemplate).query(
                eq("SELECT isbn, title, author_id FROM books"),
                ArgumentMatchers.<BookDaoImpl.BookRowMapper>any()
        );
    }
}
