package com.spookzie.database.services;

import com.spookzie.database.domain.entities.AuthorEntity;

import java.util.List;


/*  Service Layer Interface for Author  */
public interface AuthorService
{
    AuthorEntity createAuthor(AuthorEntity author_entity);

    List<AuthorEntity> findAll();
}