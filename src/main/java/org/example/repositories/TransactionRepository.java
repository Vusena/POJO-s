package org.example.repositories;

import org.example.entities.Transactions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TransactionRepository {

    private Map<Integer, Transactions> transactions = new HashMap<>();

    // Save or update a transaction
    public void save(Transactions transaction) {
        transactions.put(transaction.getId(), transaction);
    }

    // Find transaction by ID
    public Transactions findById(int id) {
        return transactions.get(id);
    }

    // Delete transaction by ID
    public void delete(int id) {
        transactions.remove(id);
    }

    // Return all transactions
    public List<Transactions> findAll() {
        return new ArrayList<>(transactions.values());
    }

    // Find all ACTIVE transactions
    public List<Transactions> findActiveTransactions() {
        List<Transactions> result = new ArrayList<>();
        for (Transactions t : transactions.values()) {
            if ("ACTIVE".equalsIgnoreCase(t.getStatus())) {
                result.add(t);
            }
        }
        return result;
    }

    // Find all overdue transactions
    public List<Transactions> findOverdueTransactions() {
        List<Transactions> result = new ArrayList<>();
        for (Transactions t : transactions.values()) {
            if ("OVERDUE".equalsIgnoreCase(t.getStatus())) {
                result.add(t);
            }
        }
        return result;
    }

    // Find all transactions for a specific member
    public List<Transactions> findByMemberId(int memberId) {
        List<Transactions> result = new ArrayList<>();
        for (Transactions t : transactions.values()) {
            if (t.getMember().getId() == memberId) {
                result.add(t);
            }
        }
        return result;
    }

    // Find all transactions for a specific book
    public List<Transactions> findByBookId(int bookId) {
        List<Transactions> result = new ArrayList<>();
        for (Transactions t : transactions.values()) {
            if (t.getBook().getId() == bookId) {
                result.add(t);
            }
        }
        return result;
    }
}
