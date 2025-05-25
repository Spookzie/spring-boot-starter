package com.spookzie.database.mappers.impl;

import com.spookzie.database.domain.dto.AuthorDto;
import com.spookzie.database.domain.entities.AuthorEntity;
import com.spookzie.database.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;


@Component
public class AuthorMapperImpl implements Mapper<AuthorEntity, AuthorDto>
{
    private final ModelMapper modelMapper;


    // Constructor
    public AuthorMapperImpl(ModelMapper model_mapper)
    {
        this.modelMapper = model_mapper;
    }


    @Override
    public AuthorDto mapTo(AuthorEntity author_entity)
    {
        return modelMapper.map(author_entity, AuthorDto.class);
    }

    @Override
    public AuthorEntity mapFrom(AuthorDto author_dto)
    {
        return modelMapper.map(author_dto, AuthorEntity.class);
    }
}