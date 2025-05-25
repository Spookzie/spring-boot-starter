package com.spookzie.database.repositories;

import com.spookzie.database.TestDataUtil;
import com.spookzie.database.domain.entities.AuthorEntity;
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
public class AuthorEntityRepositoryIntegrationTests
{
    private final AuthorRepository authorRepo;  // Implementation that is being tested


    // Constructor
    @Autowired
    public AuthorEntityRepositoryIntegrationTests(AuthorRepository under_test)
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
        AuthorEntity authorEntity = this.authorRepo.save(TestDataUtil.createTestAuthorA());  // Inserting into DB

        // Recalling author
        Optional<AuthorEntity> result = this.authorRepo.findById(authorEntity.getId());   // Fetching from DB
        assertThat(result).isPresent(); // Confirming that record exits
        assertThat(result.get()).isEqualTo(authorEntity); // Confirming that it matches
    }

    // Read Many Test
    @Test
    public void testThatMultipleAuthorsCanBeCreatedAndRecalled()
    {
        // Creating authors
        AuthorEntity authorEntityA = this.authorRepo.save(TestDataUtil.createTestAuthorA());
        AuthorEntity authorEntityB = this.authorRepo.save(TestDataUtil.createTestAuthorB());
        AuthorEntity authorEntityC = this.authorRepo.save(TestDataUtil.createTestAuthorC());

        // Recalling Authors
        Iterable<AuthorEntity> result = this.authorRepo.findAll();
        assertThat(result)
                .hasSize(3)
                .containsExactly(authorEntityA, authorEntityB, authorEntityC);
    }

    // Update Test
    @Test
    public void testThatAuthorCanBeUpdated()
    {
        // Creating author
        AuthorEntity authorEntityA = this.authorRepo.save(TestDataUtil.createTestAuthorA());

        authorEntityA.setName("UPDATED"); // Changing author's name
        this.authorRepo.save(authorEntityA);    // Updating the author

        // Finding and checking the author
        Optional<AuthorEntity> result = this.authorRepo.findById(authorEntityA.getId());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(authorEntityA);
    }

    // Delete Test
    @Test
    public void testThatAuthorCanBeDeleted()
    {
        // Creating author
        AuthorEntity authorEntityA = this.authorRepo.save(TestDataUtil.createTestAuthorA());

        // Deleting author
        this.authorRepo.deleteById(authorEntityA.getId());

        // Checking if author is deleted (result is deleted)
        Optional<AuthorEntity> result = this.authorRepo.findById(authorEntityA.getId());
        assertThat(result).isEmpty();
    }
    //----------------------------


    //  Custom Tests    //
    //----------------------------
    // Using Spring JPA's magic feature - Name Parsing
    @Test
    public void testThatGetAuthorsWithAgeLessThan()
    {
        AuthorEntity authorEntityA = this.authorRepo.save(TestDataUtil.createTestAuthorA());
        AuthorEntity authorEntityB = this.authorRepo.save(TestDataUtil.createTestAuthorB());
        AuthorEntity authorEntityC = this.authorRepo.save(TestDataUtil.createTestAuthorC());

        Iterable<AuthorEntity> result = this.authorRepo.findByAgeLessThan(70);
        assertThat(result).containsExactly(authorEntityB, authorEntityC);
    }

    // HQL (Hibernate Query Language)
    @Test
    public void testThatGetAuthorsWithAgeGreaterThan()
    {
        AuthorEntity authorEntityA = this.authorRepo.save(TestDataUtil.createTestAuthorA());
        AuthorEntity authorEntityB = this.authorRepo.save(TestDataUtil.createTestAuthorB());
        AuthorEntity authorEntityC = this.authorRepo.save(TestDataUtil.createTestAuthorC());

        Iterable<AuthorEntity> result = this.authorRepo.findAuthorsWithAgeGreaterThan(70);
        assertThat(result).containsExactly(authorEntityA);
    }
    //----------------------------
}