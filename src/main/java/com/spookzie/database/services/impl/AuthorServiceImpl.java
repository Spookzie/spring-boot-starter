package com.spookzie.database.services.impl;

import com.spookzie.database.domain.entities.AuthorEntity;
import com.spookzie.database.repositories.AuthorRepository;
import com.spookzie.database.services.AuthorService;
import org.springframework.stereotype.Service;


@Service
public class AuthorServiceImpl implements AuthorService
{
    private final AuthorRepository authorRepository;


    // Constructor
    public AuthorServiceImpl(AuthorRepository author_repository)
    {
        this.authorRepository = author_repository;
    }


    @Override
    public AuthorEntity createAuthor(AuthorEntity author_entity)
    {
        return this.authorRepository.save(author_entity);
    }
}