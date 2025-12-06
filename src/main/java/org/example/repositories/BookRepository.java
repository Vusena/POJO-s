package org.example.repositories;

import org.example.database.DatabaseConnection;
import org.example.entities.Book;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookRepository {
    // ------------------------------------------------------------
    // 1. SAVE — Insert a new book into the database
    // ------------------------------------------------------------
    public void save(Book book) {

        String sql = "INSERT INTO books (id, title, author, isbn, available_copies, total_copies) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, book.getId());
            stmt.setString(2, book.getTitle());
            stmt.setString(3, book.getAuthor());
            stmt.setString(4, book.getIsbn());
            stmt.setInt(5, book.getAvailableCopies());
            stmt.setInt(6, book.getTotalCopies());

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error saving book: " + e.getMessage(), e);
        }
    }

    // ------------------------------------------------------------
    // 2. FIND BY ID
    // ------------------------------------------------------------
    public Book findById(int id) {

        String sql = "SELECT * FROM books WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return mapRowToBook(rs);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error finding book: " + e.getMessage(), e);
        }

        return null;
    }

    // ------------------------------------------------------------
    // 3. DELETE BY ID
    // ------------------------------------------------------------
    public void delete(int id) {

        String sql = "DELETE FROM books WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error deleting book: " + e.getMessage(), e);
        }
    }

    // ------------------------------------------------------------
    // 4. FIND ALL BOOKS
    // ------------------------------------------------------------
    public List<Book> findAll() {

        String sql = "SELECT * FROM books";
        List<Book> books = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                books.add(mapRowToBook(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error reading all books: " + e.getMessage(), e);
        }

        return books;
    }

    // ------------------------------------------------------------
    // 5. FIND BY TITLE (LIKE %title%)
    // ------------------------------------------------------------
    public List<Book> findByTitle(String title) {

        String sql = "SELECT * FROM books WHERE LOWER(title) LIKE ?";
        List<Book> results = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "%" + title.toLowerCase() + "%");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                results.add(mapRowToBook(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error searching books by title: " + e.getMessage(), e);
        }

        return results;
    }

    // ------------------------------------------------------------
    // 6. FIND BY AUTHOR
    // ------------------------------------------------------------
    public List<Book> findByAuthor(String author) {

        String sql = "SELECT * FROM books WHERE LOWER(author) LIKE ?";
        List<Book> results = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "%" + author.toLowerCase() + "%");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                results.add(mapRowToBook(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error searching books by author: " + e.getMessage(), e);
        }

        return results;
    }

    // ------------------------------------------------------------
    // HELPER — Map ResultSet to Book object
    // ------------------------------------------------------------
    private Book mapRowToBook(ResultSet rs) throws SQLException {
        return new Book(
                rs.getInt("id"),
                rs.getString("title"),
                rs.getString("author"),
                rs.getString("isbn"),
                rs.getInt("available_copies"),
                rs.getInt("total_copies")
        );
    }
}
