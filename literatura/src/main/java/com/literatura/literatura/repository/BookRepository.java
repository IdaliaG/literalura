package com.literatura.literatura.repository;

import com.literatura.literatura.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findByTitleContainingIgnoreCase(String title);

    @Query("SELECT b FROM Book b ORDER BY b.downloadCount DESC LIMIT 5")
    List<Book> findTop5ByOrderByDownloadCountDesc();

    List<Book> findByLanguagesContaining(String language);
}
