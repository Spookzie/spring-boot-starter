package com.spookzie.database.services.impl;

import com.spookzie.database.domain.entities.AuthorEntity;
import com.spookzie.database.repositories.AuthorRepository;
import com.spookzie.database.services.AuthorService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


/*  Defines how author is created   */
@Service
public class AuthorServiceImpl implements AuthorService
{
    private final AuthorRepository authorRepository;


    public AuthorServiceImpl(AuthorRepository author_repository)
    {
        this.authorRepository = author_repository;
    }


    @Override
    public AuthorEntity createAuthor(AuthorEntity author_entity)
    {
        return this.authorRepository.save(author_entity);
    }


    @Override
    public List<AuthorEntity> findAll()
    {
        /*
        * this.authorRepo.findAll() - Returns an Iterable<AuthorEntity>
        * spliterator() - Converts the Iterable into a Spliterator, which is used to create Java Stream
        * StreamSupport.stream(...false) - Converts Spliterator into a sequential stream (cuz false indicates not parallel)
        *****************************************/
        return StreamSupport.stream(this.authorRepository
                        .findAll()
                        .spliterator(),
                        false)
                .collect(Collectors.toList());
    }
}