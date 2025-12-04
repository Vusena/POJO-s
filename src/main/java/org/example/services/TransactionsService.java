package org.example.services;

import org.example.entities.Book;
import org.example.entities.Member;
import org.example.entities.Transactions;
import org.example.repositories.TransactionRepository;

import java.time.LocalDate;
import java.util.List;

public class TransactionsService {


    private TransactionRepository transactionRepository;
    private BookService bookService;
    private MemberService memberService;

    public TransactionsService(TransactionRepository transactionRepository,
                               BookService bookService,
                               MemberService memberService) {
        this.transactionRepository = transactionRepository;
        this.bookService = bookService;
        this.memberService = memberService;
    }

    // Borrow a book
    public void borrowBook(int transactionId, int bookId, int memberId) {

        // Validate input
        validateBorrowRequest(transactionId, bookId, memberId);

        // Get book and member
        Book book = bookService.getBook(bookId);
        Member member = memberService.getMember(memberId);

        // Reduce available copies
        if (book.getAvailableCopies() <= 0) {
            throw new IllegalStateException("Book is not available");
        }
        book.setAvailableCopies(book.getAvailableCopies() - 1);

        // Create transaction
        Transactions transaction = new Transactions(
                transactionId,
                book,
                member,
                LocalDate.now(),
                "ACTIVE"
        );

        transactionRepository.save(transaction);
    }


    // Return a book
    public void returnBook(int transactionId) {
        Transactions transaction = transactionRepository.findById(transactionId);

        if (transaction == null) {
            throw new IllegalArgumentException("Transaction not found");
        }

        if (!"ACTIVE".equalsIgnoreCase(transaction.getStatus())) {
            throw new IllegalStateException("Only active transactions can be returned");
        }

        // Increase available copies
        Book book = transaction.getBook();
        book.setAvailableCopies(book.getAvailableCopies() + 1);

        // Update transaction
        transaction.setStatus("RETURNED");
        transaction.setReturnDate(LocalDate.now());

        transactionRepository.save(transaction);
    }

    // --------------------------------------------------
    // 3. Get all transactions
    // --------------------------------------------------
    public List<Transactions> getAllTransactions() {
        return transactionRepository.findAll();
    }

    // --------------------------------------------------
    // 4. Get ACTIVE transactions
    // --------------------------------------------------
    public List<Transactions> getActiveTransactions() {
        return transactionRepository.findActiveTransactions();
    }

    // --------------------------------------------------
    // 5. Get OVERDUE transactions
    // --------------------------------------------------
    public List<Transactions> getOverdueTransactions() {
        return transactionRepository.findOverdueTransactions();
    }

    // --------------------------------------------------
    // VALIDATION LOGIC
    // --------------------------------------------------
    private void validateBorrowRequest(int transactionId, int bookId, int memberId) {

        if (transactionRepository.findById(transactionId) != null) {
            throw new IllegalArgumentException("Transaction ID already exists");
        }

        Book book = bookService.getBook(bookId);
        if (book == null) {
            throw new IllegalArgumentException("Book does not exist");
        }

        Member member = memberService.getMember(memberId);
        if (member == null) {
            throw new IllegalArgumentException("Member does not exist");
        }
    }

}
