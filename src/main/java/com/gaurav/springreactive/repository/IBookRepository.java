package com.gaurav.springreactive.repository;

import com.gaurav.springreactive.model.Book;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


public interface IBookRepository extends ReactiveCrudRepository<Book, String> {

}
