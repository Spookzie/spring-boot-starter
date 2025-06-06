package com.spookzie.database.repositories;

import com.spookzie.database.domain.entities.AuthorEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


/*  Repository for providing CRUD operations in authors */
@Repository
public interface AuthorRepository extends CrudRepository<AuthorEntity, Long>
{
    Iterable<AuthorEntity> findByAgeLessThan(int age);

    @Query("SELECT a FROM AuthorEntity a WHERE a.age > ?1")
    Iterable<AuthorEntity> findAuthorsWithAgeGreaterThan(int age);
}