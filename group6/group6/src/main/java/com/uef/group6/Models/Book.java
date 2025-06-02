package com.uef.group6.Models;

import com.uef.group6.Methods.IdGenerator.BookIDGenerator;
import jakarta.persistence.*;

@Entity
@Table(name = "Book")
public class Book {

    @Id
    @Column(name = "book_id", unique = true)
    private String bookID;

    @Column(name = "book_name")
    private String bookName;

    @Column(name = "book_state")
    private Boolean bookState;

    // Constructor
    public Book() {
    }

    public Book(String bookName, Boolean bookState) {
        this.bookName = bookName;
        this.bookState = bookState;
    }

    // Tự tạo ID cho Book trước khi lưu vào DB
    @PrePersist
    public void generateBookID() {
        if (this.bookID == null || this.bookID.isEmpty()) {
            this.bookID = BookIDGenerator.generateBookId();
        }
    }

    // Getters and setters
    public String getBookID() {
        return bookID;
    }

    public void setBookID(String bookID) {
        this.bookID = bookID;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public Boolean getBookState() {
        return bookState;
    }

    public void setBookState(Boolean bookState) {
        this.bookState = bookState;
    }

    // toString
    @Override
    public String toString() {
        return "Book{" +
                "bookID='" + bookID + '\'' +
                ", bookName='" + bookName + '\'' +
                ", bookState=" + bookState +
                '}';
    }
}