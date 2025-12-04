package org.example.entities;

public class Book {

    private int id;
    private String title;
    private String author;
    private String isbn; // must be 13 digits
    private int availableCopies;
    private int totalCopies;

    public Book(){}

    public Book(int id, String title, String author, String isbn, int availableCopies, int totalCopies) {
        this.id=id;
        this.title=title;
        this.author=author;
        this.isbn=isbn;
        this.availableCopies=availableCopies;
        this.totalCopies=totalCopies;
    }

    // Getters & Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }

    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }

    public int getAvailableCopies() { return availableCopies; }
    public void setAvailableCopies(int availableCopies) { this.availableCopies = availableCopies; }

    public int getTotalCopies() { return totalCopies; }
    public void setTotalCopies(int totalCopies) { this.totalCopies = totalCopies; }

}
