package com.spookzie.database.dao.impl;

import com.spookzie.database.TestDataUtil;
import com.spookzie.database.dao.AuthorDao;
import com.spookzie.database.domain.Author;
import com.spookzie.database.domain.Book;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class BookDaoImplIntegrationTests
{
    private final AuthorDao authorDao;
    private final BookDaoImpl bookDao;    // Implementation that is being tested


    // Constructor
    @Autowired
    public BookDaoImplIntegrationTests(BookDaoImpl under_test, AuthorDao author_dao)
    {
        this.bookDao = under_test;
        this.authorDao = author_dao;
    }


    // Read One Test
    @Test
    public void TestThatBookCanBeCreatedAndRecalled()
    {
        // Creating Author (for author_id in book)
        Author author = TestDataUtil.CreateTestAuthorA();
        this.authorDao.Create(author);  // Inserting into DB

        // Creating Book
        Book book = TestDataUtil.CreateTestBookA();
        book.setAuthorId(author.getId());   // Setting author id in book
        this.bookDao.Create(book);    // Inserting

        // Recalling book
        Optional<Book> result = this.bookDao.FindOne(book.getIsbn());    // Fetching
        assertThat(result).isPresent(); // Confirming existence
        assertThat(result.get()).isEqualTo(book);   // Confirming match
    }


    // Read Many Test
    @Test
    public void TestThatMultipleBooksCanBeCreatedAndRecalled()
    {
        // Creating Authors
        Author authorA = TestDataUtil.CreateTestAuthorA();
        this.authorDao.Create(authorA);
        Author authorB = TestDataUtil.CreateTestAuthorB();
        this.authorDao.Create(authorB);
        Author authorC = TestDataUtil.CreateTestAuthorC();
        this.authorDao.Create(authorC);

        // Creating Books
        Book bookA = TestDataUtil.CreateTestBookA();
        bookA.setAuthorId(authorA.getId());
        this.bookDao.Create(bookA);

        Book bookB = TestDataUtil.CreateTestBookB();
        bookB.setAuthorId(authorB.getId());
        this.bookDao.Create(bookB);

        Book bookC = TestDataUtil.CreateTestBookC();
        bookC.setAuthorId(authorC.getId());
        this.bookDao.Create(bookC);

        // Recalling Books
        List<Book> result = this.bookDao.Find();
        assertThat(result)
                .hasSize(3)
                .containsExactly(bookA, bookB, bookC);
    }


    // Update Test
    @Test
    public void TestThatBookCanBeUpdated()
    {
        // Creating author
        Author authorA = TestDataUtil.CreateTestAuthorA();
        this.authorDao.Create(authorA);

        // Creating book
        Book bookA = TestDataUtil.CreateTestBookA();
        bookA.setAuthorId(authorA.getId());
        this.bookDao.Create(bookA);

        bookA.setTitle("UPDATED");    // Changing book title
        this.bookDao.Update(bookA.getIsbn(), bookA);    // Updating the book

        // Finding & checking the updated book
        Optional<Book> result = this.bookDao.FindOne(bookA.getIsbn());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(bookA);
    }


    // Delete Test
    @Test
    public void TestThatBookCanBeDeleted()
    {
        // Create author
        Author authorA = TestDataUtil.CreateTestAuthorA();
        this.authorDao.Create(authorA);

        // Create book
        Book bookA = TestDataUtil.CreateTestBookA();
        bookA.setAuthorId(authorA.getId());
        this.bookDao.Create(bookA);

        // Delete book
        this.bookDao.Delete(bookA.getIsbn());

        // Check if book is deleted
        Optional<Book> result = this.bookDao.FindOne(bookA.getIsbn());
        assertThat(result).isEmpty();
    }
}