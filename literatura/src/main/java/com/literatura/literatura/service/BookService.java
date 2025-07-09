package com.literatura.literatura.service;

import com.literatura.literatura.dto.BookDTO;
import com.literatura.literatura.model.Author;
import com.literatura.literatura.model.Book;
import com.literatura.literatura.repository.AuthorRepository;
import com.literatura.literatura.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final ApiService apiService;

    public BookService(BookRepository bookRepository, AuthorRepository authorRepository, ApiService apiService) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.apiService = apiService;
    }

    public Book searchAndSaveBook(String title) {
        BookDTO bookDTO = apiService.buscarLibro(title);

        if (bookDTO == null) {
            throw new RuntimeException("Libro no encontrado");
        }

        Optional<Book> existingBook = bookRepository.findByTitleContainingIgnoreCase(bookDTO.title())
                .stream()
                .findFirst();

        if (existingBook.isPresent()) {
            return existingBook.get();
        }

        Author author = new Author();
        author.setName(bookDTO.author().name());
        author.setBirthYear(bookDTO.author().birthYear());
        author.setDeathYear(bookDTO.author().deathYear());
        author = authorRepository.save(author);

        Book book = new Book();
        book.setTitle(bookDTO.title());
        book.setAuthor(author);
        book.setLanguages(bookDTO.languages());
        book.setDownloadCount(bookDTO.downloadCount());

        return bookRepository.save(book);
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public List<Book> getTop5Books() {
        return bookRepository.findTop5ByOrderByDownloadCountDesc();
    }

    public List<Book> getBooksByLanguage(String language) {
        return bookRepository.findByLanguagesContaining(language);
    }

    public List<Author> getAuthorsAliveInYear(Integer year) {
        return authorRepository.findAliveInYear(year);
    }
}
