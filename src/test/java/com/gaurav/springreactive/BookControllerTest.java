package com.gaurav.springreactive;

import com.gaurav.springreactive.controller.BookController;
import com.gaurav.springreactive.model.Book;
import com.gaurav.springreactive.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.GroupedFlux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;


@WebFluxTest(BookController.class)
public class BookControllerTest {
    @MockBean
    private BookRepository bookRepository;

    @InjectMocks
    private BookController bookController;

    @Autowired
    private WebTestClient webTestClient;

    @Test
    public void testGetAllBooks() {
        Book book1 = new Book("1", "Book 1", "Author 1","Fiction");
        Book book2 = new Book("2", "Book 2", "Author 2","Thriller");

        when(bookRepository.findAll())
            .thenReturn(Flux.just(book1, book2));

        webTestClient.get()
            .uri("/api/books")
            .exchange()
            .expectStatus().isOk()
            .expectBodyList(Book.class)
            .hasSize(2)
            .contains(book1, book2);
    }

    @Test
    public void testGetBookById(){
        Book book1 = new Book("1", "Book 1", "Author 1", "Fiction");
        when(bookRepository.findById(anyString()))
                .thenReturn(Mono.just(book1));

        webTestClient.get()
                .uri("/api/books/1")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Book.class)
                .hasSize(1)
                .contains(book1);
    }

    @Test
    public void testCreateBook(){
        Book newBook = new Book("4", "New Book", "Author 4", "Mystery");

        when(bookRepository.save(newBook))
                .thenReturn(Mono.just(newBook));

        webTestClient.post()
                .uri("/api/books")
                .body(Mono.just(newBook), Book.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Book.class)
                .isEqualTo(newBook);
    }

    @Test
    public void testUpdateBook(){
        Book newBook = new Book("4", "New Book", "Author 4", "Mystery");

        when(bookRepository.save(newBook))
                .thenReturn(Mono.just(newBook));

        webTestClient.put()
                .uri("/api/books/4")
                .body(Mono.just(newBook), Book.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Book.class)
                .isEqualTo(newBook);
    }


    // Similar test methods for other CRUD operations

//    @Test
//    public void testGetBooksGroupedByGenre() {
//        Book book1 = new Book("1", "Book 1", "Author 1", "Fiction");
//        Book book2 = new Book("2", "Book 2", "Author 2", "Science Fiction");
//        Book book3 = new Book("3", "Book 3", "Author 3", "Fiction");
//
//        Mockito.when(bookRepository.findAll())
//                .thenReturn(Flux.just(book1, book2, book3));
//        //Flux<GroupedFlux<String, Book>> groupedBooks = bookController.getBooksGroupedByGenre();
//
//        Flux<GroupedFlux<String, Book>> groupedBooks = webTestClient.get()
//                .uri("/api/books/by-genre")
//                .exchange()
//                .expectStatus().isOk()
//                .returnResult(new ParameterizedTypeReference<GroupedFlux<String, Book>>() {})
//                .getResponseBody();
//        assertThat(groupedBooks);
////        StepVerifier
////                .create(groupedBooks)
////                .assertNext(entry -> assertThat(entry.).isEqualTo(expectedEntry))
////                .verifyComplete();
//
////        StepVerifier.create(
////                        Flux.just(book1,book2,book3)
////                                .groupBy(Book::getGenre)
////                                .concatMap(g -> g.defaultIfEmpty(book1) //if empty groups, show them
//////                                        .map(String::valueOf) //map to string
////                                        .map(Book::getId)
////                                        .startWith(g.key())) //start with the group's key
////                )
////                .expectNext("Fiction", book1.getId(),book3.getId())
////                .expectNext("Science Fiction", book2.getId())
////                .verifyComplete();
//
//
////        StepVerifier.create(groupedBooks)
////                .expectNext("Fiction",book1,book3)
////                .expectNext("Science Fiction",book2)
////                .verifyComplete();
//
////        StepVerifier.create(groupedBooks)
////                .assertNext(group -> {
////                    if (group.key().equals("Fiction")) {
////                        group.
////                        group.expectedNext(book1, book3);
////                    } else if (group.key().equals("Science Fiction")) {
////                        group.expectNext(book2);
////                    }
////                    group.expectComplete();
////                })
////                .expectNextCount(1) // Expect one group
////                .expectComplete()
////                .verify();
//    }

    @Test
    public void testGetBooksGroupedByGenre() {
        Book book1 = new Book("1", "Book 1", "Author 1", "Fiction");
        Book book2 = new Book("2", "Book 2", "Author 2", "Science Fiction");
        Book book3 = new Book("3", "Book 3", "Author 3", "Fiction");

        when(bookRepository.findAll())
                .thenReturn(Flux.just(book1, book2, book3));

        Flux<GroupedFlux<String, Book>> groupedBooks = webTestClient.get()
                .uri("/api/books/by-genre")
                .exchange()
                .expectStatus().isOk()
                .returnResult(new ParameterizedTypeReference<GroupedFlux<String, Book>>() {})
                .getResponseBody();

        StepVerifier.create(groupedBooks)
                .expectNextMatches(group -> {
                    if (group.key().equals("Fiction")) {
                        StepVerifier.create(group)
                                .expectNext(book1, book3)
                                .expectComplete()
                                .verify();
                    } else if (group.key().equals("Science Fiction")) {
                        StepVerifier.create(group)
                                .expectNext(book2)
                                .expectComplete()
                                .verify();
                    }
                    return true;
                })
                .expectNextCount(1)
                .expectComplete()
                .verify();
    }


}
