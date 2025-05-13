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
                .id(null)   // No need to pass in from us, will be generated automatically by @GeneratedValue
                .name("Abigail Rose")
                .age(80)
                .build();
    }

    public static Author CreateTestAuthorB()
    {
        return Author.builder()
                .id(null)
                .name("Mark Lawrence")
                .age(47)
                .build();
    }

    public static Author CreateTestAuthorC()
    {
        return Author.builder()
                .id(null)
                .name("Haruki Murakami")
                .age(65)
                .build();
    }
    //--------------------------


    // Books
    //-------------------------
    public static Book CreateTestBookA(final Author author)
    {
        return Book.builder()
                .isbn("978-1-2345-6789-0")
                .title("The Shadow in the Attic")
                .author(author)
                .build();
    }

    public static Book CreateTestBookB(final Author author)
    {
        return Book.builder()
                .isbn("123-4-5678-9012-3")
                .title("Prince of Thorns")
                .author(author)
                .build();
    }

    public static Book CreateTestBookC(final Author author)
    {
        return Book.builder()
                .isbn("456-7-8901-2345-6")
                .title("Norwegian Wood")
                .author(author)
                .build();
    }
    //-------------------------
}