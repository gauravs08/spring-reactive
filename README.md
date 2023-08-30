# Spring Reactive CRUD API Example

This repository contains an example Spring Boot Reactive CRUD API for managing books.

## Technologies Used

- Spring Boot
- Spring WebFlux (Reactive Web)
- Spring Data Reactive MongoDB
- JUnit 5
- Mockito
- WebTestClient

## Getting Started

To run this project locally, follow these steps:

1. Clone the repository:
   ```bash
   git clone https://github.com/your-username/your-repo-name.git
   cd your-repo-name
2. Run the project 
    ```./mvnw spring-boot:run```
3. The API will be available at http://localhost:8080/api/books.

    Endpoints

   ```GET /api/books: Get a list of all books.```
    
   ```GET /api/books/{id}: Get details of a specific book by ID.```

   ```POST /api/books: Create a new book.```
    
   ```PUT /api/books/{id}: Update an existing book by ID.```
   
   ```DELETE /api/books/{id}: Delete a book by ID.```
   
   ```GET /api/books/by-genre: Get books grouped by genre.```