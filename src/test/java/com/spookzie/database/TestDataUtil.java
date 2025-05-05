package com.spookzie.database;

import com.spookzie.database.domain.Author;
import com.spookzie.database.domain.Book;

public final class TestDataUtil
{
    private TestDataUtil() {
    }

    // Authors
    //--------------------------
    public static Author CreateTestAuthorA()
    {
        return Author.builder()
                .id(1L)
                .name("Abigail Rose")
                .age(80)
                .build();
    }

    public static Author CreateTestAuthorB()
    {
        return Author.builder()
                .id(2L)
                .name("Mark Lawrence")
                .age(47)
                .build();
    }

    public static Author CreateTestAuthorC()
    {
        return Author.builder()
                .id(3L)
                .name("Haruki Murakami")
                .age(65)
                .build();
    }
    //--------------------------


    // Books
    //-------------------------
    public static Book CreateTestBookA()
    {
        return Book.builder()
                .isbn("978-1-2345-6789-0")
                .title("The Shadow in the Attic")
                .authorId(1L)
                .build();
    }

    public static Book CreateTestBookB()
    {
        return Book.builder()
                .isbn("123-4-5678-9012-3")
                .title("Prince of Thorns")
                .authorId(2L)
                .build();
    }

    public static Book CreateTestBookC()
    {
        return Book.builder()
                .isbn("456-7-8901-2345-6")
                .title("Norwegian Wood")
                .authorId(3L)
                .build();
    }
    //-------------------------
}