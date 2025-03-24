package com.spookzie.database.dao.impl;

import com.spookzie.database.dao.AuthorDao;
import com.spookzie.database.domain.Author;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;


public class AuthorDaoImpl implements AuthorDao
{
    private final JdbcTemplate jdbcTemplate;

    // Constructor
    public AuthorDaoImpl(final JdbcTemplate jdbc_template)  {   this.jdbcTemplate = jdbc_template;  }

    // Interface Implementation
    //---------------------------------
    @Override
    public void create(Author author)
    {
        this.jdbcTemplate.update(
                "INSERT INTO authors(id, name, age) VALUES(?, ?, ?)",
                author.getId(), author.getName(), author.getAge()
        );
    }

    @Override
    public Optional<Author> FindOne(long l)
    {
        return Optional.empty();
    }
    //---------------------------------


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