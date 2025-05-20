package com.spookzie.database.repositories;

import com.spookzie.database.TestDataUtil;
import com.spookzie.database.domain.Author;
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
public class AuthorRepositoryIntegrationTests
{
    private final AuthorRepository authorRepo;  // Implementation that is being tested


    // Constructor
    @Autowired
    public AuthorRepositoryIntegrationTests(AuthorRepository under_test)
    {
        this.authorRepo = under_test;
    }


    //  CRUD Tests  //
    //----------------------------
    // Read One Test
    @Test
    public void testThatAuthorCanBeCreatedAndRecalled()
    {
        // Creating author
        Author author = this.authorRepo.save(TestDataUtil.createTestAuthorA());  // Inserting into DB

        // Recalling author
        Optional<Author> result = this.authorRepo.findById(author.getId());   // Fetching from DB
        assertThat(result).isPresent(); // Confirming that record exits
        assertThat(result.get()).isEqualTo(author); // Confirming that it matches
    }

    // Read Many Test
    @Test
    public void testThatMultipleAuthorsCanBeCreatedAndRecalled()
    {
        // Creating authors
        Author authorA = this.authorRepo.save(TestDataUtil.createTestAuthorA());
        Author authorB = this.authorRepo.save(TestDataUtil.createTestAuthorB());
        Author authorC = this.authorRepo.save(TestDataUtil.createTestAuthorC());

        // Recalling Authors
        Iterable<Author> result = this.authorRepo.findAll();
        assertThat(result)
                .hasSize(3)
                .containsExactly(authorA, authorB, authorC);
    }

    // Update Test
    @Test
    public void testThatAuthorCanBeUpdated()
    {
        // Creating author
        Author authorA = this.authorRepo.save(TestDataUtil.createTestAuthorA());

        authorA.setName("UPDATED"); // Changing author's name
        this.authorRepo.save(authorA);    // Updating the author

        // Finding and checking the author
        Optional<Author> result = this.authorRepo.findById(authorA.getId());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(authorA);
    }

    // Delete Test
    @Test
    public void testThatAuthorCanBeDeleted()
    {
        // Creating author
        Author authorA = this.authorRepo.save(TestDataUtil.createTestAuthorA());

        // Deleting author
        this.authorRepo.deleteById(authorA.getId());

        // Checking if author is deleted (result is deleted)
        Optional<Author> result = this.authorRepo.findById(authorA.getId());
        assertThat(result).isEmpty();
    }
    //----------------------------


    //  Custom Tests    //
    //----------------------------
    // Using Spring JPA's magic feature - Name Parsing
    @Test
    public void testThatGetAuthorsWithAgeLessThan()
    {
        Author authorA = this.authorRepo.save(TestDataUtil.createTestAuthorA());
        Author authorB = this.authorRepo.save(TestDataUtil.createTestAuthorB());
        Author authorC = this.authorRepo.save(TestDataUtil.createTestAuthorC());

        Iterable<Author> result = this.authorRepo.findByAgeLessThan(70);
        assertThat(result).containsExactly(authorB, authorC);
    }

    // HQL (Hibernate Query Language)
    @Test
    public void testThatGetAuthorsWithAgeGreaterThan()
    {
        Author authorA = this.authorRepo.save(TestDataUtil.createTestAuthorA());
        Author authorB = this.authorRepo.save(TestDataUtil.createTestAuthorB());
        Author authorC = this.authorRepo.save(TestDataUtil.createTestAuthorC());

        Iterable<Author> result = this.authorRepo.findAuthorsWithAgeGreaterThan(70);
        assertThat(result).containsExactly(authorA);
    }
    //----------------------------
}