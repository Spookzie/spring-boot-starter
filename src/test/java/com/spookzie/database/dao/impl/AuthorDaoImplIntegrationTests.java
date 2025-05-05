package com.spookzie.database.dao.impl;

import com.spookzie.database.TestDataUtil;
import com.spookzie.database.domain.Author;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class AuthorDaoImplIntegrationTests
{
    private final AuthorDaoImpl authorDao;  // Implementation that is being tested


    @Autowired
    public AuthorDaoImplIntegrationTests(AuthorDaoImpl under_test)
    {
        this.authorDao = under_test;
    }


    //  Tests   //
    @Test
    public void TestThatAuthorCanBeCreatedAndRecalled()
    {
        // Creating author
        Author author = TestDataUtil.CreateTestAuthorA();
        this.authorDao.Create(author);  // Inserting into DB

        // Recalling author
        Optional<Author> result = this.authorDao.FindOne(author.getId());   // Fetching from DB
        assertThat(result).isPresent(); // Confirming that record exits
        assertThat(result.get()).isEqualTo(author); // Confirming that it matches
    }

    @Test
    public void TestThatMultipleAuthorsCanBeCreatedAndRecalled()
    {
        // Creating authors
        Author authorA = TestDataUtil.CreateTestAuthorA();
        this.authorDao.Create(authorA);
        Author authorB = TestDataUtil.CreateTestAuthorB();
        this.authorDao.Create(authorB);
        Author authorC = TestDataUtil.CreateTestAuthorC();
        this.authorDao.Create(authorC);

        // Recalling Authors
        List<Author> result = this.authorDao.Find();
        assertThat(result)
                .hasSize(3)
                .containsExactly(authorA, authorB, authorC);
    }
}