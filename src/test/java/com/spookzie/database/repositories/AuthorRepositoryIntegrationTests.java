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


/*  Unit Tests  */
@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class AuthorRepositoryIntegrationTests
{
    private final AuthorRepository authorRepo;


    @Autowired
    public AuthorRepositoryIntegrationTests(AuthorRepository under_test)
    {
        this.authorRepo = under_test;
    }


    /*  CRUD Test - Read One    */
    @Test
    public void testThatAuthorCanBeCreatedAndRecalled()
    {
        AuthorEntity testAuthorEntity = this.authorRepo.save(TestDataUtil.createTestAuthorEntityA());   // Creating author (saving into DB)

        Optional<AuthorEntity> result = this.authorRepo.findById(testAuthorEntity.getId());   // Recalling author (Finding/Reading/Fetching from DB)

        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(testAuthorEntity);
    }


    /*  CRUD Test - Read Many   */
    @Test
    public void testThatMultipleAuthorsCanBeCreatedAndRecalled()
    {
        AuthorEntity testAuthorEntityA = this.authorRepo.save(TestDataUtil.createTestAuthorEntityA());
        AuthorEntity testAuthorEntityB = this.authorRepo.save(TestDataUtil.createTestAuthorB());
        AuthorEntity testAuthorEntityC = this.authorRepo.save(TestDataUtil.createTestAuthorC());

        Iterable<AuthorEntity> result = this.authorRepo.findAll();

        assertThat(result)
                .hasSize(3)
                .containsExactly(testAuthorEntityA, testAuthorEntityB, testAuthorEntityC);
    }


    /*  CRUD Test - Update  */
    @Test
    public void testThatAuthorCanBeUpdated()
    {
        AuthorEntity testAuthorEntityA = this.authorRepo.save(TestDataUtil.createTestAuthorEntityA());

        testAuthorEntityA.setName("UPDATED"); // Changing author's name
        this.authorRepo.save(testAuthorEntityA);    // Updating the author

        Optional<AuthorEntity> result = this.authorRepo.findById(testAuthorEntityA.getId());

        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(testAuthorEntityA);
    }


    /*  CRUD Test - Delete  */
    @Test
    public void testThatAuthorCanBeDeleted()
    {
        AuthorEntity testAuthorEntityA = this.authorRepo.save(TestDataUtil.createTestAuthorEntityA());

        this.authorRepo.deleteById(testAuthorEntityA.getId());

        Optional<AuthorEntity> result = this.authorRepo.findById(testAuthorEntityA.getId());
        assertThat(result).isEmpty();
    }


    /*  Testing Spring JPA's magic feature - Name Parsing   */
    @Test
    public void testThatGetAuthorsWithAgeLessThan()
    {
        AuthorEntity testAuthorEntityA = this.authorRepo.save(TestDataUtil.createTestAuthorEntityA());
        AuthorEntity testAuthorEntityB = this.authorRepo.save(TestDataUtil.createTestAuthorB());
        AuthorEntity testAuthorEntityC = this.authorRepo.save(TestDataUtil.createTestAuthorC());

        Iterable<AuthorEntity> result = this.authorRepo.findByAgeLessThan(70);

        assertThat(result).containsExactly(testAuthorEntityB, testAuthorEntityC);
    }

    /*  Testing HQL (Hibernate Query Language)  */
    @Test
    public void testThatGetAuthorsWithAgeGreaterThan()
    {
        AuthorEntity testAuthorEntityA = this.authorRepo.save(TestDataUtil.createTestAuthorEntityA());
        AuthorEntity testAuthorEntityB = this.authorRepo.save(TestDataUtil.createTestAuthorB());
        AuthorEntity testAuthorEntityC = this.authorRepo.save(TestDataUtil.createTestAuthorC());

        Iterable<AuthorEntity> result = this.authorRepo.findAuthorsWithAgeGreaterThan(70);
        assertThat(result).containsExactly(testAuthorEntityA);
    }
    //----------------------------
}