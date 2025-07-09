package com.literatura.literatura.controller;

import com.literatura.literatura.model.Book;
import com.literatura.literatura.service.BookService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {
    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public List<Book> getAllBooks() {
        return bookService.getAllBooks();
    }

    @GetMapping("/top5")
    public List<Book> getTop5Books() {
        return bookService.getTop5Books();
    }

    @GetMapping("/search")
    public Book searchBook(@RequestParam String title) {
        return bookService.searchAndSaveBook(title);
    }
}
