package com.example.assigment5.controller;
import com.example.assigment5.model.Book;
import com.example.assigment5.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BookController {

    private final BookRepository bookRepository;

    // URI Versioning: http://localhost:8080/v1/books
    @GetMapping("/v1/books")
    public List<Book> getAllBooksV1() {
        return bookRepository.findAll();
    }

    // Request Parameter Versioning: http://localhost:8080/books?version=1
    @GetMapping(value = "/books", params = "version=1")
    public List<Book> getAllBooksV2() {
        return bookRepository.findAll();
    }

    // Custom Header Versioning: Header X-API-VERSION=2
    @GetMapping(value = "/books", headers = "X-API-VERSION=2")
    public List<Book> getAllBooksV3() {
        return bookRepository.findAll();
    }

    // Media Type Versioning: Header Accept=application/cs.miu.edu-v1+json
    @GetMapping(value = "/books", produces = "application/cs.miu.edu-v1+json")
    public List<Book> getAllBooksV4() {
        return bookRepository.findAll();
    }

    // Common CRUD Endpoints
    @GetMapping("/books/{id}")
    public Book getBookById(@PathVariable int id) {
        return bookRepository.findById(id).orElse(null);
    }

    @PostMapping("/books")
    public Book addBook(@RequestBody Book book) {
        return bookRepository.save(book);
    }

    @PutMapping("/books/{id}")
    public Book updateBook(@PathVariable int id, @RequestBody Book updatedBook) {
        return bookRepository.findById(id)
                .map(book -> {
                    book.setTitle(updatedBook.getTitle());
                    book.setIsbn(updatedBook.getIsbn());
                    book.setPrice(updatedBook.getPrice());
                    return bookRepository.save(book);
                })
                .orElse(null);
    }

    @DeleteMapping("/books/{id}")
    public void deleteBook(@PathVariable int id) {
        bookRepository.deleteById(id);
    }
}

