package com.spookzie.database.dao.impl;

import com.spookzie.database.dao.AuthorDao;
import com.spookzie.database.domain.Author;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;


@Component
public class AuthorDaoImpl implements AuthorDao
{
    private final JdbcTemplate jdbcTemplate;

    // Constructor
    public AuthorDaoImpl(final JdbcTemplate jdbc_template)
    {
        this.jdbcTemplate = jdbc_template;
    }

    // Create Operation (C)
    //---------------------------------
    @Override
    public void Create(Author author)
    {
        this.jdbcTemplate.update(
                "INSERT INTO authors(id, name, age) VALUES(?, ?, ?)",
                author.getId(), author.getName(), author.getAge()
        );
    }
    //---------------------------------


    // Find Operation (R)
    //---------------------------------
    @Override
    public Optional<Author> FindOne(long author_id)
    {
        List<Author> results = this.jdbcTemplate.query(
                "SELECT id, name, age FROM authors WHERE id = ? LIMIT 1",
                new AuthorRowMapper(), author_id
        );

        return results.stream().findFirst();
    }

    @Override
    public List<Author> Find()
    {
        return this.jdbcTemplate.query(
                "SELECT id, name, age FROM authors",
                new AuthorRowMapper()
        );
    }
    //---------------------------------

    // Converting row from ResultSet into Author object
    // Converting DB rows into Java objects
    public static class AuthorRowMapper implements RowMapper<Author>
    {
        @Override
        public Author mapRow(ResultSet rs, int row_num) throws SQLException
        {
            return Author.builder()
                    .id(rs.getLong("id"))
                    .name(rs.getString("name"))
                    .age(rs.getInt("age"))
                    .build();
        }
    }
}