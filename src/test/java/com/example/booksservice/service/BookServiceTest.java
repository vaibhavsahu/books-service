package com.example.booksservice.service;

import com.example.booksservice.dto.BooksDto;
import com.example.booksservice.entity.Books;
import com.example.booksservice.exception.ApiRequestException;
import com.example.booksservice.repository.BooksRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class BookServiceTest {
    @Mock
    private BooksRepository mockBooksRepository;

    @InjectMocks
    private BookService bookService;

    @Mock
    private ModelMapper mockModelMapper;


    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void getAllBooksSuccess() {
        List<Books> booksList = List.of(Books.builder()
                .id(1L)
                .title("test book")
                .description("All about unit testing")
                .isbn(123456L)
                .price(12.00)
                .build());
        when(mockBooksRepository.findAll()).thenReturn(booksList);

        List<BooksDto> response = bookService.getAllBooks();

        Assertions.assertNotNull(response);
        Assertions.assertTrue(response.size() > 0);
    }

    @Test
    public void getAllBooksFailure() {
        when(mockBooksRepository.findAll()).thenReturn(null);

        assertThrows(NullPointerException.class,
                () -> {
                    List<BooksDto> response = bookService.getAllBooks();
                });
    }

    @Test
    public void getBookByIdSuccess() {

        Books book = Books.builder()
                .id(1L)
                .title("test book")
                .description("All about unit testing")
                .isbn(123456L)
                .price(22.00)
                .build();
        when(mockBooksRepository.findBooksById(123456L)).thenReturn(Optional.of(book));

        BooksDto result = bookService.getBookById(123456L);

        Assertions.assertNotNull(result);
    }

    @Test
    public void getBookByIdFailure() {
        when(mockBooksRepository.findBooksById(123456L)).thenReturn(null);

        assertThrows(NullPointerException.class,
                () -> {
                    BooksDto booksDto = bookService.getBookById(123456L);
                });
    }

    @Test
    public void addBookSuccess() {
        Books book = Books.builder()
                .id(1L)
                .title("test book")
                .description("All about unit testing")
                .isbn(123456L)
                .price(22.00)
                .build();

        BooksDto booksDto = BooksDto.builder()
                .title("test book")
                .description("All about unit testing")
                .isbn(123456L)
                .price(22.00)
                .build();

        when(mockBooksRepository.save(book)).thenReturn(book);
        when(mockModelMapper.map(book, BooksDto.class)).thenReturn(booksDto);

        BooksDto result = bookService.save(booksDto);

        Mockito.verify(mockBooksRepository, Mockito.times(1))
                .save(Mockito.any());
    }

    @Test
    public void addBookFailure() {
        Books book = Books.builder()
                .id(1L)
                .title("test book")
                .description("All about unit testing")
                .isbn(123456L)
                .price(22.00)
                .build();


        when(mockBooksRepository.save(book)).thenReturn(null);

        Mockito.verify(mockBooksRepository, Mockito.times(0))
                .save(Mockito.any());
    }

    @Test
    public void updateBookSuccess() {
        Books book = Books.builder()
                .id(2L)
                .title("test book")
                .description("All about unit testing")
                .isbn(12345678L)
                .price(22.00)
                .build();

        BooksDto booksDto = BooksDto.builder()
                .title("test book")
                .description("All about unit testing")
                .isbn(12345678L)
                .price(22.00)
                .build();
        when(mockBooksRepository.findBooksById(123456L)).thenReturn(Optional.of(book));
        when(mockBooksRepository.save(book)).thenReturn(any());

        BooksDto result = bookService.updateBookDetails(123456L, booksDto);
        Mockito.verify(mockBooksRepository, Mockito.times(1))
                .findBooksById(123456L);
    }

    @Test
    public void updateBookFailure() {

        Books book = Books.builder()
                .id(1L)
                .title("test book")
                .description("All about unit testing")
                .isbn(123456L)
                .price(22.00)
                .build();

        when(mockBooksRepository.save(book)).thenReturn(null);


        assertThrows(ApiRequestException.class,
                () -> {
                    BooksDto result = bookService.updateBookDetails(123456L, null);
                });

    }

    @Test
    public void removeBookSuccess() {
        Books book = Books.builder()
                .id(1L)
                .title("test book")
                .description("All about unit testing")
                .isbn(123456L)
                .price(22.00)
                .build();
        when(mockBooksRepository.findById(123456L)).thenReturn(Optional.of(book));

        Mockito.doNothing().when(mockBooksRepository).deleteById(123456L);

         bookService.removeBookById(123456L);
        Mockito.verify(mockBooksRepository, Mockito.times(1))
                .deleteById(123456L);
    }

    @Test
    public void removeBookFailure() {

        Mockito.doThrow(new ApiRequestException("error")).when(mockBooksRepository).deleteById(any());

        assertThrows(ApiRequestException.class,
                () -> {
                    bookService.removeBookById(123456L);
                });

    }

}
