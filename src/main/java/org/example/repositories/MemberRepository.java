package org.example.repositories;

import org.example.database.DatabaseConnection;
import org.example.entities.Member;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MemberRepository {

    // ------------------------------------------------------------
    // 1. SAVE — Insert a new member into the DB
    // ------------------------------------------------------------
    public void save(Member member) {

        String sql = "INSERT INTO members (id, name, email, membership_date) " +
                "VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, member.getId());
            stmt.setString(2, member.getName());
            stmt.setString(3, member.getEmail());
            stmt.setDate(4, Date.valueOf(member.getMembershipDate()));

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error saving member: " + e.getMessage(), e);
        }
    }

    // ------------------------------------------------------------
    // 2. FIND BY ID
    // ------------------------------------------------------------
    public Member findById(int id) {

        String sql = "SELECT * FROM members WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return mapRowToMember(rs);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error finding member: " + e.getMessage(), e);
        }

        return null;
    }

    // ------------------------------------------------------------
    // 3. DELETE BY ID
    // ------------------------------------------------------------
    public void delete(int id) {

        String sql = "DELETE FROM members WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error deleting member: " + e.getMessage(), e);
        }
    }

    // ------------------------------------------------------------
    // 4. FIND ALL MEMBERS
    // ------------------------------------------------------------
    public List<Member> findAll() {

        String sql = "SELECT * FROM members";
        List<Member> members = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                members.add(mapRowToMember(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error reading all members: " + e.getMessage(), e);
        }

        return members;
    }

    // ------------------------------------------------------------
    // 5. FIND BY EMAIL
    // ------------------------------------------------------------
    public Member findByEmail(String email) {

        String sql = "SELECT * FROM members WHERE LOWER(email) = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email.toLowerCase());
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return mapRowToMember(rs);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error finding member by email: " + e.getMessage(), e);
        }

        return null;
    }

    // ------------------------------------------------------------
    // HELPER — Map ResultSet to Member object
    // ------------------------------------------------------------
    private Member mapRowToMember(ResultSet rs) throws SQLException {

        return new Member(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getString("email"),
                rs.getDate("membership_date").toLocalDate()
        );
    }
}
