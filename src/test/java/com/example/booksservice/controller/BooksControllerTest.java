package com.example.booksservice.controller;


import com.example.booksservice.dto.BooksDto;
import com.example.booksservice.exception.ApiRequestException;
import com.example.booksservice.service.BookService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;


public class BooksControllerTest {

    @Mock
    private BookService mockBookService;

    @InjectMocks
    private BooksController booksController;


    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void getAllBooksSuccess() {
        List<BooksDto> booksDtoList = List.of(BooksDto.builder()
                .title("test book")
                .description("All about unit testing")
                .isbn(123456L)
                .price(12.00)
                .build());
        Mockito.when(mockBookService.getAllBooks()).thenReturn(booksDtoList);

        ResponseEntity<List<BooksDto>> response = booksController.getAll();

        Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);
        Assertions.assertEquals(response.getBody(), booksDtoList);
        Assertions.assertTrue(response.getBody().size() > 0);
    }

    @Test
    public void getAllBooksFailure() {
        Mockito.when(mockBookService.getAllBooks()).thenReturn(null);



        assertThrows(ApiRequestException.class,
                () -> {
                    ResponseEntity<List<BooksDto>> response = booksController.getAll();
                });
    }

    @Test
    public void getBookByIdSuccess() {
        BooksDto booksDto = BooksDto.builder()
                .title("test book")
                .description("All about unit testing")
                .isbn(123456L)
                .price(12.00)
                .build();
        Mockito.when(mockBookService.getBookById(123456L)).thenReturn(booksDto);

        ResponseEntity<?> response = booksController.getBookById(123456L);

        Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);
    }

    @Test
    public void getBookByIdFailure() {

        Mockito.when(mockBookService.getBookById(123456L)).thenReturn(null);

        assertThrows(ApiRequestException.class,
                () -> {
                    ResponseEntity<?> response = booksController.getBookById(123456L);
                });

    }

    @Test
    public void addBookSuccess() {
        BooksDto booksDto = BooksDto.builder()
                .title("test book")
                .description("All about unit testing")
                .isbn(123456L)
                .price(12.00)
                .build();
        Mockito.when(mockBookService.save(booksDto)).thenReturn(booksDto);

        ResponseEntity<?> response = booksController.create(booksDto);

        Assertions.assertEquals(response.getStatusCode(), HttpStatus.CREATED);
    }

    @Test
    public void addBookFailure() {
        BooksDto booksDto = BooksDto.builder()
                .title("test book")
                .description("All about unit testing")
                .isbn(123456L)
                .price(12.00)
                .build();
        Mockito.when(mockBookService.save(booksDto)).thenReturn(null);



     //   assertThrows(ApiRequestException.class,
             //   () -> {
                    ResponseEntity<?> response = booksController.create(booksDto);
              //  });
    }

    @Test
    public void updateBookSuccess() {
        BooksDto booksDto = BooksDto.builder()
                .title("test book")
                .description("All about unit testing")
                .isbn(123456L)
                .price(22.00)
                .build();
        Mockito.when(mockBookService.updateBookDetails(123456L, booksDto)).thenReturn(booksDto);

        ResponseEntity<HttpStatus> response = booksController.updateBook(123456L, booksDto);

        Assertions.assertEquals(response.getStatusCode(), HttpStatus.NO_CONTENT);
    }

    @Test
    public void updateBookFailure() {

        Mockito.when(mockBookService.updateBookDetails(123456L, null)).thenReturn(null);

        assertThrows(ApiRequestException.class,
                () -> {
                    ResponseEntity<HttpStatus> response = booksController.updateBook(123456L, null);
                });
    }

    @Test
    public void removeBookSuccess() {
        Mockito.doNothing().when(mockBookService).removeBookById(123456L);

        ResponseEntity<?> response = booksController.removeBook(123456L);

        Assertions.assertEquals(response.getStatusCode(), HttpStatus.NO_CONTENT);
    }

    @Test
    public void removeBookFailure() {

        Mockito.doThrow(ApiRequestException.class).when(mockBookService).removeBookById(123456L);

        assertThrows(ApiRequestException.class,
                () -> {
                    ResponseEntity<?> response = booksController.removeBook(123456L);
                });
    }

}
