package com.example.booksservice;

import com.example.booksservice.dto.BooksDto;
import com.example.booksservice.repository.BooksRepository;
import com.example.booksservice.service.BookService;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import com.fasterxml.jackson.core.type.TypeReference;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@SpringBootApplication
public class BooksServiceApplication{
    private static final Logger LOGGER= LoggerFactory.getLogger(BooksServiceApplication.class);
    public static void main(String[] args) {

        SpringApplication.run(BooksServiceApplication.class, args);
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public ObjectMapper customJson(){
        return new Jackson2ObjectMapperBuilder()
                .serializationInclusion(JsonInclude.Include.NON_NULL)
                .build();
    }

    @Bean
    CommandLineRunner runner(BookService bookService) {
        return args -> {
            // read json and write to db
            ObjectMapper mapper = new ObjectMapper();
            TypeReference<List<BooksDto>> typeReference = new TypeReference<List<BooksDto>>(){};
            InputStream inputStream = TypeReference.class.getResourceAsStream("/data.json");
            try {
                List<BooksDto> booksDtos = mapper.readValue(inputStream,typeReference);
                booksDtos.forEach( booksDto -> bookService.save(booksDto));
               LOGGER.info("Books Saved!");
            } catch (IOException e){
                LOGGER.error("Unable to save books: " + e.getMessage());
            }
        };
    }
}
