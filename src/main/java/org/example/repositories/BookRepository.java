package org.example.repositories;

import org.example.entities.Book;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BookRepository {

    Map<Integer, Book> books = new HashMap<Integer, Book>();

    public void save(Book book) {
        books.put(book.getId(),book);
    }

    // Find a book by ID
    public Book findById(int id) {
        return books.get(id);
    }

    // Remove book
    public void delete(int id) {
        books.remove(id);
    }

    // Return all books
    public List<Book> findAll() {
        return new ArrayList<>(books.values());
    }

    // Search by title
    public List<Book> findByTitle(String title) {
        List<Book> result = new ArrayList<>();
        for (Book book : books.values()) {
            if (book.getTitle().toLowerCase().contains(title.toLowerCase())) {
                result.add(book);
            }
        }
        return result;
    }

    // Search by author
    public List<Book> findByAuthor(String author) {
        List<Book> result = new ArrayList<>();
        for (Book book : books.values()) {
            if (book.getAuthor().toLowerCase().contains(author.toLowerCase())) {
                result.add(book);
            }
        }
        return result;
    }
}
