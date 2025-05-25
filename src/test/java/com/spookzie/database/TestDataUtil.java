package com.spookzie.database;

import com.spookzie.database.domain.entities.AuthorEntity;
import com.spookzie.database.domain.entities.BookEntity;

public final class TestDataUtil
{
    private TestDataUtil() {
    }

    // Authors
    //--------------------------
    public static AuthorEntity createTestAuthorA()
    {
        return AuthorEntity.builder()
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
    //--------------------------


    // Books
    //-------------------------
    public static BookEntity createTestBookA(final AuthorEntity authorEntity)
    {
        return BookEntity.builder()
                .isbn("978-1-2345-6789-0")
                .title("The Shadow in the Attic")
                .authorEntity(authorEntity)
                .build();
    }

    public static BookEntity createTestBookB(final AuthorEntity authorEntity)
    {
        return BookEntity.builder()
                .isbn("123-4-5678-9012-3")
                .title("Prince of Thorns")
                .authorEntity(authorEntity)
                .build();
    }

    public static BookEntity createTestBookC(final AuthorEntity authorEntity)
    {
        return BookEntity.builder()
                .isbn("456-7-8901-2345-6")
                .title("Norwegian Wood")
                .authorEntity(authorEntity)
                .build();
    }
    //-------------------------
}