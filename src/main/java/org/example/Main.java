package org.example;

import org.example.entities.Book;
import org.example.entities.Member;
import org.example.repositories.BookRepository;
import org.example.repositories.MemberRepository;
import org.example.repositories.TransactionRepository;
import org.example.services.BookService;
import org.example.services.MemberService;
import org.example.services.TransactionsService;

import java.time.LocalDate;

public class Main {

    public static void main(String[] args) {

        // --------------------------------------------------
        // INITIALIZE REPOSITORIES
        // --------------------------------------------------
        BookRepository bookRepository = new BookRepository();
        MemberRepository memberRepository = new MemberRepository();
        TransactionRepository transactionRepository = new TransactionRepository();


        // --------------------------------------------------
        // INITIALIZE SERVICES
        // --------------------------------------------------
        BookService bookService = new BookService(bookRepository);
        MemberService memberService = new MemberService(memberRepository);
        TransactionsService transactionService = new TransactionsService(
                transactionRepository, bookService, memberService
        );

        // --------------------------------------------------
        // SAMPLE DATA
        // --------------------------------------------------

        // 1. Add books
        Book book1 = new Book(1, "Clean Code", "Robert Martin", "1234567890123", 5, 5);
        Book book2 = new Book(2, "Effective Java", "Joshua Bloch", "9876543210123", 3, 3);

        bookService.addBook(book1);
        bookService.addBook(book2);

        // 2. Register a member
        Member member1 = new Member(1, "Alice", "alice@example.com", LocalDate.now());
        memberService.registerMember(member1);

        // --------------------------------------------------
        // BORROW A BOOK
        // --------------------------------------------------
//        System.out.println("\nBorrowing book...");
//        transactionService.borrowBook(1, 1);  // member 1 borrows book 1

        // --------------------------------------------------
        // CHECK UPDATED BOOK COPIES
        // --------------------------------------------------
        System.out.println("Available copies of Clean Code: " +
                bookService.getBook(1).getAvailableCopies());

        // --------------------------------------------------
        // VIEW MEMBER'S BORROWING HISTORY
        // --------------------------------------------------
        System.out.println("\nBorrowing history for Alice:");
        System.out.println(memberService.viewBorrowingHistory(1));

        // --------------------------------------------------
        // RETURN A BOOK
        // --------------------------------------------------
        System.out.println("\nReturning book...");
        transactionService.returnBook(1);

        System.out.println("Available copies of Clean Code after returning: " +
                bookService.getBook(1).getAvailableCopies());

        // --------------------------------------------------
        // SHOW ALL TRANSACTIONS
        // --------------------------------------------------
        System.out.println("\nAll transactions:");
        System.out.println(transactionService.getAllTransactions());
    }


    }
