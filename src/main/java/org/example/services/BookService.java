package org.example.services;

import org.example.entities.Book;
import org.example.repositories.BookRepository;

import java.util.List;

public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    // --------------------------------------------------
    // 1. Add a new book (with validation)
    // --------------------------------------------------
    public void addBook(Book book) {
        validateBook(book);
        bookRepository.save(book);
    }

    // --------------------------------------------------
    // 2. Remove a book by ID
    // --------------------------------------------------
    public void removeBook(int id) {
        bookRepository.delete(id);
    }

    // --------------------------------------------------
    // 3. Search by title
    // --------------------------------------------------
    public List<Book> searchByTitle(String title) {
        return bookRepository.findByTitle(title);
    }

    // --------------------------------------------------
    // 4. Search by author
    // --------------------------------------------------
    public List<Book> searchByAuthor(String author) {
        return bookRepository.findByAuthor(author);
    }

    // --------------------------------------------------
    // 5. Check if book is available for borrowing
    // --------------------------------------------------
    public boolean isBookAvailable(int bookId) {
        Book book = bookRepository.findById(bookId);
        return book != null && book.getAvailableCopies() > 0;
    }

    // --------------------------------------------------
    // 6. Get a single book by ID
    // --------------------------------------------------
    public Book getBook(int id) {
        return bookRepository.findById(id);
    }

    // --------------------------------------------------
    // 7. List all books
    // --------------------------------------------------
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    // --------------------------------------------------
    // VALIDATION LOGIC
    // --------------------------------------------------
    private void validateBook(Book book) {

        // ISBN must be exactly 13 digits
        if (book.getIsbn() == null || !book.getIsbn().matches("\\d{13}")) {
            throw new IllegalArgumentException("ISBN must be exactly 13 digits");
        }

        if (book.getTitle() == null || book.getTitle().trim().isEmpty()) {
            throw new IllegalArgumentException("Title cannot be empty");
        }

        if (book.getAuthor() == null || book.getAuthor().trim().isEmpty()) {
            throw new IllegalArgumentException("Author cannot be empty");
        }

        if (book.getTotalCopies() < 1) {
            throw new IllegalArgumentException("Total copies must be at least 1");
        }

        if (book.getAvailableCopies() < 0 ||
                book.getAvailableCopies() > book.getTotalCopies()) {
            throw new IllegalArgumentException("Available copies cannot be negative or greater than total copies");
        }
    }

}
