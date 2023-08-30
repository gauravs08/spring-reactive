package com.gaurav.springreactive.repository;

import com.gaurav.springreactive.model.Book;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Repository
public abstract class BookRepository implements IBookRepository {
    private final ReactiveMongoTemplate reactiveMongoTemplate;

    public BookRepository(ReactiveMongoTemplate reactiveMongoTemplate) {
        this.reactiveMongoTemplate = reactiveMongoTemplate;
    }

    @Override
    public Flux<Book> findAll() {
        return reactiveMongoTemplate.findAll(Book.class);
    }

    @Override
    public Mono<Book> findById(String id) {
        return reactiveMongoTemplate.findById(id, Book.class);
    }

    @Override
    public Mono<Book> save(Book book) {
        return reactiveMongoTemplate.save(book);
    }

    @Override
    public Mono<Void> deleteById(String id) {
        return reactiveMongoTemplate.remove(Query.query(Criteria.where("id").is(id)), Book.class)
                .then();
    }

}
