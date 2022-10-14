package com.example.booksservice.controller;


import com.example.booksservice.dto.BooksDto;
import com.example.booksservice.exception.ApiRequestException;
import com.example.booksservice.service.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@CrossOrigin(origins = "http://localhost:9090", maxAge = 3600)
@RestController
public class BooksController {

    private static final Logger LOGGER= LoggerFactory.getLogger(BooksController.class);
    @Autowired
    private BookService bookService;


    @GetMapping
    public ResponseEntity<List<BooksDto>> getAll() {
        try {
            List<BooksDto> booksDtoList = bookService.getAllBooks();
            if (Objects.isNull(booksDtoList)) {
                throw new ApiRequestException("An error has occurred while fetching books from api");
            } else {
                return new ResponseEntity<>(booksDtoList, HttpStatus.OK);
            }


        } catch (ApiRequestException e){
            throw new ApiRequestException(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getBookById(@PathVariable long id) {
        try{
            BooksDto booksDto = bookService.getBookById(id);
            if (Objects.isNull(booksDto)) {
                throw new ApiRequestException("No book exists with id: "+ id);
            } else {
                return new ResponseEntity<>(booksDto, HttpStatus.OK);
            }
        } catch (ApiRequestException e) {
            throw new ApiRequestException(e.getMessage());
        }
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> create(@RequestBody BooksDto booksDto) {
        try {
            BooksDto books = bookService.save(booksDto);
            if (Objects.isNull(booksDto)) {
                throw new ApiRequestException("An error has occurred while adding book");
            } else {
                return new ResponseEntity<>(HttpStatus.CREATED);
            }

        } catch (ApiRequestException e) {
            throw new ApiRequestException(e.getMessage());
        }
    }

    @PutMapping(path = "/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HttpStatus> updateBook(@PathVariable Long id,
                                                 @RequestBody BooksDto booksDto) {
        try {
            BooksDto books = bookService.updateBookDetails(id, booksDto);
            if (Objects.isNull(booksDto)) {
                throw new ApiRequestException("An error has occurred while updating book");
            } else {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
        } catch (ApiRequestException e) {
            throw new ApiRequestException(e.getMessage());
        }
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> removeBook(@PathVariable Long id) {
        try {
            bookService.removeBookById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (ApiRequestException e) {
            throw new ApiRequestException(e.getMessage());
        }
    }
}
