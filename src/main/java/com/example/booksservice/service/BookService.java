package com.example.booksservice.service;

import com.example.booksservice.BooksServiceApplication;
import com.example.booksservice.dto.BooksDto;
import com.example.booksservice.entity.Books;
import com.example.booksservice.exception.ApiRequestException;
import com.example.booksservice.repository.BooksRepository;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class BookService {
    private static final Logger LOGGER= LoggerFactory.getLogger(BookService.class);
    @Autowired
    public BooksRepository booksRepository;

    @Autowired
    public ModelMapper modelMapper;

     public List<BooksDto> getAllBooks(){
         LOGGER.info("fetching books from db");
         List<BooksDto> booksDtoList = new ArrayList<>();
         booksRepository.findAll().forEach( books -> {
             BooksDto booksDto = BooksDto.builder()
                     .title(books.getTitle())
                     .description(books.getDescription())
                     .isbn(books.getIsbn())
                     .price(books.getPrice())
                     .build();
             booksDtoList.add(booksDto);
         });
         LOGGER.info("fetched books from db: " +booksDtoList);
         return booksDtoList;
     }

     public BooksDto getBookById(long id){
         Optional<Books> books = booksRepository.findBooksById(id);
         if(books.isPresent()){
             LOGGER.info("book exists with id: " + id + ", "+books.get());
             BooksDto booksDto = BooksDto.builder()
                     .title(books.get().getTitle())
                     .description(books.get().getDescription())
                     .isbn(books.get().getIsbn())
                     .price(books.get().getPrice())
                     .build();
            return booksDto;
         }else {
             LOGGER.error("No book exists with id: " + id);
             throw new ApiRequestException("No book exists with id: " + id);
         }
     }

     public BooksDto updateBookDetails(Long id, BooksDto booksDto){
         if(Objects.isNull(booksDto)){
             LOGGER.error("Request body can not be null");
             throw new ApiRequestException("Request body can not be null");
         }
         Optional<Books> books = booksRepository.findBooksById(id);
         if(books.isPresent()){
             LOGGER.info("book exists with id: " + id + ", "+books.get());
            Books books1 = books.get();
            booksRepository.save(Books.builder()
                            .id(books1.getId())
                    .title(Objects.nonNull(booksDto.getTitle())? booksDto.getTitle(): books1.getTitle())
                    .description(Objects.nonNull(booksDto.getDescription())? booksDto.getDescription(): books1.getDescription())
                    .isbn(booksDto.getIsbn())
                    .price(booksDto.getPrice())
                    .build());

            return modelMapper.map(books1, BooksDto.class);
         }else {
             throw new ApiRequestException("No book exists with id: " + id);
         }
     }

     public void removeBookById( Long id){
         Optional<Books> booksDto = booksRepository.findById(id);
         if(booksDto.isPresent()){
             LOGGER.info("book exists with id: " + id);
             booksRepository.deleteById(id);
             LOGGER.info("book deleted with id: " + id);
         } else {
             throw new ApiRequestException("No book exists with id: " + id);
         }
     }

     public BooksDto save(BooksDto booksDto){
         if(Objects.isNull(booksDto)){
             throw new ApiRequestException("Request body can not be null");
         }
         Books book = booksRepository.save(Books.builder()
                 .title(booksDto.getTitle())
                 .description(booksDto.getDescription())
                 .isbn(booksDto.getIsbn())
                 .price(booksDto.getPrice())
                 .build());
         LOGGER.info("book saved in db: " + book);
         BooksDto booksDto1 = modelMapper.map(book, BooksDto.class);
         return booksDto1;
     }
}
