package com.example.booksservice.repository;

import com.example.booksservice.entity.Books;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BooksRepository extends CrudRepository<Books, Long> {

     Optional<Books> findBooksById(Long id);

     Books save(Books books);
     void deleteById(Long id);
}
