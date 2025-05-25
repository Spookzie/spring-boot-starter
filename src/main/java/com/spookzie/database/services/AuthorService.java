package com.spookzie.database.services;

import com.spookzie.database.domain.entities.AuthorEntity;


/*  Service Layer Interface for Author  */
public interface AuthorService
{
    AuthorEntity createAuthor(AuthorEntity author_entity);
}