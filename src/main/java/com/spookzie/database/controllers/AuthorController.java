package com.spookzie.database.controllers;

import com.spookzie.database.domain.dto.AuthorDto;
import com.spookzie.database.domain.entities.AuthorEntity;
import com.spookzie.database.mappers.Mapper;
import com.spookzie.database.services.AuthorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;


/*
* Defining CRUD operations using REST API principles & design
* Setting up HTTP methods
*****************************************************/
@RestController
public class AuthorController
{
    private final AuthorService authorService;
    private final Mapper<AuthorEntity, AuthorDto> authorMapper;


    public AuthorController(AuthorService author_service, Mapper<AuthorEntity, AuthorDto> author_mapper)
    {
        this.authorService = author_service;
        this.authorMapper  = author_mapper;
    }


    // POST - Create
    @PostMapping(path = "/authors")
    public ResponseEntity<AuthorDto> createAuthor(@RequestBody AuthorDto author_dto)
    {
        AuthorEntity authorEntity = this.authorMapper.mapFrom(author_dto);
        AuthorEntity savedAuthorEntity = this.authorService.createAuthor(authorEntity); // Saving (Creating) the Entity
        AuthorDto savedAuthorDto = this.authorMapper.mapTo(savedAuthorEntity);

        return new ResponseEntity<>(savedAuthorDto, HttpStatus.CREATED);  // Returning the updated info to client with status 201
    }


    //  GET - Read Many
    @GetMapping(path = "/authors")
    public List<AuthorDto> listAuthors()
    {
        List<AuthorEntity> authors = this.authorService.findAll();

        return authors.stream()
                .map(this.authorMapper::mapTo)
                .collect(Collectors.toList());
    }
}