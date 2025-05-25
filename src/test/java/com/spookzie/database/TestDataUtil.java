package com.spookzie.database;

import com.spookzie.database.domain.dto.AuthorDto;
import com.spookzie.database.domain.dto.BookDto;
import com.spookzie.database.domain.entities.AuthorEntity;
import com.spookzie.database.domain.entities.BookEntity;

public final class TestDataUtil
{
    private TestDataUtil() {
    }

    /*  Authors */
    public static AuthorEntity createTestAuthorEntityA()
    {
        return AuthorEntity.builder()
                .name("Abigail Rose")
                .age(80)
                .build();
    }

    public static AuthorDto createTestAuthorDtoA()
    {
        return AuthorDto.builder()
                .name("Abigail Rose")
                .age(80)
                .build();
    }


    public static AuthorEntity createTestAuthorB()
    {
        return AuthorEntity.builder()
                .name("Mark Lawrence")
                .age(47)
                .build();
    }


    public static AuthorEntity createTestAuthorC()
    {
        return AuthorEntity.builder()
                .name("Haruki Murakami")
                .age(65)
                .build();
    }
    /****************************************************/


    /*  Books   */
    public static BookEntity createTestBookEntityA(final AuthorEntity author_entity)
    {
        return BookEntity.builder()
                .isbn("978-1-2345-6789-0")
                .title("The Shadow in the Attic")
                .authorEntity(author_entity)
                .build();
    }

    public static BookDto createTestBookDtoA(final AuthorDto author_dto)
    {
        return BookDto.builder()
                .isbn("978-1-2345-6789-0")
                .title("The Shadow in the Attic")
                .authorDto(author_dto)
                .build();
    }


    public static BookEntity createTestBookB(final AuthorEntity author_entity)
    {
        return BookEntity.builder()
                .isbn("123-4-5678-9012-3")
                .title("Prince of Thorns")
                .authorEntity(author_entity)
                .build();
    }

    public static BookEntity createTestBookC(final AuthorEntity author_entity)
    {
        return BookEntity.builder()
                .isbn("456-7-8901-2345-6")
                .title("Norwegian Wood")
                .authorEntity(author_entity)
                .build();
    }
    /****************************************************/
}