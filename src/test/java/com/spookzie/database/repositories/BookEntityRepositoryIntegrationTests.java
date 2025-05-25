package com.spookzie.database.repositories;

import com.spookzie.database.TestDataUtil;
import com.spookzie.database.domain.entities.AuthorEntity;
import com.spookzie.database.domain.entities.BookEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class BookEntityRepositoryIntegrationTests
{
    private final BookRepository bookRepo;    // Implementation that is being tested


    // Constructor
    @Autowired
    public BookEntityRepositoryIntegrationTests(BookRepository under_test)
    {
        this.bookRepo = under_test;
    }


    // Read One Test
    @Test
    public void testThatBookCanBeCreatedAndRecalled()
    {
        // Creating Author (for author_id in book)
        AuthorEntity authorEntity = TestDataUtil.createTestAuthorA();

        // Creating Book
        BookEntity bookEntity = this.bookRepo.save(TestDataUtil.createTestBookA(authorEntity));    // Inserting

        // Recalling book
        Optional<BookEntity> result = this.bookRepo.findById(bookEntity.getIsbn());    // Fetching
        assertThat(result).isPresent(); // Confirming existence
        assertThat(result.get()).isEqualTo(bookEntity);
    }


    // Read Many Test
    @Test
    public void testThatMultipleBooksCanBeCreatedAndRecalled()
    {
        // Creating author
        AuthorEntity authorEntity = TestDataUtil.createTestAuthorA();

        // Creating Books
        BookEntity bookEntityA = this.bookRepo.save(TestDataUtil.createTestBookA(authorEntity));
        BookEntity bookEntityB = this.bookRepo.save(TestDataUtil.createTestBookB(authorEntity));
        BookEntity bookEntityC = this.bookRepo.save(TestDataUtil.createTestBookC(authorEntity));


        // Recalling Books
        Iterable<BookEntity> result = this.bookRepo.findAll();
        assertThat(result)
                .hasSize(3)
                .containsExactly(bookEntityA, bookEntityB, bookEntityC);
    }


    // Update Test
    @Test
    public void testThatBookCanBeUpdated()
    {
        // Creating author & book
        AuthorEntity authorEntityA = TestDataUtil.createTestAuthorA();
        BookEntity bookEntityA = this.bookRepo.save(TestDataUtil.createTestBookA(authorEntityA));

        bookEntityA.setTitle("UPDATED");    // Changing book title
        this.bookRepo.save(bookEntityA);    // Updating the book

        // Finding & checking the updated book
        Optional<BookEntity> result = this.bookRepo.findById(bookEntityA.getIsbn());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(bookEntityA);
    }


    // Delete Test
    @Test
    public void testThatBookCanBeDeleted()
    {
        // Create author & book
        AuthorEntity authorEntityA = TestDataUtil.createTestAuthorA();
        BookEntity bookEntityA = this.bookRepo.save(TestDataUtil.createTestBookA(authorEntityA));

        // Delete book
        this.bookRepo.deleteById(bookEntityA.getIsbn());

        // Check if book is deleted
        Optional<BookEntity> result = this.bookRepo.findById(bookEntityA.getIsbn());
        assertThat(result).isEmpty();
    }
}