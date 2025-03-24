package com.spookzie.database.dao.impl;

import com.spookzie.database.domain.Author;
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
public class AuthorDaoImplTests
{
    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private AuthorDaoImpl underTest;


    @Test
    public void TestThatCreateAuthorGeneratesCorrectSql()
    {
        Author author = Author.builder()
                .id(1L)
                .name("Abigail Rose")
                .age(80)
                .build();

        this.underTest.create(author);

        verify(this.jdbcTemplate).update(
                eq("INSERT INTO authors(id, name, age) VALUES(?, ?, ?)"),
                eq(1L), eq("Abigail Rose"), eq(80)
        );
    }


    @Test
    public void TestThatFindOneGeneratesTheCorrectSql()
    {
        this.underTest.FindOne(1L);

        verify(this.jdbcTemplate).query(
                eq("SELECT id, name, age FROM authors WHERE id = ? LIMIT 1"),
                ArgumentMatchers.<AuthorDaoImpl.AuthorRowMapper>any(),
                eq(1L)
        );
    }
}