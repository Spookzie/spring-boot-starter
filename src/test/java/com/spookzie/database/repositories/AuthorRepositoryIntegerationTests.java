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
public class AuthorRepositoryIntegerationTests
{
    private final AuthorRepository authorRepo;  // Implementation that is being tested


    // Constructor
    @Autowired
    public AuthorRepositoryIntegerationTests(AuthorRepository under_test)
    {
        this.authorRepo = under_test;
    }


    // Read One Test
    @Test
    public void TestThatAuthorCanBeCreatedAndRecalled()
    {
        // Creating author
        Author author = TestDataUtil.CreateTestAuthorA();
        this.authorRepo.save(author);  // Inserting into DB

        // Recalling author
        Optional<Author> result = this.authorRepo.findById(author.getId());   // Fetching from DB
        assertThat(result).isPresent(); // Confirming that record exits
        assertThat(result.get()).isEqualTo(author); // Confirming that it matches
    }


//    // Read Many Test
//    @Test
//    public void TestThatMultipleAuthorsCanBeCreatedAndRecalled()
//    {
//        // Creating authors
//        Author authorA = TestDataUtil.CreateTestAuthorA();
//        this.authorRepo.Create(authorA);
//        Author authorB = TestDataUtil.CreateTestAuthorB();
//        this.authorRepo.Create(authorB);
//        Author authorC = TestDataUtil.CreateTestAuthorC();
//        this.authorRepo.Create(authorC);
//
//        // Recalling Authors
//        List<Author> result = this.authorRepo.Find();
//        assertThat(result)
//                .hasSize(3)
//                .containsExactly(authorA, authorB, authorC);
//    }
//
//
//    // Update Test
//    @Test
//    public void TestThatAuthorCanBeUpdated()
//    {
//        // Creating author
//        Author authorA = TestDataUtil.CreateTestAuthorA();
//        this.authorRepo.Create(authorA);
//
//        authorA.setName("UPDATED"); // Changing author's name
//        this.authorRepo.Update(authorA.getId(), authorA);    // Updating the author
//
//        // Finding and checking the author
//        Optional<Author> result = this.authorRepo.FindOne(authorA.getId());
//        assertThat(result).isPresent();
//        assertThat(result.get()).isEqualTo(authorA);
//    }
//
//
//    // Delete Test
//    @Test
//    public void TestThatAuthorCanBeDeleted()
//    {
//        // Creating author
//        Author authorA = TestDataUtil.CreateTestAuthorA();
//        this.authorRepo.Create(authorA);
//
//        // Deleting author
//        this.authorRepo.Delete(authorA.getId());
//
//        // Checking if author is deleted (result is deleted)
//        Optional<Author> result = this.authorRepo.FindOne(authorA.getId());
//        assertThat(result).isEmpty();
//    }
}