package com.spookzie.database.dao.impl;

import com.spookzie.database.TestDataUtil;
import com.spookzie.database.domain.Author;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;


@ExtendWith(MockitoExtension.class)
public class AuthorDaoImplTests
{
    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private AuthorDaoImpl authorDao;


    // Create Test
    @Test
    public void TestThatCreateAuthorGeneratesCorrectSql()
    {
        // Creating author
        Author author = TestDataUtil.CreateTestAuthorA();
        this.authorDao.Create(author);  // Inserting into DB

        verify(this.jdbcTemplate).update(
                eq("INSERT INTO authors(id, name, age) VALUES(?, ?, ?)"),
                eq(1L), eq("Abigail Rose"), eq(80)
        );  // Verifying that the jdbcTemplate.update() was called with the expected SQL query and values
    }


    // Read One Test
    @Test
    public void TestThatFindOneAuthorGeneratesTheCorrectSql()
    {
        this.authorDao.FindOne(1L);

        verify(this.jdbcTemplate).query(
                eq("SELECT id, name, age FROM authors WHERE id = ? LIMIT 1"),
                ArgumentMatchers.<AuthorDaoImpl.AuthorRowMapper>any(),
                eq(1L)
        );
    }

    // Read Many Test
    @Test
    public void TestThatFindManyAuthorGeneratesTheCorrectSql()
    {
        this.authorDao.Find();
        verify(this.jdbcTemplate).query(
                eq("SELECT id, name, age FROM authors"),
                ArgumentMatchers.<AuthorDaoImpl.AuthorRowMapper>any()
        );
    }
}