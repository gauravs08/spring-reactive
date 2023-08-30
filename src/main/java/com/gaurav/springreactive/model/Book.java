package com.gaurav.springreactive.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
@AllArgsConstructor
public class Book {
    @Id
    private String id;
    private String title;
    private String author;
    private String genre;
}
