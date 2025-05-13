package com.spookzie.database.repositories;

import com.spookzie.database.TestDataUtil;
import com.spookzie.database.domain.Author;
import com.spookzie.database.domain.Book;
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
public class BookRepositoryIntegerationTests
{
    private final BookRepository bookRepo;    // Implementation that is being tested


    // Constructor
    @Autowired
    public BookRepositoryIntegerationTests(BookRepository under_test)
    {
        this.bookRepo = under_test;
    }


    // Read One Test
    @Test
    public void TestThatBookCanBeCreatedAndRecalled()
    {
        // Creating Author (for author_id in book)
        Author author = TestDataUtil.CreateTestAuthorA();

        // Creating Book
        Book book = TestDataUtil.CreateTestBookA(author);
        this.bookRepo.save(book);    // Inserting

        // Recalling book
        Optional<Book> result = this.bookRepo.findById(book.getIsbn());    // Fetching
        assertThat(result).isPresent(); // Confirming existence
    }


//    // Read Many Test
//    @Test
//    public void TestThatMultipleBooksCanBeCreatedAndRecalled()
//    {
//        // Creating Authors
//        Author authorA = TestDataUtil.CreateTestAuthorA();
//        this.authorRepo.Create(authorA);
//        Author authorB = TestDataUtil.CreateTestAuthorB();
//        this.authorRepo.Create(authorB);
//        Author authorC = TestDataUtil.CreateTestAuthorC();
//        this.authorRepo.Create(authorC);
//
//        // Creating Books
//        Book bookA = TestDataUtil.CreateTestBookA();
//        bookA.setAuthorId(authorA.getId());
//        this.bookRepo.Create(bookA);
//
//        Book bookB = TestDataUtil.CreateTestBookB();
//        bookB.setAuthorId(authorB.getId());
//        this.bookRepo.Create(bookB);
//
//        Book bookC = TestDataUtil.CreateTestBookC();
//        bookC.setAuthorId(authorC.getId());
//        this.bookRepo.Create(bookC);
//
//        // Recalling Books
//        List<Book> result = this.bookRepo.Find();
//        assertThat(result)
//                .hasSize(3)
//                .containsExactly(bookA, bookB, bookC);
//    }
//
//
//    // Update Test
//    @Test
//    public void TestThatBookCanBeUpdated()
//    {
//        // Creating author
//        Author authorA = TestDataUtil.CreateTestAuthorA();
//        this.authorRepo.Create(authorA);
//
//        // Creating book
//        Book bookA = TestDataUtil.CreateTestBookA();
//        bookA.setAuthorId(authorA.getId());
//        this.bookRepo.Create(bookA);
//
//        bookA.setTitle("UPDATED");    // Changing book title
//        this.bookRepo.Update(bookA.getIsbn(), bookA);    // Updating the book
//
//        // Finding & checking the updated book
//        Optional<Book> result = this.bookRepo.FindOne(bookA.getIsbn());
//        assertThat(result).isPresent();
//        assertThat(result.get()).isEqualTo(bookA);
//    }
//
//
//    // Delete Test
//    @Test
//    public void TestThatBookCanBeDeleted()
//    {
//        // Create author
//        Author authorA = TestDataUtil.CreateTestAuthorA();
//        this.authorRepo.Create(authorA);
//
//        // Create book
//        Book bookA = TestDataUtil.CreateTestBookA();
//        bookA.setAuthorId(authorA.getId());
//        this.bookRepo.Create(bookA);
//
//        // Delete book
//        this.bookRepo.Delete(bookA.getIsbn());
//
//        // Check if book is deleted
//        Optional<Book> result = this.bookRepo.FindOne(bookA.getIsbn());
//        assertThat(result).isEmpty();
//    }
}