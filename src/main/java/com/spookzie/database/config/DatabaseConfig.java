package com.spookzie.database.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;


@Configuration
public class DatabaseConfig
{
    @Bean
    public JdbcTemplate jdbcTemplate(final DataSource data_soruce)
    {
        return new JdbcTemplate(data_soruce);
    }
}