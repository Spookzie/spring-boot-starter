package com.spookzie.database.repositories;

import com.spookzie.database.domain.entities.BookEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


/*  Repository for providing CRUD operations in books   */
@Repository
public interface BookRepository extends CrudRepository<BookEntity, String>
{
}