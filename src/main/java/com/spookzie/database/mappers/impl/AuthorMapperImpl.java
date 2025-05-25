package com.spookzie.database.mappers.impl;

import com.spookzie.database.domain.dto.AuthorDto;
import com.spookzie.database.domain.entities.AuthorEntity;
import com.spookzie.database.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

/*
* Converting b/w 2 object types:- AuthorEntity & AuthorDto
* Entity - represents the database(JPA)
* DTO - used for data transfer via API (controller -> service -> client)
************************************************************/
@Component
public class AuthorMapperImpl implements Mapper<AuthorEntity, AuthorDto>
{
    private final ModelMapper modelMapper;  // Provides boilerplate code for mapping


    public AuthorMapperImpl(ModelMapper model_mapper)
    {
        this.modelMapper = model_mapper;
    }


    /*  Converts the AuthorEntity to AuthorDto for controller to return in the API response */
    @Override
    public AuthorDto mapTo(AuthorEntity author_entity)
    {
        return this.modelMapper.map(author_entity, AuthorDto.class);
    }


    /*  Converts incoming AuthorDto to AuthorEntity, so the service layer can save it   */
    @Override
    public AuthorEntity mapFrom(AuthorDto author_dto)
    {
        return this.modelMapper.map(author_dto, AuthorEntity.class);
    }
}