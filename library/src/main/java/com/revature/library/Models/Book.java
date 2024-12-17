package com.revature.library.Models;

import jakarta.persistence.*;

@Entity
@Table(name="books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int bookId;
    
    @Column(name="book_name")
    private String bookName;
    
    @Column(name="author")
    private String author;
    
    @Column(name="book_genre")
    private String bookGenre;
    
    @Column(name="book_age_limit")
    private int bookAgeLimit;

    //Constructors
    public Book(){}

    public Book(int bookId, String bookName, String author, String bookGenre, int bookAgeLimit){
        this.bookId=bookId;
        this.bookName=bookName;
        this.author=author;
        this.bookGenre=bookGenre;
        this.bookAgeLimit=bookAgeLimit;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getBookGenre() {
        return bookGenre;
    }

    public void setBookGenre(String bookGenre) {
        this.bookGenre = bookGenre;
    }

    public int getBookAgeLimit() {
        return bookAgeLimit;
    }

    public void setBookAgeLimit(int bookAgeLimit) {
        this.bookAgeLimit = bookAgeLimit;
    }
}

    //Getters and Setters
    