package com.spookzie.database.mappers.impl;

import com.spookzie.database.domain.dto.BookDto;
import com.spookzie.database.domain.entities.BookEntity;
import com.spookzie.database.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;


/*
 * Converting b/w 2 object types:- BookEntity & BookDto
 * Entity - represents the database(JPA)
 * DTO - used for data transfer via API (controller -> service -> client)
 ************************************************************/
@Component
public class BookMapperImpl implements Mapper<BookEntity, BookDto>
{
    private final ModelMapper modelMapper;


    public BookMapperImpl(ModelMapper model_mapper)
    {
        this.modelMapper = model_mapper;
    }


    /*  Converts the BookEntity to BookDto for controller to return in the API response */
    @Override
    public BookDto mapTo(BookEntity book_entity)
    {
        return this.modelMapper.map(book_entity, BookDto.class);
    }


    /*  Converts incoming BookDto to BookEntity, so the service layer can save it   */
    @Override
    public BookEntity mapFrom(BookDto book_dto)
    {
        return this.modelMapper.map(book_dto, BookEntity.class);
    }
}