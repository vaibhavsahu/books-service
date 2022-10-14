package com.example.booksservice.dto;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@Builder
@Getter
public class BooksDto {

    private String title;
    private String description;

    private Long isbn;

    private double price;

    @JsonCreator
    public BooksDto(@JsonProperty("title") String title,
                      @JsonProperty("description") String description,
                    @JsonProperty("isbn") Long isbn,
                    @JsonProperty("price") double price){
        this.title = title;
        this.description = description;
        this.isbn = isbn;
        this.price = price;
    }
}
