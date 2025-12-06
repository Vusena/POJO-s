package org.example.repositories;

import org.example.database.DatabaseConnection;
import org.example.entities.Book;
import org.example.entities.Member;
import org.example.entities.Transactions;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TransactionRepository {

    private final BookRepository bookRepository;
    private final MemberRepository memberRepository;

    // Constructor to allow fetching book & member
    public TransactionRepository(BookRepository bookRepository,
                                 MemberRepository memberRepository) {
        this.bookRepository = bookRepository;
        this.memberRepository = memberRepository;
    }

    // ------------------------------------------------------------
    // 1. SAVE — Insert or update a transaction
    // ------------------------------------------------------------
    public void save(Transactions transaction) {

        String sql = "REPLACE INTO transactions " +
                "(id, book_id, member_id, borrow_date, return_date, status) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, transaction.getId());
            stmt.setInt(2, transaction.getBook().getId());
            stmt.setInt(3, transaction.getMember().getId());
            stmt.setDate(4, Date.valueOf(transaction.getBorrowDate()));

            if (transaction.getReturnDate() != null) {
                stmt.setDate(5, Date.valueOf(transaction.getReturnDate()));
            } else {
                stmt.setNull(5, Types.DATE);
            }

            stmt.setString(6, transaction.getStatus());

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error saving transaction: " + e.getMessage(), e);
        }
    }

    // ------------------------------------------------------------
    // 2. FIND BY ID
    // ------------------------------------------------------------
    public Transactions findById(int id) {

        String sql = "SELECT * FROM transactions WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return mapRowToTransaction(rs);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error finding transaction: " + e.getMessage(), e);
        }

        return null;
    }

    // ------------------------------------------------------------
    // 3. DELETE
    // ------------------------------------------------------------
    public void delete(int id) {

        String sql = "DELETE FROM transactions WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error deleting transaction: " + e.getMessage(), e);
        }
    }

    // ------------------------------------------------------------
    // 4. FIND ALL
    // ------------------------------------------------------------
    public List<Transactions> findAll() {

        String sql = "SELECT * FROM transactions";
        List<Transactions> result = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                result.add(mapRowToTransaction(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error reading all transactions: " + e.getMessage(), e);
        }

        return result;
    }

    // ------------------------------------------------------------
    // 5. FIND ACTIVE
    // ------------------------------------------------------------
    public List<Transactions> findActiveTransactions() {

        return findByStatus("ACTIVE");
    }

    // ------------------------------------------------------------
    // 6. FIND OVERDUE
    // ------------------------------------------------------------
    public List<Transactions> findOverdueTransactions() {

        return findByStatus("OVERDUE");
    }

    private List<Transactions> findByStatus(String status) {

        String sql = "SELECT * FROM transactions WHERE UPPER(status) = ?";
        List<Transactions> result = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, status.toUpperCase());
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                result.add(mapRowToTransaction(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error filtering transactions by status: " + e.getMessage(), e);
        }

        return result;
    }

    // ------------------------------------------------------------
    // 7. FIND BY MEMBER ID
    // ------------------------------------------------------------
    public List<Transactions> findByMemberId(int memberId) {

        String sql = "SELECT * FROM transactions WHERE member_id = ?";
        List<Transactions> result = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, memberId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                result.add(mapRowToTransaction(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error finding transactions by member ID: " + e.getMessage(), e);
        }

        return result;
    }

    // ------------------------------------------------------------
    // 8. FIND BY BOOK ID
    // ------------------------------------------------------------
    public List<Transactions> findByBookId(int bookId) {

        String sql = "SELECT * FROM transactions WHERE book_id = ?";
        List<Transactions> result = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, bookId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                result.add(mapRowToTransaction(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error finding transactions by book ID: " + e.getMessage(), e);
        }

        return result;
    }

    // ------------------------------------------------------------
    // HELPER — Convert DB row → Transaction object
    // ------------------------------------------------------------
    private Transactions mapRowToTransaction(ResultSet rs) throws SQLException {

        int bookId = rs.getInt("book_id");
        int memberId = rs.getInt("member_id");

        Book book = bookRepository.findById(bookId);
        Member member = memberRepository.findById(memberId);

        Transactions t = new Transactions(
                rs.getInt("id"),
                book,
                member,
                rs.getDate("borrow_date").toLocalDate(),
                rs.getString("status")
        );

        Date returnDate = rs.getDate("return_date");
        if (returnDate != null) {
            t.setReturnDate(returnDate.toLocalDate());
        }

        return t;
    }
}
