package com.example.bookcatalog.controller;

import com.example.bookcatalog.model.Book;
import com.example.bookcatalog.service.BookService;
import io.micrometer.core.instrument.MeterRegistry;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
@Tag(name = "Book Controller", description = "Operations pertaining to books in the catalog")
public class BookController {

    private final BookService bookService;
    private final MeterRegistry meterRegistry;

    @Autowired
    public BookController(BookService bookService, MeterRegistry meterRegistry) {
        this.bookService = bookService;
        this.meterRegistry = meterRegistry;
    }

    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks() {
        meterRegistry.counter("api.requests", "endpoint", "getAllBooks").increment();
        return ResponseEntity.ok(bookService.findAllBooks());
    }

    @GetMapping("/paginated")
    public ResponseEntity<Page<Book>> getPaginatedBooks(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sort,
            @RequestParam(defaultValue = "asc") String direction) {
        meterRegistry.counter("api.requests", "endpoint", "getPaginatedBooks").increment();
        return ResponseEntity.ok(bookService.findBooksPaginated(page, size, sort, direction));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
        meterRegistry.counter("api.requests", "endpoint", "getBookById").increment();
        return ResponseEntity.ok(bookService.findBookById(id));
    }

    @GetMapping("/isbn/{isbn}")
    public ResponseEntity<Book> getBookByIsbn(@PathVariable String isbn) {
        meterRegistry.counter("api.requests", "endpoint", "getBookByIsbn").increment();
        return ResponseEntity.ok(bookService.findBookByIsbn(isbn));
    }

    @GetMapping("/search")
    public ResponseEntity<List<Book>> searchBooks(@RequestParam String keyword) {
        meterRegistry.counter("api.requests", "endpoint", "searchBooks").increment();
        return ResponseEntity.ok(bookService.searchBooks(keyword));
    }

    @GetMapping("/year/{year}")
    public ResponseEntity<List<Book>> getBooksByYear(@PathVariable int year) {
        meterRegistry.counter("api.requests", "endpoint", "getBooksByYear").increment();
        return ResponseEntity.ok(bookService.findBooksByYear(year));
    }

    @PostMapping
    public ResponseEntity<Book> createBook(@Valid @RequestBody Book book) {
        meterRegistry.counter("api.requests", "endpoint", "createBook").increment();
        return new ResponseEntity<>(bookService.createBook(book), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable Long id, @Valid @RequestBody Book book) {
        meterRegistry.counter("api.requests", "endpoint", "updateBook").increment();
        return ResponseEntity.ok(bookService.updateBook(id, book));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        meterRegistry.counter("api.requests", "endpoint", "deleteBook").increment();
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }
}
