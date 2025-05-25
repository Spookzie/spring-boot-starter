package com.spookzie.database.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


// POJO
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookDto
{
    private String isbn;
    private String title;

    private AuthorDto authorDto;
}