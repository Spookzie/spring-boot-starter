package com.spookzie.database.controllers;

import com.spookzie.database.domain.dto.AuthorDto;
import com.spookzie.database.domain.entities.AuthorEntity;
import com.spookzie.database.mappers.Mapper;
import com.spookzie.database.services.AuthorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
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
        AuthorEntity savedAuthorEntity = this.authorService.saveAuthor(authorEntity); // Saving (Creating) the Entity
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


    // GET - Read One
    @GetMapping(path = "/authors/{id}")
    public ResponseEntity<AuthorDto> getAuthor(@PathVariable("id") Long id)
    {
        Optional<AuthorEntity> foundAuthor = this.authorService.findOne(id);

        return foundAuthor.map(
                authorEntity -> {
                    AuthorDto authorDto = this.authorMapper.mapTo(authorEntity);
                    return new ResponseEntity<>(authorDto, HttpStatus.OK);
                }
        ).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


    // PUT - Full Update
    @PutMapping(path = "/authors/{id}")
    public ResponseEntity<AuthorDto> fullUpdateAuthor(
            @PathVariable("id") Long id,
            @RequestBody AuthorDto author_dto)
    {
        if(!this.authorService.doesExist(id))
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        author_dto.setId(id);
        AuthorEntity authorEntity = this.authorMapper.mapFrom(author_dto);
        AuthorEntity savedAuthorEntity = this.authorService.saveAuthor(authorEntity);
        AuthorDto savedAuthorDto = this.authorMapper.mapTo(savedAuthorEntity);

        return new ResponseEntity<>(savedAuthorDto, HttpStatus.OK);
    }
}