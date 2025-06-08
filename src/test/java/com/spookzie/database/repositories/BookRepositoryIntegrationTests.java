package com.spookzie.database.repositories;

import com.spookzie.database.TestDataUtil;
import com.spookzie.database.domain.entities.AuthorEntity;
import com.spookzie.database.domain.entities.BookEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


/*  Unit Tests  */
@DataJpaTest
public class BookRepositoryIntegrationTests
{
    private final BookRepository bookRepo;


    @Autowired
    public BookRepositoryIntegrationTests(BookRepository under_test)
    {
        this.bookRepo = under_test;
    }


    /*  CRUD Test - Read One    */
    @Test
    public void testThatBookCanBeCreatedAndRecalled()
    {
        AuthorEntity testAuthorEntityA = TestDataUtil.createTestAuthorEntityA(); // Creating Author (for author_id in book)

        BookEntity testBookEntity = this.bookRepo.save(TestDataUtil.createTestBookEntityA(testAuthorEntityA));    // Creating book (saving into DB)


        Optional<BookEntity> result = this.bookRepo.findById(testBookEntity.getIsbn());    // Recalling book (Finding/Reading/Fetching from DB)

        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(testBookEntity);
    }


    /*  CRUD Test - Read Many   */
    @Test
    public void testThatMultipleBooksCanBeCreatedAndRecalled()
    {
        AuthorEntity testAuthorEntityA = TestDataUtil.createTestAuthorEntityA();

        BookEntity testBookEntityA = this.bookRepo.save(TestDataUtil.createTestBookEntityA(testAuthorEntityA));
        BookEntity testBookEntityB = this.bookRepo.save(TestDataUtil.createTestBookB(testAuthorEntityA));
        BookEntity testBookEntityC = this.bookRepo.save(TestDataUtil.createTestBookC(testAuthorEntityA));

        Iterable<BookEntity> result = this.bookRepo.findAll();

        assertThat(result)
                .hasSize(3)
                .containsExactly(testBookEntityA, testBookEntityB, testBookEntityC);
    }


    /*  CRUD Test - Update  */
    @Test
    public void testThatBookCanBeUpdated()
    {
        AuthorEntity testAuthorEntityA = TestDataUtil.createTestAuthorEntityA();

        BookEntity testBookEntityA = this.bookRepo.save(TestDataUtil.createTestBookEntityA(testAuthorEntityA));

        testBookEntityA.setTitle("UPDATED");    // Changing book title
        this.bookRepo.save(testBookEntityA);    // Updating the book

        Optional<BookEntity> result = this.bookRepo.findById(testBookEntityA.getIsbn());

        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(testBookEntityA);
    }


    /*  CRUD Test - Delete  */
    @Test
    public void testThatBookCanBeDeleted()
    {
        AuthorEntity testAuthorEntityA = TestDataUtil.createTestAuthorEntityA();

        BookEntity testBookEntityA = this.bookRepo.save(TestDataUtil.createTestBookEntityA(testAuthorEntityA));

        this.bookRepo.deleteById(testBookEntityA.getIsbn());

        Optional<BookEntity> result = this.bookRepo.findById(testBookEntityA.getIsbn());
        assertThat(result).isEmpty();
    }
}