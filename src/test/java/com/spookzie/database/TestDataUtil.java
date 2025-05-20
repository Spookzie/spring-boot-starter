package com.spookzie.database;

import com.spookzie.database.domain.Author;
import com.spookzie.database.domain.Book;

public final class TestDataUtil
{
    private TestDataUtil() {
    }

    // Authors
    //--------------------------
    public static Author createTestAuthorA()
    {
        return Author.builder()
                .name("Abigail Rose")
                .age(80)
                .build();
    }

    public static Author createTestAuthorB()
    {
        return Author.builder()
                .name("Mark Lawrence")
                .age(47)
                .build();
    }

    public static Author createTestAuthorC()
    {
        return Author.builder()
                .name("Haruki Murakami")
                .age(65)
                .build();
    }
    //--------------------------


    // Books
    //-------------------------
    public static Book createTestBookA(final Author author)
    {
        return Book.builder()
                .isbn("978-1-2345-6789-0")
                .title("The Shadow in the Attic")
                .author(author)
                .build();
    }

    public static Book createTestBookB(final Author author)
    {
        return Book.builder()
                .isbn("123-4-5678-9012-3")
                .title("Prince of Thorns")
                .author(author)
                .build();
    }

    public static Book createTestBookC(final Author author)
    {
        return Book.builder()
                .isbn("456-7-8901-2345-6")
                .title("Norwegian Wood")
                .author(author)
                .build();
    }
    //-------------------------
}