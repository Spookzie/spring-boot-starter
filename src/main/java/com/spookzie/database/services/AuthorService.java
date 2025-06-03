package com.spookzie.database.services;

import com.spookzie.database.domain.entities.AuthorEntity;

import java.util.List;
import java.util.Optional;


/*  Service Layer Interface for Author  */
public interface AuthorService
{
    AuthorEntity saveAuthor(AuthorEntity author_entity);

    List<AuthorEntity> findAll();

    Optional<AuthorEntity> findOne(Long id);

    boolean doesExist(Long id);

    AuthorEntity partialUpdate(Long id, AuthorEntity author_entity);
}