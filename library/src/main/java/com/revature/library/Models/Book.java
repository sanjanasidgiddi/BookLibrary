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
    
    @Column(name="authors")
    private String Author;
    
    @Column(name="book_genre")
    private String BookGenre;
    
    @Column(name="book_age_rated_reads")
    private int BookAgeRatedReads;

    //Constructors
    public Book(){}

    public Book(int BookId, int BookName, String Author, String BookGenre, int BookAgeRatedReads){
        this.BookId=BookId;
        this.BookName=BookName;
        this.Author=Author;
        this.BookGenre=BookGenre;
        this.BookAgeRatedReads=BookAgeRatedReads;
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

    public int getBookAgeRatedReads() {
        return BookAgeRatedReads;
    }

    public void setBookAgeRatedReads(int bookAgeRatedReads) {
        BookAgeRatedReads = bookAgeRatedReads;
    }
    


}
