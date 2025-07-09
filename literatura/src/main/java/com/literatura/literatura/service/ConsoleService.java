package com.literatura.literatura.service;

import com.literatura.literatura.dto.BookDTO;
import com.literatura.literatura.model.Author;
import com.literatura.literatura.model.Book;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Scanner;



@Service
public class ConsoleService {
    private final BookService bookService;
    private final Scanner scanner = new Scanner(System.in);

    public ConsoleService(BookService bookService) {
        this.bookService = bookService;
    }

    public void showMenu() {
        while (true) {
            System.out.println("\n=== LiterAlura - Catálogo de Libros ===");
            System.out.println("1. Buscar libro por título");
            System.out.println("2. Listar todos los libros registrados");
            System.out.println("3. Listar top 5 libros más descargados");
            System.out.println("4. Buscar libros por idioma");
            System.out.println("5. Buscar autores vivos en un año específico");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opción: ");

            int option = scanner.nextInt();
            scanner.nextLine(); // Consumir el salto de línea

            switch (option) {
                case 1:
                    searchBookByTitle();
                    break;
                case 2:
                    listAllBooks();
                    break;
                case 3:
                    listTop5Books();
                    break;
                case 4:
                    searchBooksByLanguage();
                    break;
                case 5:
                    searchAuthorsAliveInYear();
                    break;
                case 0:
                    System.out.println("Saliendo de LiterAlura...");
                    return;
                default:
                    System.out.println("Opción no válida. Intente de nuevo.");
            }
        }
    }

    private void searchBookByTitle() {
        System.out.print("Ingrese el título a buscar: ");
        String titulo = scanner.nextLine();

        try {
            // Busca en la API
            Book libro = bookService.searchAndSaveBook(titulo);


            if (libro == null) {
                System.out.println("No se encontró el libro. Prueba con:");
                System.out.println("- Don Quijote");
                System.out.println("- Frankenstein");
                System.out.println("- Dracula");
                return;
            }

           


            // Muestra los resultados
            System.out.println("\n=== LIBRO ENCONTRADO ===");
            System.out.println("Título: " + libro.getTitle());
            System.out.println("Autor: " + libro.getAuthor().getName());
            System.out.println("Idiomas: " + libro.getLanguages());

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void listAllBooks() {
        List<Book> books = bookService.getAllBooks();
        if (books.isEmpty()) {
            System.out.println("No hay libros registrados.");
        } else {
            System.out.println("Libros registrados:");
            books.forEach(System.out::println);
        }
    }

    private void listTop5Books() {
        List<Book> books = bookService.getTop5Books();
        System.out.println("Top 5 libros más descargados:");
        books.forEach(System.out::println);
    }

    private void searchBooksByLanguage() {
        System.out.print("Ingrese el idioma a buscar (ej. es, en, fr, etc.): ");
        String language = scanner.nextLine();

        List<Book> books = bookService.getBooksByLanguage(language);
        if (books.isEmpty()) {
            System.out.println("No se encontraron libros en ese idioma.");
        } else {
            System.out.println("Libros en idioma " + language + ":");
            books.forEach(System.out::println);
        }
    }

    private void searchAuthorsAliveInYear() {
        System.out.print("Ingrese el año a consultar: ");
        int year = scanner.nextInt();
        scanner.nextLine();

        List<Author> authors = bookService.getAuthorsAliveInYear(year);
        if (authors.isEmpty()) {
            System.out.println("No se encontraron autores vivos en ese año.");
        } else {
            System.out.println("Autores vivos en el año " + year + ":");
            authors.forEach(System.out::println);
        }
    }
}
