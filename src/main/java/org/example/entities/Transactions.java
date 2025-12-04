package org.example.entities;

import java.time.LocalDate;

public class Transactions {

    private int id;
    private Book book;
    private Member member;
    private LocalDate borrowDate;
    private LocalDate returnDate;
    private String status; // ACTIVE, RETURNED, OVERDUE

    public Transactions() {}

    public Transactions(int id, Book book, Member member, LocalDate borrowDate, String status) {
        this.id = id;
        this.book = book;
        this.member = member;
        this.borrowDate = borrowDate;
        this.status = status;
    }

    // Getters & Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public Book getBook() { return book; }
    public void setBook(Book book) { this.book = book; }

    public Member getMember() { return member; }
    public void setMember(Member member) { this.member = member; }

    public LocalDate getBorrowDate() { return borrowDate; }
    public void setBorrowDate(LocalDate borrowDate) { this.borrowDate = borrowDate; }

    public LocalDate getReturnDate() { return returnDate; }
    public void setReturnDate(LocalDate returnDate) { this.returnDate = returnDate; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }


}
