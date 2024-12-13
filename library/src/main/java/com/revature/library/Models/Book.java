package com.revature.library.Models;

import jakarta.persistence.*;

@Entity
@Table(name="books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int BookId;
    
    @Column(name="book_name")
    private int BookName;
    
    @Column(name="author")
    private String Author;
    
    @Column(name="book_genre")
    private String BookGenre;
    
    @Column(name="book_age_limit")
    private int BookAgeLimit;

    //Constructors
    public Book(){}

    public Book(int BookId, int BookName, String Author, String BookGenre, int BookAgeLimit){
        this.BookId=BookId;
        this.BookName=BookName;
        this.Author=Author;
        this.BookGenre=BookGenre;
        this.BookAgeLimit=BookAgeLimit;
    }

    //Getters and Setters
    public int getBookId() {
        return BookId;
    }

    public void setBookId(int bookId) {
        BookId = bookId;
    }

    public int getBookName() {
        return BookName;
    }

    public void setBookName(int bookName) {
        BookName = bookName;
    }

    public String getAuthor() {
        return Author;
    }

    public void setAuthor(String author) {
        Author = author;
    }

    public String getBookGenre() {
        return BookGenre;
    }

    public void setBookGenre(String bookGenre) {
        BookGenre = bookGenre;
    }

    public int getBookAgeLimit() {
        return BookAgeLimit;
    }

    public void setBookAgeRatedReads(int bookAgeLimit) {
        BookAgeLimit = bookAgeLimit;
    }
    


}
