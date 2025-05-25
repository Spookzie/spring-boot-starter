package com.spookzie.database.services;

import com.spookzie.database.domain.entities.AuthorEntity;


public interface AuthorService
{
    AuthorEntity createAuthor(AuthorEntity author_entity);
}